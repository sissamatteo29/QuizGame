package com.mygdx.quizgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.quizgame.timehandling.Timer;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;

public class GameScreen extends InputAdapter implements Screen {

    MainGame game;

    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private Stage stage;
    private Table table;

    private int quizNumber;

    //boolean value that specifies if this screen is set on display after a pause of the game, of because of a new game
    boolean newGame = true;

    //need to generate random numbers
    Random myRandomGenerator;

    Quiz currentQuizInfos;
    private int correctAnswerPosition;

    BitmapFont font;


    public GameScreen(MainGame game) {
        this.game = game;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    @Override
    public void show() {

        //you repeat the show method only if this screen is called for a new game
        if (newGame == true) {

            //Gdx.app.log("tt", "entrato dentro if");

            //it's important, BEFORE THE BEGINNING OF THE LOGIC, to restore the initial state of the IntArray containing the YearIDs and the other variables necessary to begin a new game
            game.gameSetter();

            //if all of this block of code was executed, the new game is already created
            newGame = false;

            //VIEWPORT
            viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
            viewport.apply();

            //SPRITEBATCH
            spriteBatch = new SpriteBatch();

            //FONT
            font = new BitmapFont(Gdx.files.internal("font.fnt"));
            font.getData().setScale(0.2f);

            //STAGE AND TABLE
            stage = new Stage(viewport);
            table = new Table();
            //DEBUG
            //table.setDebug(true);
            table.setFillParent(true);

            //adding the label containing the year
            game.yearLabel.setFontScale(0.8f);
            table.add(game.yearLabel).padBottom(70f).padTop(50f).colspan(2);
            table.row();

            //adding the label with the timer in it
            game.timeLabel.setFontScale(0.4f);
            game.timeLabel.setText(game.timer.toString());
            table.add(game.timeLabel).padBottom(20f);

            //adding label to count the user total score
            game.scoreLabel.setFontScale(0.4f);
            game.scoreLabel.setText(Integer.toString(game.userScore));
            table.add(game.scoreLabel).padBottom(20f);
            table.row();

            //adding buttons to the table
            for (int i = 0; i < 4; i++) {
                game.answersButtonArray[i].getLabel().setFontScale(0.2f);
                if (i != 3) {
                    table.add(game.answersButtonArray[i]).width(380f).height(50f).padBottom(35f).colspan(2);
                } else {
                    table.add(game.answersButtonArray[i]).width(380f).height(50f).padBottom(100f).colspan(2);
                }
                table.row();
            }

            table.bottom();
            stage.addActor(table);

            myRandomGenerator = new Random();
            quizNumber = 0;
            currentQuizInfos = new Quiz();

            //this method is called everytime it is needed to change the quiz on screen, so it will set the new year to display, change buttons and their text using random numbers
            prepareNewQuiz();
        }

        //INPUT RESPONSE OF THE GUI.
        //note: this has to be done AFTER the call to prepareNewQuiz, so that variables are set to the new content on the screen
        Gdx.input.setInputProcessor(stage);

        game.timer.setUpdatable(true);

        //DEBUG
        //Gdx.input.setInputProcessor(this);



        setInputResponse();

    }

    private void setInputResponse() {

        //every Textbutton needs to know what to do when clicked
        for (int i = 0; i < 4; i++) {
            //first of all we need to clear all the previous actions and listeners set on the buttons, to make sure they don't overlap
            game.answersButtonArray[i].clearListeners();
            game.answersButtonArray[i].clearActions();

            if (i == correctAnswerPosition) {
                game.answersButtonArray[i].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        //in this case we go to the next quiz
                        game.userScore++;
                        game.scoreLabel.setText(Integer.toString(game.userScore));
                        prepareNewQuiz();
                    }
                });
            } else { //wrong button clicked
                final int finalI = i;
                game.answersButtonArray[i].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        game.answersButtonArray[finalI].addAction(Actions.fadeOut(0.6f));
                        game.timer.sub10();
                    }
                });
            }
        }
    }

    //method to set all the variable necessary to display a new quiz. It returns the int of the correct answer, or -1 in case of error
    private boolean prepareNewQuiz() {

        //method that manages all the previous changes made to buttons and restores the default values to create a new quiz
        restartButtons();

        //we also need to check if the user already answered all the questions
        //DEBUG-- System.out.println("Starting a new quiz with:  countQuiz = " + countQuiz + " game.quizAmount = " + game.quizAmount + " game.yearIDAvailable.size = " + game.yearIdAvailable.size);
        Gdx.app.log("quizNumber", "value: " + quizNumber);
        if (quizNumber == game.quizAmount){
            //the user won
            game.setScreen(new LastScreen(game, FinalScreenEnums.VICTORY, game.userScore));
            return true;
        }

        //first of all we need to fetch a new year from the underlying database that has not already been used
        //we do it by taking a random number comprised between 0 and size of the intArray containing YearIDs available. the int at that random index is the next correct answer
        int randomIndex, currentYearID;
        randomIndex = myRandomGenerator.nextInt(game.yearIdAvailable.size);
        currentYearID = game.yearIdAvailable.get(randomIndex);


        //DEBUG-- Gdx.app.log("New YearID found", "The new yearID is " + currentYearID);

        //we need to take the element out of the IntArray so that it won't show again
        game.yearIdAvailable.removeIndex(randomIndex);

        //we query the database to take the YEAR corresponding to the YEARid found
        game.result = game.database.selectYearById(currentYearID);
        game.result.moveToNext();
        currentQuizInfos.setYear(game.result.getInt(2));
        //set label text to be displayed
        game.yearLabel.setText(Integer.toString(currentQuizInfos.getYear()));
        //DEBUG-- Gdx.app.log("Associating YearID with YEAR", "The current year is set to " + currentQuizInfos.getYear());

        //we query the database to take the event string associated with that year
        game.result = game.database.selectEventByYearId(currentYearID);
        game.result.moveToNext();
        currentQuizInfos.setEvent(game.result.getString(2));
        //DEBUG-- Gdx.app.log("Associating YearID with EVENT", "The current event is set to " + currentQuizInfos.getEvent());

        //we need to establish where we're going to place the correct answer (random number between 0 and 3)
        correctAnswerPosition = myRandomGenerator.nextInt(4);
        //DEBUG-- Gdx.app.log("Correct Answer Position", "" + correctAnswerPosition);

        //then we can:
        //-  Place the correct answer in its position, so we'll set the text of the textbutton with the same number
        //-  Use the random number generator to find other 3 different answers to place on the other buttons
        game.answersButtonArray[correctAnswerPosition].setText(currentQuizInfos.getEvent());

        //for the other buttons
        IntSet previousYears = new IntSet();
        int temporaryRandomNumber;
        String otherEvent;
        for (int i = 0; i < 4; i++){
            if (i != correctAnswerPosition){
                //generate a random number different from randomYearID
                temporaryRandomNumber = myRandomGenerator.nextInt(game.quizAmount);
                //verify if this yearID was not already taken by the other incorrect answers or if it's the same ID as the correct one
                if (temporaryRandomNumber == currentYearID || previousYears.contains(temporaryRandomNumber)){
                    i--;
                    continue;
                } else {    //found a different year, we retrieve the events associated and place them as text on the current button

                    //DEBUG-- Gdx.app.log("Found new YearID","YearID: " + temporaryRandomNumber);
                    previousYears.add(temporaryRandomNumber);

                    game.result = game.database.selectEventByYearId(temporaryRandomNumber);
                    game.result.moveToNext();
                    otherEvent = game.result.getString(2);
                    //DEBUG-- Gdx.app.log("Found other Event", "The event related to index " + i + " is: " + otherEvent);
                    game.answersButtonArray[i].setText(otherEvent);
                }
            }
        }

        //we can clear the setint
        previousYears.clear();

        //we need to reset the input answers for the buttons
        setInputResponse();

        //remember to add 1 to the number of quizes
        quizNumber++;
        //DEBUG-- System.out.println("countQuiz updated to: " + countQuiz);

        //we return true because everything ran successfully
        return true;
    }


    public void restartButtons() {
        for (int i = 0; i < 4; i++){
            //colors detected through pixspy online. The important thing is to set alpha (last parameter) back to 1)
              game.answersButtonArray[i].setColor(151f, 151f, 151f, 1);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1f,1f,1f,1f);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        font.draw(spriteBatch, "Your score:", 270, 510);
        spriteBatch.end();

        //we need to display the current time and update it every second
        game.timer.update(delta);
        //we check if time has expired
        if (game.timer.isFinished()){
            game.setScreen(new LastScreen(game, FinalScreenEnums.DEFEAT, game.userScore));
        }
        //if there was an update, we update the label text:
        game.timeLabel.setText(game.timer.toString());

        stage.draw();
        stage.act();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        //we need to save here the current state of the game, so we stop the rendering method that is responsible of updating our timer
        game.timer.setUpdatable(false);

    }

    @Override
    public void resume() {
        //it's called from the pause screen to take back this screen without passing through the show method again
        game.setScreen(game.pauseScreen);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        spriteBatch.dispose();
    }





    /* DEBUG CODE TO MOVE CAMERA AROUND
    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.UP) {
            //it needs downcasting because getCamera returns a simple Camera, which doesn't have the field zoom
            ((OrthographicCamera) viewport.getCamera()).zoom -= 0.1;
            ((OrthographicCamera) viewport.getCamera()).update();
        }
        if (keycode == Input.Keys.DOWN) {
            ((OrthographicCamera) viewport.getCamera()).zoom += 0.1;
            ((OrthographicCamera) viewport.getCamera()).update();
        }
        if(keycode == Input.Keys.A) {
            ((OrthographicCamera) viewport.getCamera()).translate(-35f,0);
            ((OrthographicCamera) viewport.getCamera()).update();
        }
        if(keycode == Input.Keys.S) {
            ((OrthographicCamera) viewport.getCamera()).translate(0,-35f);
            ((OrthographicCamera) viewport.getCamera()).update();
        }
        if(keycode == Input.Keys.W) {
            ((OrthographicCamera) viewport.getCamera()).translate(0,35f);
            ((OrthographicCamera) viewport.getCamera()).update();
        }
        if(keycode == Input.Keys.D) {
            ((OrthographicCamera) viewport.getCamera()).translate(35,0);
            ((OrthographicCamera) viewport.getCamera()).update();
        }
        return true;
    }*/


    //Deleted code about the background image
    //show method
    //set the background to be larger than the size of the screen so that we can exploit the extend viewport properly
    //game.backgroundSprite.setSize(900f, 900f);
    //game.backgroundSprite.setPosition(-500,-500);

    //render method
    //we need to render things in order, so the first element to be displayed in every frame is the background
        /*spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        game.backgroundSprite.draw(spriteBatch);
        spriteBatch.end();*/


}

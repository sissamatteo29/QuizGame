package com.mygdx.quizgame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen extends ScreenAdapter {

    MainGame game;

    Stage stage;
    Table table;

    //references to buttons
    ImageTextButton startGameButton, quitGameButton;


    public StartScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(1f,1f,1f,1f);

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        game.viewport.update(width, height);
    }
    

    @Override
    public void show() {
        super.show();

        stage = new Stage(game.viewport);
        table = new Table();
        startGameButton = new ImageTextButton("New Game", game.skin2);
        quitGameButton = new ImageTextButton("Quit Game", game.skin2);
        startGameButton.setTransform(true);
        quitGameButton.setTransform(true);


        //table.setPosition(200f,100f);
        stage.addActor(table);
        table.setFillParent(true);



        //metodo per settare anche la dimensione del font rispetto a quelle inserite per il bottone stesso
        //startGameButton.setScale(0.2f);
        //game.font.getData().setScale(0.1f);
        //startGameButton.getStyle().font.getData().setScale(0.1f);
        startGameButton.getStyle().font.getData().setScale(0.1f);
        //startGameButton.setSize(30f,50f);
        /*startGameButton.getStyle().font.getData().setScale(0.1f);
        startGameButton.pack();*/
        table.add(startGameButton);//.height(20f).width(50f);
        //table.row();
        //startGameButton.getLabel().setFontScale(0.07f);
        //quitGameButton.setScale(0.8f);
        //table.add(quitGameButton).fillX().fillY();


        /*
        If the parent is the stage, the actor will be sized to the stage.
        This method is for convenience only when the widget's parent does not set the size of its children (such as the stage).
         */
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

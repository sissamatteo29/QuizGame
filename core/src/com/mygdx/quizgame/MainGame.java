package com.mygdx.quizgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.quizgame.DBManagement.Database;
import com.mygdx.quizgame.DBManagement.DesktopResult;
import com.mygdx.quizgame.DBManagement.Result;
import com.mygdx.quizgame.timehandling.Timer;

import javax.xml.crypto.Data;

public class MainGame extends Game {

	//references to interfaces that need to be se in the constructor to make DB connectivity platform-indipendent
	Database database;
	Result result;
	int quizAmount;

	//need a data structure to contain all the remaining YearIDs to display. At the beginning, this intArray needs to contain all of the YearIDs present
	IntArray yearIdAvailable;
	int userScore;
	Timer timer;

	SpriteBatch batch;
	Sprite animationSprite, backgroundSprite;
	Viewport viewport;

	Screen animationScreen, gameScreen, loadingScreen, pauseScreen, startScreen;

	TextButton startGame, quitGame, resumeGame, pauseGame, answer0, answer1, answer2, answer3;
	//array grouping all the answers
	TextButton[] answersButtonArray;
	Label yearLabel, timeLabel, scoreLabel;

	AssetManager assetManager;

	Skin skin;

	BitmapFont font;

	//Aspect Ratio 16:9
	final float WORLD_WIDTH = 45f;
	final float WORLD_HEIGHT = 80f;

	//to create the connection to the SQLlite database in a platform-indipendent environment,
	//we need to implement a constructor of this class that takes the concrete implementations of the interfaces and sets the references of the class
	public MainGame(Database database, Result result) {
		this.database = database;
		this.result = result;
		//We need to set connection and get the first important data from the database
		setDatabaseConnection();
	}

	private void setDatabaseConnection() {
		//simply open the connection (the Connection object is now stored inside the instance of desktopDatabase)
		database.openDatabase();

		//at the beginning of the application, we can already count the total number of quiz present in our database and store that value in a field of this class.
		//the count is made through a simple query to the YEAR table
		result = database.queryDatabase("SELECT COUNT(*) AS TOT_QUIZ FROM YEAR WHERE YEAR IS NOT NULL");

		//to retrieve the calculated value, I first need to move the resulset to the first row, then take the value:
		result.moveToNext();
		quizAmount = result.getInt(1);

		//DEBUG
		//Gdx.app.log("Number of Quiz", "The total number calculated is " + quizAmount);
		System.out.println("the total number of quiz is " + quizAmount);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, new OrthographicCamera());
		viewport.apply();
		batch.setProjectionMatrix(viewport.getCamera().combined);

		setManager();
		initScreens();

		Gdx.input.setInputProcessor(new MyInputHandler(this));

		setScreen(loadingScreen);

	}

	//this method will be automatically invoked by gameScreen Every time a new game starts
	public void gameSetter() {
		yearIdAvailable = new IntArray();
		for (int i = 0; i < quizAmount; i++){
			yearIdAvailable.add(i);
		}
		userScore = 0;
		timer = new Timer(2, 0);

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	//unutilized method for this Class
	@Override
	public void render () {
		super.render();

		//ScreenUtils.clear(250f / 255f, 250f / 255f, 200f / 255f, 0);
		//Gdx.gl20.glClearColor(250f / 255f, 250f / 255f, 200f / 255f, 0);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//this dispose() takes care of getting rid of all assets within AssetManager
		assetManager.dispose();

		animationScreen.dispose();
		gameScreen.dispose();
		startScreen.dispose();
		loadingScreen.dispose();
		pauseScreen.dispose();

		//close DB connection
		database.closeDatabase();
	}


	private void initScreens() {
		//note that the only screen which is not present is lastScreen, because it is parametrized based on the victory or defeat of the player
		animationScreen = new AnimationScreen(this);
		pauseScreen = new PauseScreen(this);
		startScreen = new StartScreen(this);
		gameScreen = new GameScreen(this);
		loadingScreen = new LoadingScreen(this);
	}

	private void setManager() {
		assetManager = new AssetManager();
		assetManager.load("Animation.png", Texture.class);
		assetManager.load("Background.jpg", Texture.class);
		assetManager.load("font.fnt", BitmapFont.class);
		assetManager.load("uiskin.json", Skin.class);
	}
}

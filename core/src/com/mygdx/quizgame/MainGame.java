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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGame extends Game {

	SpriteBatch batch;
	Sprite animationSprite, backgroundSprite;
	Viewport viewport;

	Screen animationScreen, gameOverScreen, gameScreen, loadingScreen, pauseScreen, startScreen;

	TextButton startGame, quitGame, resumeGame, pauseGame, answer1, answer2, answer3, answer4;

	AssetManager assetManager;

	Skin skin;
	Skin skin2;

	BitmapFont font;

	//Aspect Ratio 16:9
	final float WORLD_WIDTH = 45f;
	final float WORLD_HEIGHT = 80f;


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
		gameOverScreen.dispose();
		startScreen.dispose();
		loadingScreen.dispose();
		pauseScreen.dispose();
	}


	private void initScreens() {
		animationScreen = new AnimationScreen(this);
		gameOverScreen = new GameOverScreen(this);
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
		assetManager.load("level-plane-ui.json", Skin.class);
	}
}

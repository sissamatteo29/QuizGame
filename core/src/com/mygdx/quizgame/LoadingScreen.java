package com.mygdx.quizgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

import java.awt.*;

public class LoadingScreen extends ScreenAdapter {

    MainGame game;
    ShapeRenderer shapeRenderer;
    float progress;
    long endPauseTime;
    boolean first = true;

    public LoadingScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(new Color(1f,1f,1f,1f));

        //returns true if assetManager finished loadings
        if (game.assetManager.update()){
            if (first) {
                game.animationSprite = new Sprite(game.assetManager.get("Animation.png", Texture.class));
                game.backgroundSprite = new Sprite(game.assetManager.get("Background.jpg", Texture.class));
                game.font = game.assetManager.get("font.fnt", BitmapFont.class);
                game.skin = game.assetManager.get("uiskin.json", Skin.class);
                game.skin2 = game.assetManager.get("level-plane-ui.json", Skin.class);
                endPauseTime = System.currentTimeMillis() + 2000;
                first = false;
            }
            if (endPauseTime < System.currentTimeMillis()) {
                game.setScreen(game.startScreen);
            }

            game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
            game.batch.begin();
            game.font.getData().setScale(0.03f);
            game.font.setUseIntegerPositions(false);
            game.font.draw(game.batch, "Game Loaded", -game.viewport.getWorldWidth() / 2 + 11f, -game.viewport.getWorldHeight() / 2 + 22.5f);
            game.batch.end();
            //initButtons to do AFTER having loaded the skin
            initButtons();
        }

        progress = game.assetManager.getProgress();
        shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f,0f,0f,1f);
        shapeRenderer.rect(-game.viewport.getWorldWidth()/2 + 5f, -game.viewport.getWorldHeight()/2 + 15f, 35f, 3f);
        shapeRenderer.setColor(0f, 119f / 255f, 1f, 1f);
        shapeRenderer.rect(-game.viewport.getWorldWidth()/2 + 5.25f, -game.viewport.getWorldHeight()/2 + 15.25f, 34.5f * progress, 2.5f);
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        game.viewport.update(width, height);
    }

    @Override
    public void show() {
        super.show();
        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }


    private void initButtons() {
        game.startGame = new TextButton("New Game", game.skin, "default");
        game.quitGame = new TextButton("Quit", game.skin, "default");
        game.resumeGame = new TextButton("Resume", game.skin, "default");
        game.pauseGame = new TextButton("Pause", game.skin, "default");

        //empty buttons
        game.answer1 = new TextButton("", game.skin, "default");
        game.answer2 = new TextButton("", game.skin, "default");
        game.answer3 = new TextButton("", game.skin, "default");
        game.answer4 = new TextButton("", game.skin, "default");
    }
}

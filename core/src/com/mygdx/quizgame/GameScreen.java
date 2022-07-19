package com.mygdx.quizgame;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    MainGame game;

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.backgroundSprite.setSize(game.WORLD_WIDTH * 2, game.WORLD_HEIGHT * 2);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }



    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

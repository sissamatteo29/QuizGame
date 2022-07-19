package com.mygdx.quizgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyInputHandler implements InputProcessor {

    MainGame game;

    public MyInputHandler(MainGame game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP){
           ((OrthographicCamera) game.viewport.getCamera()).zoom += 0.4;
           ((OrthographicCamera) game.viewport.getCamera()).update();
        }
        if (keycode == Input.Keys.DOWN){
            ((OrthographicCamera) game.viewport.getCamera()).zoom -= 0.4;
            ((OrthographicCamera) game.viewport.getCamera()).update();
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

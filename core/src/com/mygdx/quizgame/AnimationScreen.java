package com.mygdx.quizgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class AnimationScreen extends ScreenAdapter {

    MainGame game;

    private Stage stage;
    private Image image;
    //boolean first = true;

    public AnimationScreen(MainGame game) {
        super();
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(1f,1f,1f,1f);
        stage.act();
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
        image = new Image(game.animationSprite);
        image.setSize(12.5f, 12.5f);
        image.setPosition(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        stage.addActor(image);
        image.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(3f, 3f, 2.5f, Interpolation.pow5),
                        moveTo(3.3f, 20.5f, 2f, Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.startScreen);
                    }
                })));

        stage.addActor(image);
    }


    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

}

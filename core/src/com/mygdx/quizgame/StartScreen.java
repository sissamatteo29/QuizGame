package com.mygdx.quizgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartScreen extends ScreenAdapter {

    MainGame game;

    Stage stage;
    Table table;

     ExtendViewport viewport;

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
        viewport.update(width, height);
    }
    

    @Override
    public void show() {
        super.show();

        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();
        stage = new Stage(viewport);
        table = new Table();

        game.startGame.getStyle().font.getData().setScale(0.5f);
        game.startGame.getLabel().setSize(1000f, 4000f);
        table.add(game.startGame).bottom().expand();
        table.row();
        game.quitGame.getLabel().setFontScale(0.3f);
        game.quitGame.getLabelCell().height(60f).width(200f);
        table.add(game.quitGame).top().padBottom(200f).padTop(100f);

        /*
        If the parent is the stage, the actor will be sized to the stage.
        This method is for convenience only when the widget's parent does not set the size of its children (such as the stage).
         */

        //table.setDebug(true);
        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
        setInput();
    }

    private void setInput() {
        game.startGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.gameScreen);
            }
        });

        game.quitGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
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

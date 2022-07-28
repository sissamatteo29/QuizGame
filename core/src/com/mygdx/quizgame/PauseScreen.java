package com.mygdx.quizgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class PauseScreen extends ScreenAdapter {

    MainGame game;

    Stage stage;
    Table table;

    TextButton resumeButton, quitButton;
    Label infoLabel;

    public PauseScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(1,1,1,1);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        super.show();

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()));
        stage.getViewport().apply();
        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);

        infoLabel = new Label("The Game is Paused", game.skin);
        infoLabel.setFontScale(0.35f);
        resumeButton = new TextButton("Resume Game", game.skin);
        resumeButton.getLabel().setFontScale(0.4f);
        quitButton = new TextButton("Quit Game", game.skin);
        quitButton.getLabel().setFontScale(0.3f);

        table.add(infoLabel).padBottom(50f).padTop(200f);
        table.row();
        table.add(resumeButton).width(370).height(80).padBottom(100f);
        table.row();
        table.add(quitButton).width(200f);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        setButtonResponses();

    }

    private void setButtonResponses() {

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(game.gameScreen);
            }
        });

    }


    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}

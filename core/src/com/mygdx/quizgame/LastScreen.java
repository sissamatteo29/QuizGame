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

public class LastScreen extends ScreenAdapter {

    MainGame game;
    FinalScreenEnums isVictory;
    int finalScore;

    Stage stage;
    Table table;

    TextButton quitButton, playAgainButton;
    Label scoreInfosLabel, victoryOrDefeatLable;

    public LastScreen(MainGame game, FinalScreenEnums isVictory, int finalScore) {
        super();
        this.game = game;
        this.isVictory = isVictory;
        this.finalScore = finalScore;
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

        if (isVictory == FinalScreenEnums.VICTORY) {
            victoryOrDefeatLable = new Label("YOU WON", game.skin);
        } else {
            victoryOrDefeatLable = new Label("YOU LOST", game.skin);
        }
        victoryOrDefeatLable.setFontScale(0.6f);

        scoreInfosLabel = new Label("Your final score is: " + Integer.toString(finalScore), game.skin);
        scoreInfosLabel.setFontScale(0.25f);
        playAgainButton = new TextButton("Play Again", game.skin);
        playAgainButton.getLabel().setFontScale(0.25f);
        quitButton = new TextButton("Quit Game", game.skin);
        quitButton.getLabel().setFontScale(0.25f);

        table.add(victoryOrDefeatLable).colspan(2).padBottom(50f).padTop(200f);
        table.row();
        table.add(scoreInfosLabel).colspan(2).padBottom(100f);
        table.row();
        table.add(playAgainButton).width(160f).padRight(40f).expandY();
        table.add(quitButton).width(160f).expandY();

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

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.gameScreen = new GameScreen(game);
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
        super.dispose();
        stage.dispose();

    }
}

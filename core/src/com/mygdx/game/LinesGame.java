package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.results.AchivementsList;

public class LinesGame extends Game {
    private SpriteBatch batch; //область отрисовки
    private GameScreen gameScreen;
    private Viewport viewport ;
    private AchivementsList achivementsList;

    @Override
    public void create () {
        batch = new SpriteBatch();
        viewport   = new FitViewport(1280,720);

        // создаем достижения
        achivementsList = new AchivementsList(this);
        achivementsList.generateAchivemnets();

//        setScreen(gameScreen);
        setScreen(new MainMenuScreen(this));
    }

    public void setGameScreen() {
        gameScreen = new GameScreen(this,batch);
        setScreen(gameScreen);
    }

    @Override
    public void render () {
        float dt = Gdx.graphics.getDeltaTime();
        //update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getScreen().render(dt);
    }

    @Override
    public void dispose () {
        batch.dispose();
        getScreen().dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }
}

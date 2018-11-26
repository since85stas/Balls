package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LinesGame extends Game {
    private SpriteBatch batch; //область отрисовки
    private   GameScreen gameScreen;
    private Viewport viewport ;

    @Override
    public void create () {
        batch = new SpriteBatch();
        gameScreen = new GameScreen(this,batch);
        viewport   = new FitViewport(1280,720);

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

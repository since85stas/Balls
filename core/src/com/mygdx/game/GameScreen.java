package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.util.Constants;

public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;

    private  LinesGame lineGame;
    private  SpriteBatch batch ;
    GameField gameField ;



	public  GameScreen (LinesGame lineGame,SpriteBatch batch){
		this.lineGame = lineGame;
		this.batch      = batch ;
	}

    @Override
    public void show() {
	    gameField = new GameField(this);
    }

    @Override
    public void render(float delta) {

        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            update(Constants.TIME_STEP);
            accumulator -= Constants.TIME_STEP;
        }

        batch.begin();

        gameField.render(batch);
        float fps = 1 / delta;
        Gdx.app.log(TAG,"fps =" + fps);

        batch.end();
    }

    public void update (float dt) {

	    gameField.update(dt);
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}

    @Override
    public void hide() {
        batch.dispose();
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    @Override
    public void resize(int width, int height) {

    }
}

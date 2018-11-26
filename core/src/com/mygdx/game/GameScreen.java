package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.util.Constants;

public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;
    private float gametime ;

    private  LinesGame lineGame;
    private  SpriteBatch batch ;
    GameField gameField ;


    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // Add BitmapFont
    BitmapFont font;

	public  GameScreen (LinesGame lineGame,SpriteBatch batch){
		this.lineGame = lineGame;
		this.batch      = batch ;
	}

    @Override
    public void show() {

	    gameField = new GameField(this);

        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        //  Initialize the BitmapFont
        font = new BitmapFont();

        //  Give the font a linear TextureFilter
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
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

        hudViewport.apply();

        // Set the SpriteBatch's projection matrix
        batch.setProjectionMatrix(hudViewport.getCamera().combined);

        batch.begin();

        //gameField.update(delta);
        gameField.render(batch, delta);

        // Draw the number of player deaths in the top left
        font.setColor(Color.CYAN);
        gametime +=delta;

        font.draw(batch, "Score: " + gameField.getGameScore() + "  Time: " + String.format("%f",gametime),
                Constants.HUD_MARGIN, Gdx.graphics.getWidth() + Constants.HUD_MARGIN);


        float fps = 1 / delta;
        Gdx.app.log(TAG,"fps =" + fps);

        batch.end();
    }

    public void update (float dt) {


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

        // Update HUD viewport
        hudViewport.update(width, height, true);

        // Set font scale to min(width, height) / reference screen size
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }
}

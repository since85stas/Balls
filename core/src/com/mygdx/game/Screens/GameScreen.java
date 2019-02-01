package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameField;
import com.mygdx.game.LinesGame;
import com.mygdx.game.results.AchivementsList;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

public class GameScreen implements Screen {

    private static final String TAG = GameScreen.class.getName();

    private float accumulator = 0;
    private float gametime ;
    private Stage stage;

//    private Skin mySkin;

    public LinesGame lineGame  ;
    private  SpriteBatch batch ;
    public GameField gameField ;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // Add BitmapFont

	public  GameScreen (LinesGame lineGame,SpriteBatch batch){
		this.lineGame = lineGame;
		this.batch      = batch ;
	}

    @Override
    public void show() {
        stage = new Stage();

	    gameField = new GameField(this);

        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        Container cont = new Container(itemHudLable("title",10));
        cont.setPosition(50,400);
        stage.addActor(cont);
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
//        batch.setProjectionMatrix(hudViewport.getCamera().combined);

//        batch.disableBlending();
        batch.begin();

        //gameField.update(delta);
        gameField.render(batch, delta);

        // Draw the number of player deaths in the top left
//        font.setColor(Color.CYAN);
        gametime = gameField.getGameTime();

//        font.draw(batch, "Score: " + gameField.getGameScore() + "  Time: " + String.format("%f",gametime),
//                Constants.HUD_MARGIN, Gdx.graphics.getWidth() + Constants.HUD_MARGIN);

        if (frameTime % 0.5 == 0) {
            float fps = 1 / delta;
            Gdx.app.log(TAG, "fps =" + fps);
        }
        batch.end();

        stage.draw();
    }

    private VerticalGroup itemHudLable(String title, int digit ) {

	    Skin skin = Assets.instance.skinAssets.skin;

        Label titleLable =  new Label(title,skin,"small");
        Label digitLable = new Label(Integer.toString(digit),skin,"title");

        VerticalGroup group = new VerticalGroup().pad(100);
        group.addActor(titleLable);
        group.addActor(digitLable);
	    return group;
    }

    public void update (float dt) {

    }
	
	@Override
	public void dispose () {
		batch.dispose();
		gameField.dispose();
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
//        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }
}

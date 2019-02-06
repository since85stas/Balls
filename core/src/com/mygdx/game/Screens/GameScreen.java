package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameField;
import com.mygdx.game.LinesGame;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import java.util.Date;

public class GameScreen implements Screen {

    //
    private static final String TAG = GameScreen.class.getName();

    //
    private float accumulator = 0;
    private float gametime ;
    private Stage stage;

    //
    public LinesGame lineGame  ;
    private  SpriteBatch batch ;
    public GameField gameField ;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    private int width;
    private int height;

    public float lableItemHeight;

    Label timeLable;
    Label scoreLable;

	public  GameScreen (LinesGame lineGame,SpriteBatch batch){
		this.lineGame = lineGame;
		this.batch      = batch ;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

    @Override
    public void show() {
        stage = new Stage();

        // Initialize the HUD viewport
        hudViewport = new ScreenViewport();

        VerticalGroup scoreLable = scoreHudLable("score",0);
        scoreLable.setPosition(2*Constants.HUD_OFFSET *width,
                height - lableItemHeight   );
        scoreLable.setSize(Constants.HUD_ITEM_HOR_SIZE*width,lableItemHeight);

        VerticalGroup timeLable = timeHudLable("time",100);
        timeLable.setPosition(width - 2*Constants.HUD_OFFSET *width - Constants.HUD_ITEM_HOR_SIZE*width,
                height - lableItemHeight  );
        timeLable.setSize(Constants.HUD_ITEM_HOR_SIZE*width,lableItemHeight);

        stage.addActor(scoreLable);
        stage.addActor(timeLable);

        gameField = new GameField(this);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.3f, 0.47f, 0.6f, 0.8f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        gametime = gameField.getGameTime();

        if (frameTime % 0.5 == 0) {
            float fps = 1 / delta;
            Gdx.app.log(TAG, "fps =" + fps);
        }

        int  time = (int)gameField.getGameTime();
        String timeString = "";
        if (time < 60 ) {
            timeString = Integer.toString(time);
        } else if ( time > 60 && time < 3600) {
            int min = (int) (time / 60);
            int sec = time%60;
            if (sec > 9) {
                timeString = "0" + Integer.toString(min) + ":" + Integer.toString(sec);
            }  else {
                timeString = "0" + Integer.toString(min) + ":0" + Integer.toString(sec);
            }
        }
        timeLable.setText(timeString);
        int score = gameField.getGameScore();
        String scoreString = "";

        if (score < 10) {
            scoreString = "0000" +  Integer.toString(score)   ;
        } else if (score>=10 && score < 100) {
            scoreString = "000" +  Integer.toString(score)    ;
        } else if (score>=100 && score < 1000) {
            scoreString = "00" +  Integer.toString(score)     ;
        } else if (score>=1000 && score < 10000) {
            scoreString = "0" +  Integer.toString(score)      ;
        } else if (score>=10000 && score < 100000) {
            scoreString = "" +  Integer.toString(score)       ;
        }
        scoreLable.setText(scoreString );
        batch.end();

        stage.draw();
    }

    private VerticalGroup timeHudLable(String title, int digit ) {

        Skin skin = Assets.instance.skinAssets.skin;
        Label titleLable =  new Label(title,skin,"small");
        timeLable = new Label(Integer.toString(digit),skin,"title");
        float size1 = titleLable.getHeight();
        float size2 = timeLable.getHeight();
        lableItemHeight = size1 + size2;
        VerticalGroup group = new VerticalGroup();
        group.addActor(titleLable);
        group.addActor(timeLable);
        return group;
    }

    private VerticalGroup scoreHudLable(String title, int digit ) {

        Skin skin = Assets.instance.skinAssets.skin;
        Label titleLable =  new Label(title,skin,"small");
        scoreLable = new Label(Integer.toString(digit),skin,"title");
        float size1 = titleLable.getHeight();
        float size2 = scoreLable.getHeight();
        lableItemHeight = size1 + size2;
        VerticalGroup group = new VerticalGroup();
        group.addActor(titleLable);
        group.addActor(scoreLable);
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

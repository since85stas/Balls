package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.LinesGame;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

/**
 * Created by seeyo on 04.12.2018.
 */

public class MainMenuScreen extends InputAdapter implements Screen{

    private static final float BUTTONS_HEIGHT = 0.1f;
    private static final float BUTTONS_WIDTH = 0.6f;
    private static final float TITLE_UPPER_OFFSET = 0.1f;
    private static final float BUTTONS_UPPER_OFFSET = 0.3f;
    private static final float BUTTONS_BETWEEN_SPACE = 0.05f;

    private static final int numButtons = 4;

    private static final String[] buttonNames = {
            "continue",
            "new game",
            "achievements",
            "exit"
    };

    SpriteBatch batch;
//    BitmapFont hudFont;
    LinesGame mGame;

    private Stage stage;
    private Skin mySkin;
    int widtht;
    int height;

    public MainMenuScreen(LinesGame game) {
        mGame = game;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        widtht = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
//        mySkin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        mySkin = Assets.instance.skinAssets.skin;
//        mySkin.
        generateButtons();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.47f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fps = 1 / delta;
        Gdx.app.log("preLevel","fps =" + fps);

        stage.act();
        stage.draw();

        batch.begin();
        batch.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
//        hudFont.dispose();
    }

    private void generateButtons() {

        Label titleLable = new Label("LINES 2019",mySkin,"menu");
        titleLable.layout();
        GlyphLayout lay = new GlyphLayout();
        lay.setText(mySkin.getFont("menu-font"),"LINES 2019");
        int len = (int)lay.width;
//        titleLable.getGlyphLayout().setText(mySkin.getFont("menu-font"),"mew");
//        titleLable
//        int len = (int)titleLable.getGlyphLayout();
        titleLable.setPosition((widtht - len)/2,height - 2*TITLE_UPPER_OFFSET*height);
        stage.addActor(titleLable);

        TextButton[] buttons = new TextButton[numButtons];

        float buttonX = (widtht - widtht*BUTTONS_WIDTH)/2;
        float buttonY = height - height*BUTTONS_UPPER_OFFSET;

//        TextButton.TextButtonStyle style = mySkin.get

        for (int i = 0; i < buttons.length; i++) {
            buttonY -= height*BUTTONS_HEIGHT + height*BUTTONS_BETWEEN_SPACE;

            buttons[i] = new TextButton(buttonNames[i],mySkin,"default");

            if(!mGame.findSaveGame) {
                buttons[0].setDisabled(true);
            }
            buttons[i].setSize(widtht*BUTTONS_WIDTH,height*BUTTONS_HEIGHT);

            buttons[i].setPosition(buttonX,buttonY);

            int finalI = i;
            buttons[i].addListener(new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("PreScreen","Pressed");
                    switch (finalI){
                        case 0:
                            mGame.setGameScreen();
                            break;
                        case 1:
                            mGame.findSaveGame = false;
                            mGame.setGameScreen();
                            break;
                        case 2:
                            mGame.setAchieveScreen();
                            break;
                        case 3:
                            break;
                    }

                    return true;
                }
            });
            stage.addActor(buttons[i]);
        }
    }
//    }

    private void buttonPressed(int button) {

    }

}

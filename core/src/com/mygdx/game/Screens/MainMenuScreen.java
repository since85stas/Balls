package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private static final float BUTTONS_UPPER_OFFSET = 0.2f;
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
//        BitmapFont font = generateHudFont();
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 12;
//        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
//        generator.dispose(); // don't forget to dispose to avoid memory leaks!
//        hudFont = generateHudFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fps = 1 / delta;
        Gdx.app.log("preLevel","fps =" + fps);

        stage.act();
        stage.draw();

        batch.begin();
//        hudFont.draw(batch,
//                "click to listen",
//                widtht/2,
//                height );
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
        TextButton[] buttons = new TextButton[numButtons];

        float buttonX = (widtht - widtht*BUTTONS_WIDTH)/2;
        float buttonY = height - height*BUTTONS_UPPER_OFFSET;

//        TextButton.TextButtonStyle style = mySkin.get

        for (int i = 0; i < buttons.length; i++) {
            buttonY -= height*BUTTONS_HEIGHT + height*BUTTONS_BETWEEN_SPACE;

            buttons[i] = new TextButton(buttonNames[i],mySkin,"default");
//            TextButton.TextButtonStyle style = buttons[i].getStyle();
//            style.font = mySkin.getFont("newFont");
//            buttons[i] = new TextButton(buttonNames[i],style);
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

//    private BitmapFont generateHudFont() {
//        BitmapFont font = new BitmapFont();
//        FreeTypeFontGenerator generator =
//                new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
//                new FreeTypeFontGenerator.FreeTypeFontParameter();
////            parameter.size = (int)(height - (height*(1-Constants.HUD_UP_SIZE)
////                    + Constants.HUD_MARGIN_UP_RATIO*height) );
////        parameter.size = 20;
////        parameter.borderColor = Color.BLACK;
////        parameter.borderWidth = 2;
////        parameter.shadowOffsetX = 3;
////        parameter.shadowOffsetY = -3;
////        parameter.shadowColor = Color.BLACK;
//
//        font = generator.generateFont(parameter);
//        return font;
//    }

    private void buttonPressed(int button) {

    }

}

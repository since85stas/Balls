package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameField;
import com.mygdx.game.LinesGame;
import com.mygdx.game.Widgets.PagedScrollPane;
import com.mygdx.game.util.Assets;

public class AchieveScreen implements Screen {

    public LinesGame lineGame;
    private SpriteBatch batch ;
    private Stage stage;

    // Add ScreenViewport for HUD
    ScreenViewport hudViewport;

    // Add BitmapFont
    BitmapFont font;

    private Skin mySkin;
    private Table container;

    public  AchieveScreen (LinesGame lineGame,SpriteBatch batch){
        this.lineGame = lineGame;
        this.batch      = batch ;
    }

    @Override
    public void show() {
        mySkin = Assets.instance.skinAssets.skin;

        font = Assets.instance.skinAssets.skin.getFont("font");
        font.getData().setScale(0.01f,0.01f);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        PagedScrollPane scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(25);

//        int c = 1;
//        for (int l = 0; l < 10; l++) {
//            Table levels = new Table().pad(50);
//            levels.defaults().pad(20, 40, 20, 40);
//            for (int y = 0; y < 3; y++) {
//                levels.row();
//                for (int x = 0; x < 4; x++) {
//                    levels.add(getLevelButton(c++)).expand().fill();
//                }
//            }
//            scroll.addPage(levels);
//        }

        String[] achievsDescr = lineGame.achivementsList.getAchievDescrArray();
        Button[] acievmentsItems = new Button[achievsDescr.length];

        Table achievments = new Table().pad(100);

        for (int i = 0; i < achievsDescr.length; i++) {
            acievmentsItems[i] = createAchieveItem(achievsDescr[i],i);
            achievments.add(acievmentsItems[i]);
            achievments.row();
        }

        scroll.addPage(achievments);

        container = new Table();
        stage.addActor(container);
        container.setFillParent(true);
        container.add(scroll).expand().fill();
    }

    public Button createAchieveItem(String descr, int number) {
        Button button = new Button(mySkin);
        Button.ButtonStyle style = button.getStyle();
        style.up = 	style.down = null;

        // Create the label to show the level number
        Label label = new Label(descr, mySkin);
        label.setFontScale(2f);
        label.setAlignment(Align.center);

        // create image
        Image image = new Image(Assets.instance.blueBallAssets.texture);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(image);
        table.add(label);

        button.add(table);

        button.setName("item " + number);
//        button.addListener(levelClickListener);
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
//        Table(stage);
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

    }

    @Override
    public void dispose() {

    }


}

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.AchieveScreen;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.results.AchivementsList;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import java.util.Hashtable;

public class LinesGame extends Game {
    private SpriteBatch batch; //область отрисовки
    private GameScreen gameScreen;
    private Viewport viewport ;
    public AchivementsList achivementsList;
    public boolean findSaveGame;

    @Override
    public void create () {

        batch = new SpriteBatch();
//        viewport   = new FitViewport(1280,720);

        // создаем текстуры
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        setMainMenuScreen();
    }

    public void setMainMenuScreen() {

        // создаем достижения
        achivementsList = new AchivementsList(this);
        achivementsList.generateAchivemnets();
        loadAchieve();

        setScreen(new MainMenuScreen(this));
    }

    public void setGameScreen() {
        gameScreen = new GameScreen(this,batch);
        setScreen(gameScreen);
    }

    public void setAchieveScreen() {
        AchieveScreen screen = new AchieveScreen(this,batch);
        setScreen(screen);
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

    public void saveAchieve() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREF_ACHIEV);

        Hashtable<String, String> hashTable = new Hashtable<String, String>();
        Json json = new Json();
        hashTable.put(Constants.PREF_ACHIEV_MASSIVE, json.toJson(achivementsList.getAchievCompArray()) ); //here you are serializing the array
        prefs.put(hashTable);

        prefs.flush();
    }

    public void loadAchieve() {
        Preferences gamePref = Gdx.app.getPreferences(Constants.PREF_GAME);
        findSaveGame = gamePref.getBoolean(Constants.PREF_GAME_IS_PLAY,false);
        Preferences prefs = Gdx.app.getPreferences(Constants.PREF_ACHIEV);

        Json json = new Json();
        String serializedInts = prefs.getString(Constants.PREF_ACHIEV_MASSIVE);
        int[] deserializedInts = json.fromJson(int[].class, serializedInts); //you need to pass the class type - be aware of it!

    }

    public Viewport getViewport() {
        return viewport;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
}

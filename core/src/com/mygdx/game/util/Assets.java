package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;


import javax.xml.soap.Text;
import java.util.HashMap;

/**
 * Created by seeyo on 03.12.2018.
 */

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    private Assets() {
    }

//    public EnemyAssets enemyAssets;
    public BlueBallAssets blueBallAssets;
    public GreenBallAssets greenBallAssets;
    public PurpleBallAssets purpleBallAssets;
    public YellowBallAssets yellowBallAssets;
    public TileAssets tileAssets;
    public SkinAssets skinAssets;

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load("sphere_blue.png", Texture.class);
        assetManager.load("sphere_green.png",Texture.class);
        assetManager.load("sphere_purle.png",Texture.class);
        assetManager.load("sphere_yellow.png",Texture.class);
        assetManager.load("green_rock.png"   ,Texture.class);
        assetManager.load("mini_brown_rock.png",Texture.class);
//        assetManager.load("skin/craftacular-ui.json",Skin.class);


        // loading skin with parameters
        java.util.Map<String,BitmapFont> fontsByName = initFonts();
        Gdx.app.log(TAG,"before AssertManager>>>>>>>>>>>>" );
        SkinLoader ldr =  new GeneratedFontSkinLoader( new InternalFileHandleResolver() {
            @Override
            public FileHandle resolve(String fileName) {
                Gdx.app.log(TAG,"AssertManager>>>>>>>>>>>>" + fileName);
                return super.resolve(fileName);
            }
        }, fontsByName);
        assetManager.setLoader( Skin.class, ldr );
        assetManager.load( "skin/craftacular-ui.atlas", TextureAtlas.class );
        assetManager.load( "skin/craftacular-ui.json", Skin.class );

        assetManager.finishLoading();

        Texture blueBallTexture = assetManager.get  ("sphere_blue.png");
        Texture greenBallTexture = assetManager.get ("sphere_green.png");
        Texture purleBallTexture = assetManager.get ("sphere_purle.png");
        Texture yellowBallTexture = assetManager.get("sphere_yellow.png");
        Texture tileTexture = assetManager.get("mini_brown_rock.png");
        Skin mySkin = assetManager.get("skin/craftacular-ui.json");
//        enemyAssets = new EnemyAssets(walkTexture);
        blueBallAssets   = new BlueBallAssets(blueBallTexture);
        greenBallAssets  = new GreenBallAssets(greenBallTexture);
        purpleBallAssets = new PurpleBallAssets(purleBallTexture);
        yellowBallAssets = new YellowBallAssets(yellowBallTexture);
        tileAssets       = new TileAssets(tileTexture);
        skinAssets       = new SkinAssets(mySkin);
//        crosshairAssets = new CrosshairAssets(crossTexture);


    }

    protected java.util.Map<String,BitmapFont> initFonts() {

        Gdx.app.log( "INIT", "Loading fonts..." );

        FileHandle fontFile = Gdx.files.internal("zorque.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        java.util.Map<String,BitmapFont> fontsByName = new HashMap<>();
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        float ppi = Gdx.graphics.getPpiY();
//        param.size = Math.round( ppi / 2);
        param.size = 32;
        fontsByName.put( "huge-font", generator.generateFont( param ));
//        param.size = Math.round( ppi / 3);
        param.size = 22;
        fontsByName.put( "big-font", generator.generateFont( param ));
//        param.size = Math.round( ppi / 4);
        param.size = 12;
        fontsByName.put( "small-font", generator.generateFont( param ));

        generator.dispose();
        return fontsByName;
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }


    public class TileAssets {
        private static final int FRAME_COLS = 3; // #1
        private static final int FRAME_ROWS = 1; // #2

//        public final TextureRegion targetTexture;
        public Texture texture;

        public TileAssets (Texture texture) {
//            TextureRegion[][] tmp = TextureRegion.split(texture,
//                    texture.getWidth()/FRAME_COLS,
//                    texture.getHeight()/FRAME_ROWS); // #10
//
//            targetTexture = tmp[0][0];
            this.texture = texture;
            Gdx.app.log(TAG,"animation load");
        }
    }

    public class BrokenAssets {

        //        public final Animation<TextureRegion> walkAnimation;
        public final Texture brokenTexture;
        TextureRegion[] walkFrames; // #5
        SpriteBatch spriteBatch; // #6
        TextureRegion currentFrame; //

        public BrokenAssets (Texture texture) {
            brokenTexture = texture;

            Gdx.app.log(TAG,"animation load");
        }
    }

    public class BlueBallAssets {

        public Texture texture;

        public BlueBallAssets(Texture texture) {
            this.texture = texture;
            Gdx.app.log(TAG,"animation load");
        }

    }

    public class GreenBallAssets {

        public Texture texture;

        public GreenBallAssets(Texture texture) {
            this.texture = texture;
            Gdx.app.log(TAG,"animation load");
        }

    }

    public class PurpleBallAssets {

        public Texture texture;

        public PurpleBallAssets(Texture texture) {
            this.texture = texture;
            Gdx.app.log(TAG,"animation load");
        }

    }

    public class YellowBallAssets {

        public Texture texture;

        public YellowBallAssets(Texture texture) {
            this.texture = texture;
            Gdx.app.log(TAG,"animation load");
        }
    }

    public class SkinAssets {

        public Skin skin;

        public SkinAssets(Skin skin) {
//            BitmapFont font = generateHudFont();
            this.skin = skin;

//            skin.add("newFont", font, BitmapFont.class);

//            skin.
        }

    }




}

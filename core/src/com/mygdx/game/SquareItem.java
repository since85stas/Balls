package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SquareItem {
    private GameScreen gameScreen;
    private Texture texture;

    private Vector2 position;

    // item dimensions
    private int width ;
    private int height;

    public SquareItem (GameScreen gameScreen, int width, int height, Vector2 position ){
        this.gameScreen = gameScreen;
        this.width = width;
        this.height = height;
        texture = new Texture("green_rock.png");
        this.position = position;
    }

    public void   render (SpriteBatch batch) {
        batch.draw(texture,0,0);

    }
}

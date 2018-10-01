package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ball {
    private GameScreen gameScreen;
    private Texture texture;

    private Vector2 position;

    // ball dimensions
    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    public Ball (GameScreen gameScreen){
        this.gameScreen = gameScreen;
        //texture = new Texture("sphere_blue.png");
        this.position = new Vector2(0,0);
    }


}

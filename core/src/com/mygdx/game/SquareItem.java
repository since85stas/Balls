package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SquareItem {
    private GameScreen gameScreen;
    private Texture textureSquare;
    private Texture textureBall;
    public Rectangle hitBox;

    private Vector2 position;
    private Vector2 ballPosition;

    // item dimensions
    private int width ;
    private int height;

    // ball parameters
    private boolean hasBall = true;
    private boolean isActive = true;
    private float ballOfSquare = 0.7f;
    private float ballactiveTime;

    public SquareItem (GameScreen gameScreen, int width, int height, Vector2 position ){
        this.gameScreen = gameScreen;
        this.width = width;
        this.height = height;
        textureSquare = new Texture("green_rock.png");
        this.position = position;
        this.hitBox = new Rectangle(position.x,position.y,width,height);
        float ballPositionDel = (width - width*ballOfSquare)/2;
        ballPosition = new Vector2(position.x + ballPositionDel,position.y + ballPositionDel);

    }

    public void   render (SpriteBatch batch) {
        //batch.draw(textureSquare,position.x,position.y);
        batch.draw(textureSquare,position.x,position.y,width,height);

        if (hasBall) {
            batch.draw(drawBall(0),ballPosition.x,ballPosition.y
                    ,width*ballOfSquare,height*ballOfSquare);
        }
    }

    public void update(float dt) {
        if (isActive)  {
            ballactiveTime += dt;
            ballPosition.y = ballPosition.y + (float)Math.sin(ballactiveTime);
        }
        //if (Gdx.input.isCursorCatched())
    }

    public Texture drawBall (int ballColor) {
        textureBall = new Texture("sphere_blue.png");
        return textureBall;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public boolean isHasBall() {
        return hasBall;
    }
}

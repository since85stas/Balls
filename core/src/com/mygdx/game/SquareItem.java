package com.mygdx.game;

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
    private boolean hasBall  = false;



    private boolean isActive = false;
    private float ballOfSquare = 0.7f;
    private float ballactiveTime ;


    private int   ballColor      ;


    // collision parameters
    private float ballVelocity         = 0.01f ;
    private float ballDeformationVelocity = 0.02f;
    private float ballDeformationRatio = 0.1f;
    private float ballDeformation  ;
    private float upperWallOffset = 0.1f;
    private boolean afterCollision = false;
    private boolean stopCollision = false;


    public SquareItem (GameScreen gameScreen, int width, int height, Vector2 position ){
        this.gameScreen = gameScreen;
        this.width = width;
        this.height = height;
        textureSquare = new Texture("mini_brown_rock.png");
        this.position = position;
        this.hitBox = new Rectangle(position.x,position.y,width,height);
        float ballPositionDel = (width - width*ballOfSquare)/2;
        ballPosition = new Vector2(position.x + ballPositionDel,position.y + ballPositionDel);
    }



    public void   render (SpriteBatch batch) {
        //batch.draw(textureSquare,position.x,position.y);
        batch.draw(textureSquare,position.x,position.y,width,height);

        if (hasBall) {
            batch.draw(drawBall(ballColor),ballPosition.x,ballPosition.y
                    ,width*ballOfSquare,height*ballOfSquare + ballDeformation);
        }
    }

    public void update(float dt) {
        if (isActive && hasBall)  {
            changeBallPosition(dt);
        }
        //if (Gdx.input.isCursorCatched())
    }

    public void changeBallPosition ( float dt ) {
        ballactiveTime += dt;

        if ( ballPosition.y - position.y <= 0 && !stopCollision ) {
            if (  Math.abs(ballDeformation) >= height*ballDeformationRatio) {
                ballVelocity = ballVelocity*(-1);
                ballDeformationVelocity = ballDeformationVelocity*(-1);
                afterCollision = true;
            } else if (ballDeformation > 0 && afterCollision) {
                stopCollision = true;
            }
            ballPosition.y = position.y;
            ballDeformation -= ballDeformationVelocity*height;
//            System.out.println("ooops");
        } else if  ( ballPosition.y + height*ballOfSquare - position.y > height - height*upperWallOffset ) {
            ballVelocity = ballVelocity*(-1);
            ballDeformationVelocity = ballDeformationVelocity*(-1);
            ballPosition.y = ballPosition.y -(width*ballVelocity);
            stopCollision = false;
            afterCollision = false;
        } else {
            ballPosition.y = ballPosition.y -(width*ballVelocity);
        }
    }

    public void setBallInCenter () {
        float ballPositionDel = (width - width*ballOfSquare)/2;
        ballPosition = new  Vector2(position.x + ballPositionDel,position.y + ballPositionDel);
    }

    public Texture drawBall (int ballColor) {
        textureBall = new Texture(getBallColor(ballColor));
        return textureBall;
    }

    private String getBallColor( int colorId ) {
        String textureName = null;
        switch (colorId) {
            case 0:
                textureName = "mini_sphere_blue.png";
                break;
            case 1:
                textureName = "mini_sphere_green.png";
                break;
            case 2:
                textureName = "mini_sphere_purle.png";
                break;
            case 3:
                textureName = "mini_sphere_yellow.png";
                break;
        }
        return textureName;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public boolean isHasBall() {
        return hasBall;
    }

    public int getBallColor() {
        return ballColor;
    }

    public void setBallColor(int ballColor) {
        this.ballColor = ballColor;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

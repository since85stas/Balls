package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameField {
    private GameScreen gameScreen;
    private Texture texture;

    private Vector2 position;

     // item dimensions
    private int itemWidth;


    private int fieldDimension = 9 ;

    private SquareItem[][] squares ;

    public GameField (GameScreen gameScreen ){
        this.gameScreen = gameScreen;

        texture = new Texture("bg.png");

        //this.position = new Vector2(0,0);
        int screenWidth = Gdx.graphics.getWidth();

        itemWidth = (int)screenWidth/ fieldDimension;

        squares = new SquareItem[fieldDimension][fieldDimension];
        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int x = j*itemWidth;
                int y = i*itemWidth;
                Vector2 position = new Vector2(x,y);
                squares[j][i] = new SquareItem(gameScreen,itemWidth,itemWidth,position);
            }
        }
    }

    public void   render (SpriteBatch batch) {
        batch.draw(texture,0,0);

        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                //squares[i][j].render(batch);
            }
        }
    }
}

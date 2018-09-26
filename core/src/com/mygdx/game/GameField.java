package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class GameField {
    private GameScreen gameScreen;
    private Texture texture;

    private Vector2 position;

     // item dimensions
    private int itemWidth;

    // game parameters
    private int fieldDimension = 9 ;
    private int numberOfAiBalls = 3;
    private int numberOfColors  = 4;

    // массив с ячейками
    private SquareItem[][] squares ;

    private boolean isAiTurn;

    public float gameTime;

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
                squares[i][j].render(batch);
            }
        }
    }

    public void update( float dt) {

        // время игры
        gameTime += dt;

        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                squares[i][j].update(dt);
            }
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    // do something
                    for (int i = 0; i < fieldDimension ; i++) {
                        for (int j = 0; j < fieldDimension; j++) {

                            //проверяем попал ли щелчок в ячейку и помещяем туда шарик
                            if (squares[i][j].hitBox.contains(screenX,Gdx.graphics.getHeight() - screenY)) {
                               aiTurn();
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /* компьютер выбирает шарики и кладет их в рандомные ячейки
       пока сделано для 3 шариков
     */
    public void aiTurn () {

        Vector2[] freeSquares = checkSquares();
        for (int i = 0; i < numberOfAiBalls ; i++) {
            int random = MathUtils.random(0,freeSquares.length-1);

            squares[(int)freeSquares[random].x][(int)freeSquares[random].y]
                    .setBallColor(MathUtils.random(0,numberOfColors-1));
            squares[(int)freeSquares[random].x][(int)freeSquares[random].y]
                    .setHasBall(true);

        }

    }

    /* проверяем из всех ячеек где нет шариковб получаем список таких ячеек в виде String[]

     */
    public Vector2[] checkSquares () {
        ArrayList<Vector2> freeSquares= new ArrayList<>();

        // проверяем все ячейки
        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++)
                if (squares[i][j].isHasBall() == false) {
                    freeSquares.add(new Vector2(i,j));
                }
        }

        // отдаем массив из свободных ячеек
        Vector2[] freeOut = freeSquares.toArray(new Vector2[freeSquares.size()]);
        return freeOut;
    }
}

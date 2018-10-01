package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class GameField {
    private static final  String TAG = GameField.class.getName();

    private GameScreen gameScreen;
    private Texture texture;

    private Vector2 position;

     // item dimensions
    private int itemWidth;

    // game parameters
    private int fieldDimension = 9 ;
    private int numberOfAiBalls = 3;
    public  int numberOfColors  = 4;
    private int numberOfTurns;
    private int gameScore;

    // turnParameters
    private boolean isBallSelected = false;
    private Vector2 selectedBall;

    // массив с ячейками
    private SquareItem[][] squares ;

    private static boolean isAiTurn;

    public float gameTime;

    public GameField (GameScreen gameScreen ){
        this.gameScreen = gameScreen;

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

        aiTurn();
        //addFakeBalls(3,0,0,0);

    }

    private void deleteBalls (Vector2[] balls) {

        for (int i = 0; i < balls.length ; i++) {

            squares[(int)balls[i].x][(int)balls[i].y].setHasBall(false);
            squares[(int)balls[i].x][(int)balls[i].y].setActive(false);
        }
    }

    public void   render (SpriteBatch batch) {
        //batch.draw(texture,0,0);

        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                squares[i][j].render(batch);
            }
        }


    }

    public void update( float dt) {

        // время игры
        gameTime += dt;

        if(isBallSelected && selectedBall!=null) {
            squares[(int)selectedBall.x][(int)selectedBall.y].update(dt);
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 clickPosition = null;
                if (button == Input.Buttons.LEFT) {
                    // do something
                    if (isBallSelected ) {
                        clickPosition = checkClickEvent(screenX,screenY);
                        if (clickPosition.equals(selectedBall)) {
                            squares[(int)clickPosition.x][(int)clickPosition.y].setActive(false);
                            squares[(int)clickPosition.x][(int)clickPosition.y].setBallInCenter();
                            isBallSelected = false;
                            selectedBall = null;
                        } else {

                            // получаем информацию из выбранного шара и убераем его
                            int color = squares[(int)selectedBall.x][(int)selectedBall.y].getBallColor();
                            squares[(int)selectedBall.x][(int)selectedBall.y].setHasBall(false);
                            squares[(int)selectedBall.x][(int)selectedBall.y].setActive(false);

                            // переносим в другую ячейку
                            squares[(int)clickPosition.x][(int)clickPosition.y].setHasBall(true);
                            squares[(int)clickPosition.x][(int)clickPosition.y].setBallColor(color);
                            isBallSelected = false;
                            selectedBall   = null ;
                            CheckBallLines check = new CheckBallLines(squares,numberOfColors);
                            boolean hasLine = check.startCheck();
                            if(hasLine && check.getBallsInLine() != null) {
                                deleteBalls(check.getBallsInLine());
                            }

                            aiTurn();
                        }
                    }  else if (!isBallSelected){
                        clickPosition = checkClickEvent(screenX,screenY);
                        if (clickPosition != null) {
                            if (squares[(int)clickPosition.x][(int)clickPosition.y].isHasBall()) {
                                squares[(int)clickPosition.x][(int)clickPosition.y].setActive(true);
                                squares[(int)clickPosition.x][(int)clickPosition.y].update(dt);
                                isBallSelected = true;
                                selectedBall = clickPosition;
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public Vector2 checkClickEvent(int screenX, int screenY) {
        Vector2 clickPosition = null;
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {

                //проверяем попал ли щелчок в ячейку и помещяем туда шарик
                if (squares[i][j].hitBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    clickPosition = new Vector2(i,j);
//                    aiTurn();
                }
                //squares[i][j].update(dt);
            }
        }
        return clickPosition;

    }


    public void selectBall ( Vector2 position) {

    }

    /* компьютер выбирает шарики и кладет их в рандомные ячейки
       пока сделано для 3 шариков
     */
    public void aiTurn () {

        for (int i = 0; i < numberOfAiBalls ; i++) {
            Vector2[] freeSquares = checkSquares();
            int random = MathUtils.random(0,freeSquares.length-1);

            squares[(int)freeSquares[random].x][(int)freeSquares[random].y]
                    .setBallColor(MathUtils.random(0,numberOfColors-1));
            squares[(int)freeSquares[random].x][(int)freeSquares[random].y]
                    .setHasBall(true);
        }
    }

    /*
        Игрок по щелчку выбирает шарик с которым взаимодействовать
     */
    public void playerTurn() {

    }

    /*  проверяем из всех ячеек где нет шариковб получаем список таких ячеек в виде String[]

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

    private void addFakeBalls(int number, int color, int positX, int positY ) {

        for (int i = 0; i < number ; i++) {
            //Vector2[] freeSquares = checkSquares();
            //int random = MathUtils.random(0,freeSquares.length-1);

            squares[positX][positY+i]
                    .setBallColor(color);
            squares[positX][positY+i]
                    .setHasBall(true);
        }
    }
}

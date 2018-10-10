package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.funcs.CheckBallLines;
import com.mygdx.game.funcs.FindBallPath;

import java.util.ArrayList;

public class GameField {
    private static final  String TAG = GameField.class.getName();

    private GameScreen gameScreen;
    private Texture texture;
    private ShapeRenderer shapeRenderer;

    private Vector2 position;

    // путь до точки назанчения
    private Vector2[] path;

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
        shapeRenderer = new ShapeRenderer();

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
//        addFakeBalls(8,0,0,1,0);
//        addFakeBalls(8,0,1,1,0);
//        addFakeBalls(3,0,2,1,0);
//        addFakeBalls(4,0,2,5,0);
//        addFakeBalls(9,0,4,0,0);



    }

    private void deleteBalls (Vector2[] balls) {

        for (int i = 0; i < balls.length ; i++) {
            squares[(int)balls[i].x][(int)balls[i].y].setHasBall(false);
            squares[(int)balls[i].x][(int)balls[i].y].setBallInCenter();
            squares[(int)balls[i].x][(int)balls[i].y].setActive(false);
        }
    }

    public void   render (SpriteBatch batch) {
        //batch.draw(texture,0,0);

        for (int i = 0; i < fieldDimension ; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                //if(isBallSelected && selectedBall!=null&& squares[i][j].isHasBall()) {
                    squares[i][j].render(batch);
                //}
            }
        }

        if (path != null && path.length != 0) {
            // Draw the dots

            // получаем координаты центров ячеек
            Vector2[] centers = new Vector2[path.length];
            for (int i = 0; i < path.length ; i++) {
                centers[i] = squares[(int)path[i].x][(int)path[i].y].getCenterPosition();
            }

            float[] floats = vector2ArrayToFloatArray(centers);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.polyline(floats);
            shapeRenderer.end();
        }


    }


    private float[] vector2ArrayToFloatArray(Vector2[] dots){
        float[] floatDots = new float[dots.length * 2];
        int i = 0;
        for (Vector2 dot : dots){
            floatDots[i++] = dot.x;
            floatDots[i++] = dot.y;
        }
        return floatDots;
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

                    if (isBallSelected ) {
                        // если шар уже выбран то проверяем куда потом нажали
                        clickPosition = checkClickEvent(screenX,screenY);
                        if (clickPosition.equals(selectedBall)) {
                            returnSquareInitState(clickPosition);

                            isBallSelected = false;
                            selectedBall = null;
                        } else {
                            // получаем информацию из выбранного шара и убераем его
                            int color = squares[(int)selectedBall.x][(int)selectedBall.y].getBallColor();
                            returnSquareInitState(new Vector2(selectedBall));

                            // проверяем на наличие прохода для шарика
                            FindBallPath finder = new FindBallPath(squares,
                                    selectedBall,
                                    clickPosition) ;

                            // передаем путь до точки
                            boolean pathIsFind = finder.findPath();
                            if(pathIsFind) {
                                path = finder.getPath();
                                // переносим в другую ячейку
                                squares[(int)clickPosition.x][(int)clickPosition.y].setHasBall(true);
                                squares[(int)clickPosition.x][(int)clickPosition.y].setBallColor(color);
                                isBallSelected = false;
                                selectedBall   = null ;

                                // проверяем на наличие составленных линий
                                CheckBallLines check = new CheckBallLines(squares,numberOfColors);
                                boolean hasLine = check.startCheck();
                                if(hasLine && check.getBallsInLine() != null) {
                                    deleteBalls(check.getBallsInLine());
                                }
                            }

                            aiTurn();
                        }
                    }  else if (!isBallSelected){
                        // если шар еще не выбран то выбираем его
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

    private void returnSquareInitState(Vector2 click) {
        squares[(int)click.x][(int)click.y].setActive(false);
        squares[(int)click.x][(int)click.y].setHasBall(false);
        squares[(int)click.x][(int)click.y].setBallInCenter();
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

    private void addFakeBalls(int number, int color, int positX, int positY, int direction ) {

        if (direction  ==0){
            for (int i = 0; i < number; i++) {

                squares[positX][positY + i]
                        .setBallColor(color);
                squares[positX][positY + i]
                        .setHasBall(true);
            }
        } else if (direction == 1) {
            for (int i = 0; i < number; i++) {

                squares[positX+i][positY]
                        .setBallColor(color);
                squares[positX+i][positY]
                        .setHasBall(true);
            }
        }
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.funcs.CheckBallLines;
import com.mygdx.game.funcs.FindBallPath;
import com.mygdx.game.util.Constants;

import java.util.ArrayList;

public class GameField {
    private static final String TAG = GameField.class.getName();

    private GameScreen gameScreen;
    private Texture textureBall;
    private ShapeRenderer shapeRenderer;

    private Vector2 position;

    // путь до точки назанчения
    private Vector2[] path;
    private Vector2[] ballPathCellsCoord;

    // item dimensions
    private int itemWidth;

    // game parameters
    private int fieldDimension = 9;
    private int numberOfAiBalls = 3;
    public int numberOfColors = 4;
    private int numberOfTurns;
    private int gameScore;

    // turnParameters
    private boolean isBallSelected = false;
    private boolean isDrawBallPath = false;
    private Vector2 selectedBall;
    private Vector2 transportPosition;
    private Vector2[] nextTurnBallCells;

    // массив с ячейками
    private SquareItem[][] squares;

    // для передвижения
    private float ballMoveX;
    private float ballMoveY;

    public float gameTime;
    private float ballMoveTime;
    private int ballMoveNumber = 1;

    public GameField(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        shapeRenderer = new ShapeRenderer();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int screenWidth = Gdx.graphics.getWidth();

        itemWidth = (int) screenWidth / fieldDimension;

        squares = new SquareItem[fieldDimension][fieldDimension];
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                int x = j * itemWidth;
                int y = i * itemWidth;
                Vector2 position = new Vector2(x, y);
                squares[j][i] = new SquareItem(gameScreen, itemWidth, itemWidth, position);
            }
        }


        nextTurnBallCells = new Vector2[numberOfAiBalls];
        numberOfTurns = 0;
        gameScore = 0;

        aiTurn();
//        addFakeBalls(3,0,4,3,0);
//        addFakeBalls(8,0,1,1,0);
//        addFakeBalls(1,0,0,3,0);
//        addFakeBalls(4,0,2,5,0);
        //addFakeBalls(9,0,4,0,0);

    }

    private void deleteBalls(Vector2[] balls) {

        for (int i = 0; i < balls.length; i++) {
            squares[(int) balls[i].x][(int) balls[i].y].setHasBall(false);
            squares[(int) balls[i].x][(int) balls[i].y].setBallInCenter();
            squares[(int) balls[i].x][(int) balls[i].y].setActive(false);
        }
    }

    public void render(SpriteBatch batch, float dt) {

        update(dt);

        for (int i = 0; i < fieldDimension;         i++) {
            for (int j = 0; j < fieldDimension - 1; j++) {
                //if(isBallSelected && selectedBall!=null&& squares[i][j].isHasBall()) {
                squares[i][j].render(batch);
                //}
            }
        }

        if (path != null && path.length != 0 && isDrawBallPath) {
            ballMoveTime += dt;

            batch.draw(textureBall,
                    ballMoveX,
                    ballMoveY,
                    itemWidth * Constants.BALL_SIZE_RATIO,
                    itemWidth * Constants.BALL_SIZE_RATIO);
            updateMove(dt);
            if(ballMoveTime > 1 ) {
                isDrawBallPath = false;
            }
            if (!isDrawBallPath) {
                squares[(int) transportPosition.x][(int) transportPosition.y].setHasBall(true);
                ballMoveTime =0;
                aiTurn();
            }
            Gdx.app.log("Move", "move time=" + ballMoveTime);
            //float[] floats = vector2ArrayToFloatArray(centers);
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            shapeRenderer.polyline(floats);
//            Polyline lines = new Polyline(floats);
//            lines.
//            shapeRenderer.end();
        }

    }


    private float[] vector2ArrayToFloatArray(Vector2[] dots) {
        float[] floatDots = new float[dots.length * 2];
        int i = 0;
        for (Vector2 dot : dots) {
            floatDots[i++] = dot.x;
            floatDots[i++] = dot.y;
        }
        return floatDots;
    }


    public void update(float dt) {

        // время игры
        gameTime += dt;

        if (isBallSelected && selectedBall != null) {
            squares[(int) selectedBall.x][(int) selectedBall.y].update(dt);
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 clickPosition = null;
                if (button == Input.Buttons.LEFT) {

                    if (isBallSelected) {
                        // если шар уже выбран то проверяем куда потом нажали
                        clickPosition = checkClickEvent(screenX, screenY);

                        // проверяем на наличие прохода для шарика
                        FindBallPath finder = new FindBallPath(squares,
                                selectedBall,
                                clickPosition);

                        // передаем путь до точки
                        boolean pathIsFind = finder.findPath();

                        if (clickPosition.equals(selectedBall)) {
                            returnSquareInitState(clickPosition, false);

                            isBallSelected = false;
                            selectedBall = null;
                        } else if (squares[(int) clickPosition.x][(int) clickPosition.y].isHasBall()) {
                            Gdx.app.log(TAG, "clicked  to ball");
                        } else if (pathIsFind) {
                            // получаем информацию из выбранного шара и убераем его
                            int color = squares[(int) selectedBall.x][(int) selectedBall.y].getBallColor();
                            returnSquareInitState(new Vector2(selectedBall), true);

                            path = finder.getPath();

                            // получаем координаты центров ячеек
                            ballPathCellsCoord = new Vector2[path.length];
                            for (int i = 0; i < path.length; i++) {
                                ballPathCellsCoord[i] = squares[(int) path[i].x][(int) path[i].y].getPosition();
                            }

                            ballMoveX = ballPathCellsCoord[0].x;
                            ballMoveY = ballPathCellsCoord[0].y;
                            textureBall = squares[(int) selectedBall.x][(int) selectedBall.y].getTextureBall();

                            isDrawBallPath = true;
                            // переносим в другую ячейку
                            transportPosition = clickPosition;
//                            squares[(int) clickPosition.x][(int) clickPosition.y].setHasBall(true);

                            squares[(int) clickPosition.x][(int) clickPosition.y].setBallColor(color);
                            isBallSelected = false;
                            selectedBall = null;
//                            aiTurn();

                            // проверяем на наличие составленных линий
                            CheckBallLines check = new CheckBallLines(squares, numberOfColors);
                            boolean hasLine = check.startCheck();
                            if (hasLine && check.getBallsInLine() != null) {
                                deleteBalls(check.getBallsInLine());
                                gameScore += check.getNumberBallsInLine() * Constants.SCORED_PER_BALL;
                            }
                        }
                    } else if (!isBallSelected) {
                        // если шар еще не выбран то выбираем его
                        clickPosition = checkClickEvent(screenX, screenY);
                        if (clickPosition != null) {
                            if (squares[(int) clickPosition.x][(int) clickPosition.y].isHasBall()) {
                                squares[(int) clickPosition.x][(int) clickPosition.y].setActive(true);
                                squares[(int) clickPosition.x][(int) clickPosition.y].update(dt);
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

    private void updateMove(float dt) {

        Gdx.app.log(TAG, "ballMove " + ballMoveNumber + " pathSize=" + path.length);
        if (path.length == 1) {
            Gdx.app.log(TAG, "path0 " +path[0].x+" " + path[0].y);
        }

        float dx = path[ballMoveNumber].x - path[ballMoveNumber - 1].x;
        float dy = path[ballMoveNumber].y - path[ballMoveNumber - 1].y;

        if (Math.abs(dx) > 0) {
            ballMoveX += dx / Math.abs(dx) * Constants.MOVE_VEL * dt;
        } else if (Math.abs(dy) > 0) {
            ballMoveY += dy / Math.abs(dy) * Constants.MOVE_VEL * dt;
        }

        if (Math.abs(ballMoveX) > itemWidth || Math.abs(ballMoveY) > itemWidth) {
            ballMoveNumber++;
            if (ballMoveNumber < path.length - 1) {
                ballMoveX = ballPathCellsCoord[ballMoveNumber].x;
                ballMoveY = ballPathCellsCoord[ballMoveNumber].y;
            } else {
                isDrawBallPath = false;
                ballMoveNumber = 1;
            }
        }
    }

    public int getGameScore() {
        return gameScore;
    }

    public Vector2 checkClickEvent(int screenX, int screenY) {
        Vector2 clickPosition = null;
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {

                //проверяем попал ли щелчок в ячейку и помещяем туда шарик
                if (squares[i][j].hitBox.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    clickPosition = new Vector2(i, j);
//                    aiTurn();
                }
                //squares[i][j].update(dt);
            }
        }
        return clickPosition;
    }

    private void returnSquareInitState(Vector2 click, boolean delBall) {
        squares[(int) click.x][(int) click.y].setActive(false);
        squares[(int) click.x][(int) click.y].setBallInCenter();
        if (delBall) {
            squares[(int) click.x][(int) click.y].setHasBall(false);
        }
    }

    /* компьютер выбирает шарики и кладет их в рандомные ячейки
     */
    public void aiTurn() {
        if (numberOfTurns == 0) {
            getNextTurnBalls();
        }
        putBall();
        getNextTurnBalls();
    }

    private void putBall() {
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++) {
                if (squares[i][j].isNextTurnBall()) {
                    squares[i][j].setHasBall(true);
                    squares[i][j].setNextTurnBall(false);
                }
            }
        }
    }

    private void getNextTurnBalls() {
        for (int i = 0; i < numberOfAiBalls; i++) {
            Vector2[] freeSquares = checkSquares();
            int random = MathUtils.random(0, freeSquares.length - 1);

            squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                    .setBallColor(MathUtils.random(0, numberOfColors - 1));
            squares[(int) freeSquares[random].x][(int) freeSquares[random].y]
                    .setNextTurnBall(true);
        }
    }

    /*
        Игрок по щелчку выбирает шарик с которым взаимодействовать
     */
    public void playerTurn() {

    }

    /*  проверяем из всех ячеек где нет шариков получаем список таких ячеек в виде String[]

     */
    public Vector2[] checkSquares() {
        ArrayList<Vector2> freeSquares = new ArrayList<>();

        // проверяем все ячейки
        for (int i = 0; i < fieldDimension; i++) {
            for (int j = 0; j < fieldDimension; j++)
                if (squares[i][j].isHasBall() == false) {
                    freeSquares.add(new Vector2(i, j));
                }
        }

        // отдаем массив из свободных ячеек
        Vector2[] freeOut = freeSquares.toArray(new Vector2[freeSquares.size()]);
        return freeOut;
    }

    private void addFakeBalls(int number, int color, int positX, int positY, int direction) {

        if (direction == 0) {
            for (int i = 0; i < number; i++) {

                squares[positX][positY + i]
                        .setBallColor(color);
                squares[positX][positY + i]
                        .setHasBall(true);
            }
        } else if (direction == 1) {
            for (int i = 0; i < number; i++) {

                squares[positX + i][positY]
                        .setBallColor(color);
                squares[positX + i][positY]
                        .setHasBall(true);
            }
        }
    }
}

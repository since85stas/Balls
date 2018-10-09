package com.mygdx.game.funcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SquareItem;
import com.mygdx.game.util.Constants;

import java.util.ArrayList;

public class FindBallPath {
    private static final String TAG = FindBallPath.class.getName();

    private Vector2 from;
    private Vector2 to;
    private SquareItem[][] squares;
    private boolean[][] field;
    private Cell[][] cells;

    private static final int VERTICAL = 22;
    private static final int HORIZONTAL = 33;

    // путь до требуемой точки
    private ArrayList<Vector2> path;

    // params
    private int sizeX;
    private int sizeY;

    // iteration params
    private int currentCellI;
    private int currentCellJ;

    public FindBallPath(SquareItem[][] squares, Vector2 from, Vector2 to) {
        this.squares = squares;
        this.from = from;
        this.to = to;
        sizeX = squares.length;
        sizeY = squares.length;
        field = new boolean[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (squares[i][j].isHasBall()) {
                    field[i][j] = false;
                } else {
                    field[i][j] = true;
                }
            }
        }

        this.cells = generateEmptyCells();


        boolean result = false;

        for (int i = 0; i < 4; i++) {
            if (!result) {
                currentCellI = (int) from.x;
                currentCellJ = (int) from.y;
                result = checkCells();
            }
        }


        //boolean result =  checkCellsRecursion((int)from.x,(int)from.y);

        Gdx.app.log(TAG, "field" + field);
    }


    private boolean checkCells() {
        boolean hasPath = false;
        path = new ArrayList<>();
        path.add(new Vector2(currentCellI, currentCellJ));

        //hasPath = ballMovement();

        while (!hasPath) {

            hasPath = ballMovement();

            // проверка на предыдущую ячейку с открытой границей
            if (!hasPath && path.size() != 0) {
                int size = path.size();
                for (int iter = size - 1; iter > -1; iter--) {
                    int i = (int) path.get(iter).x;
                    int j = (int) path.get(iter).y;
                    if (checkLeftCell(i, j) || checkRightCell(i, j) || checkDownCell(i, j) || checkUpCell(i, j)) {
                        currentCellI = (int) path.get(iter).x;
                        currentCellJ = (int) path.get(iter).y;
                        break;

                    } else {

                        path.remove(iter);
                    }
                }
            }
        }
        return hasPath;
    }


    private boolean ballMovement() {
        boolean hasPath = false;

        while (checkLeftCell(currentCellI, currentCellJ) || checkRightCell(currentCellI, currentCellJ) ||
                checkDownCell(currentCellI, currentCellJ) || checkUpCell(currentCellI, currentCellJ)) {

            // находим приоритетное направление
            int prior = findPrioriativeDirection();
            switch (prior) {
                case Constants.LEFT:
                    // проходим по всем направлениям
                    if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    } else if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    } else if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    } else if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    }
                    break;
                case Constants.RIGHT:
                    // проходим по всем направлениям
                    if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    } else if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    } else if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    } else if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    }
                    break;
                case Constants.DOWN:
                    // проходим по всем направлениям
                    if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    } else if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    } else if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    } else if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    }
                    break;
                case Constants.UP:
                    // проходим по всем направлениям
                    if (checkDownCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.DOWN));
                    } else if (checkRightCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.RIGHT));
                    } else if (checkLeftCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.LEFT));
                    } else if (checkUpCell(currentCellI, currentCellJ)) {
                        path.add(goToCellStep(Constants.UP));
                    }
            }


            // проверка на попадание в Нужную точку
            if (currentCellI == to.x && currentCellJ == to.y) {
                Gdx.app.log(TAG, "Eaaa");
                hasPath = true;
                break;
            }
        }
        return hasPath;
    }

    private int findPrioriativeDirection() {
        int direction = -1;
        int directionCase;
        int dx = (int) to.x - currentCellI;
        int dy = (int) to.y - currentCellJ;

        if (dx <= dy) {
            directionCase = HORIZONTAL;
        } else {
            directionCase = VERTICAL;
        }
        switch (directionCase) {
            case HORIZONTAL:
                if (dx > 0) {
                    direction = Constants.RIGHT;
                } else if (dx < 0) {
                    direction = Constants.LEFT;
                }
                break;
            case VERTICAL:
                if (dy > 0) {
                    direction = Constants.UP;
                } else if (dy < 0) {
                    direction = Constants.DOWN;
                }
                break;
        }
        return direction;
    }

    private void checkStopPoint() {
        if (cells[currentCellI][currentCellJ].isHasDirections()) {


        }
        ;

    }

    private boolean checkLeftCell(int i, int j) {
        boolean result = false;
        if (i != 0) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i - 1][j];
            result = cells[i][j].directionLeft && !cells[i - 1][j].isCrossed;
        }
        Gdx.app.log(TAG, "Eaaa");
        return result;
    }

    private boolean checkRightCell(int i, int j) {
        boolean result = false;
        if (i != sizeX - 1) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i + 1][j];
            result = cells[i][j].directionRight && !cells[i + 1][j].isCrossed;
        }
        Gdx.app.log(TAG, "Eaaa");
        return result;
    }

    private boolean checkDownCell(int i, int j) {
        boolean result = false;
        if (j != 0) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i][j - 1];
            result = cells[i][j].directionDown && !cells[i][j - 1].isCrossed;
        }
        Gdx.app.log(TAG, "Eaaa");
        return result;
    }

    private boolean checkUpCell(int i, int j) {
        boolean result = false;
        if (j != sizeY - 1) {
            Cell cell = cells[i][j];
            Cell cellChecked = cells[i][j + 1];
            result = cells[i][j].directionUp && !cells[i][j + 1].isCrossed;
        }
        Gdx.app.log(TAG, "Eaaa");
        return result;
    }


    private boolean checkCellsRecursion(int i, int j) {
        if (i == to.x && j == to.y) {
            return true;
        }

        // проходим по всем направлениям
        if (cells[i][j].directionLeft) {
            cells[i][j].directionLeft = false;
            i = i - 1;
            cells[i][j].directionRight = false;
            checkCellsRecursion(i, j);
        }
        if (cells[i][j].directionRight) {
            cells[i][j].directionRight = false;
            i = i - 1;
            cells[i][j].directionLeft = false;
            checkCellsRecursion(i, j);
        }
        if (cells[i][j].directionDown) {
            cells[i][j].directionDown = false;
            j = j - 1;
            cells[i][j].directionUp = false;
            checkCellsRecursion(i, j);
        }
        if (cells[i][j].directionUp) {
            cells[i][j].directionUp = false;
            j = j + 1;
            cells[i][j].directionDown = false;
            checkCellsRecursion(i, j);
        }
        return false;
    }


    private Vector2 goToCellStep(int direction) {
        int previousCellI = -1;
        int previousCellJ = -1;

        switch (direction) {
            case Constants.LEFT:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellI--;
                cells[previousCellI][previousCellJ].directionLeft = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionRight = false;
                break;
            case Constants.RIGHT:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellI++;
                cells[previousCellI][previousCellJ].directionRight = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionLeft = false;
                break;
            case Constants.DOWN:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellJ--;
                cells[previousCellI][previousCellJ].directionDown = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionUp = false;
                break;
            case Constants.UP:
                previousCellI = currentCellI;
                previousCellJ = currentCellJ;
                currentCellJ++;
                cells[previousCellI][previousCellJ].directionUp = false;
                cells[previousCellI][previousCellJ].setCrossed(true);
                cells[currentCellI][currentCellJ].directionDown = false;
                break;
        }
        return (new Vector2(currentCellI, currentCellJ));
    }

    private Cell[][] generateEmptyCells() {
        Cell[][] cells = new Cell[sizeX][sizeY];

        boolean directionLeft;
        boolean directionRight;
        boolean directionUp;
        boolean directionDown;

        boolean cellHasBall = false;

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                directionLeft = false;
                directionRight = false;
                directionUp = false;
                directionDown = false;
                //if (field[i][j] ) {

                // проверяем соседние клетки по X
                if (i != 0) {
                    if (field[i - 1][j]) {
                        directionLeft = true;
                    }
                }
                if (i != sizeX - 1) {
                    if (field[i + 1][j]) {
                        directionRight = true;
                    }
                }

                // проверяем соседние клетки по Y
                if (j != 0) {
                    if (field[i][j - 1]) {
                        directionDown = true;
                    }
                }
                if (j != sizeY - 1) {
                    if (field[i][j + 1]) {
                        directionUp = true;
                    }
                }

                if (field[i][j]) {
                    cellHasBall = false;
                } else {
                    cellHasBall = true;
                }

                cells[i][j] = new Cell(i, j, directionLeft, directionRight, directionDown, directionUp, cellHasBall);
            }
        }

        showCells(cells);
        Gdx.app.log(TAG, "test");
        return cells;
    }


    private boolean goToAdjCell(int i, int j, int direction) {

        switch (direction) {
            case Constants.LEFT:
                if (cells[i][j].directionLeft) {
                    return true;
                } else {
                    return false;
                }
            case Constants.RIGHT:
                if (cells[i][j].directionRight) {
                    return true;
                } else {
                    return false;
                }
            case Constants.DOWN:
                if (cells[i][j].directionDown) {
                    return true;
                } else {
                    return false;
                }
            case Constants.UP:
                if (cells[i][j].directionUp) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }


    private void showCells(Cell[][] cells) {
        for (int j = sizeY - 1; j > -1; j--) {
//            Gdx.app.log(TAG, "j= " + j + " " +
//                    cells[0][j].isHasBall() + " " +
//                    cells[1][j].isHasBall() + " " +
//                    cells[2][j].isHasBall() + " " +
//                    cells[3][j].isHasBall() + " " +
//                    cells[4][j].isHasBall() + " " +
//                    cells[5][j].isHasBall() + " " +
//                    cells[6][j].isHasBall() + " " +
//                    cells[7][j].isHasBall() + " " +
//                    cells[8][j].isHasBall()
//            );
            Gdx.app.log(TAG, "j= " + j + " " +
                    cells[0][j].isValidCell() + " " +
                    cells[1][j].isValidCell() + " " +
                    cells[2][j].isValidCell() + " " +
                    cells[3][j].isValidCell() + " " +
                    cells[4][j].isValidCell() + " " +
                    cells[5][j].isValidCell() + " " +
                    cells[6][j].isValidCell() + " " +
                    cells[7][j].isValidCell() + " " +
                    cells[8][j].isValidCell()
            );
        }
        Gdx.app.log(TAG, "\n");
    }

    private enum direction {

    }


}
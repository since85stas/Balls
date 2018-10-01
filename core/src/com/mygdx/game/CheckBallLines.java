package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class CheckBallLines {

    // массив с ячейками
    private SquareItem[][] squares;

    //1. параметры игрового поля
    int SIZE_Y; //размер поля по вертикале
    int SIZE_X;
    int SIZE_WIN = 3; //кол-во заполненных подряд полей для победы
    char[][] fieldg;
    int colors;

    int finalBallXindx;
    int finalBallYindx;

    Vector2[] ballsInLine;

    public Vector2[] getBallsInLine() {
        return ballsInLine;
    }

    final char EMPTY_DOT = '.';

    CheckBallLines(SquareItem[][] squares, int colors) {

        this.squares = squares;
        this.colors = colors;

        SIZE_Y = squares.length;
        SIZE_X = squares.length;
        fieldg = new char[SIZE_Y][SIZE_X];

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (squares[i][j].isHasBall()) {
                    //fieldg[i][j] = (char)squares[i][j].getBallColor();
                    fieldg[i][j] = Character.forDigit(squares[i][j].getBallColor(), 10);
                } else {
                    fieldg[i][j] = '.';
                }
            }
        }

    }

    public boolean startCheck() {
        boolean win = false;

        for (int i = 0; i < colors; i++) {
            char check = Character.forDigit(i, 10);
            win = checkWin(check);
            if (win) return win;
        }

        System.out.println();
        return win;

    }

    //проверка победы
    private boolean checkWin(char dot) {
        for (int v = 0; v < SIZE_Y; v++) {
            for (int h = 0; h < SIZE_X; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= SIZE_X) {                           //по горизонтале
                    if (checkLineHorisont(v, h, dot) >= SIZE_WIN) {
                        return true;
                    }

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, dot) >= SIZE_WIN) {
                            return true;
                        }
                    }
                    if (v + SIZE_WIN <= SIZE_Y) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, dot) >= SIZE_WIN) {
                            return true;
                        }
                    }
                }
                if (v + SIZE_WIN <= SIZE_Y) {                       //по вертикале
                    if (checkLineVertical(v, h, dot) >= SIZE_WIN) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //проверка заполнения всей линии по диагонале вверх
    private int checkDiaUp(int v, int h, char dot) {
        int count = 0;
        int cycleValue = SIZE_WIN;
        for (int i = 0, j = 0; j < cycleValue; i--, j++) {
            if ((fieldg[v + i][h + j] == dot)) {
                    count++;

                    if (count >= SIZE_WIN) {
                        finalBallXindx = i + v;
                        finalBallYindx = j + h;
                        ballsInLine = new Vector2[count];
                        for (int n = 0; n < count; n++) {
                            ballsInLine[n] = new Vector2(finalBallXindx + n, finalBallYindx - n);
                        }

                        //проверка на большее чем SIZE_WIN количество шариков
                        if ( (i + v != SIZE_Y - 1)&& (j + h != SIZE_Y - 1)){
                            cycleValue++;
                        }
                    }
                }
            }
        return count;
    }
    //проверка заполнения всей линии по диагонале вниз

    private int checkDiaDown(int v, int h, char dot) {
        int count = 0;
        int cycleValue = SIZE_WIN;
        for (int i = 0; i < cycleValue; i++) {
            if ((fieldg[i + v][i + h] == dot)) {
                count++;
                if (count >= SIZE_WIN) {
                    finalBallXindx = i + v;
                    finalBallYindx = i + h;
                    ballsInLine = new Vector2[count];
                    for (int n = 0; n < count; n++) {
                        ballsInLine[n] = new Vector2(finalBallXindx - n, finalBallYindx - n);
                    }

                    //проверка на большее чем SIZE_WIN количество шариков
                    if ( (i + v != SIZE_Y - 1)&& (i + h != SIZE_Y - 1)){
                        cycleValue++;
                    }
                }
            }

        }
        return count;
    }

    private int checkLineHorisont(int v, int h, char dot) {
        int count = 0;
        int cycleValue = SIZE_WIN;
        for (int j = h; j < cycleValue + h; j++) {
            if ((fieldg[v][j] == dot)) {
                count++;
                if (count >= SIZE_WIN) {
                    finalBallXindx = v;
                    finalBallYindx = j;
                    ballsInLine = new Vector2[count];
                    for (int n = 0; n < count; n++) {
                        ballsInLine[n] = new Vector2(finalBallXindx, finalBallYindx - n);
                    }

                    //проверка на большее чем SIZE_WIN количество шариков
                    if (j != SIZE_Y - 1) {
                        cycleValue++;
                    }
                }
            }

        }
        return count;
    }

    //проверка заполнения всей линии по вертикале
    private int checkLineVertical(int v, int h, char dot) {
        int count = 0;
        int cycleValue = SIZE_WIN;
        for (int i = v; i < cycleValue + v; i++) {
            if ((fieldg[i][h] == dot)) {
                count++;
                if (count >= SIZE_WIN) {
                    finalBallXindx = i;
                    finalBallYindx = h;
                    ballsInLine = new Vector2[count];
                    for (int n = 0; n < count; n++) {
                        ballsInLine[n] = new Vector2(finalBallXindx - n, finalBallYindx);
                    }

                    //проверка на большее чем SIZE_WIN количество шариков
                    if (i != SIZE_Y - 1) {
                        cycleValue++;
                    }
                }
            }


        }
        return count;
    }


}

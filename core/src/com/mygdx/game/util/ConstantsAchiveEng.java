package com.mygdx.game.util;

public class ConstantsAchiveEng {

    public static final int NUM_ACHIVEMENTS = 9;

    public static final int TYPE_SCORE = -7;
    public static final int TYPE_TIME_SINGLE = -8;
    public static final int TYPE_TIME_OVERALL = -9;
    public static final int TYPE_LINES_SIZE   = -10;

    public static final String[] achivementsName = {
            "achivement 1",
            "achivement 2",
            "achivement 3",
            "achivement 4",
            "achivement 5",
            "achivement 6",
            "achivement 7",
            "achivement 8",
            "achivement 9"};

    public static final String[] achivementsDescr = {
            "get 500 scores",
            "get 1500 scores",
            "get 5000 scores",
            "playing 10 min in one game",
            "playing 30 min in one game",
            "playing 60 min in one game",
            "playing 5 hours total",
            "playing 10 hours total",
            "playing 30 hours total"
    };

    public static final int[] achivementsType = {
            TYPE_SCORE,
            TYPE_SCORE,
            TYPE_SCORE,
            TYPE_TIME_SINGLE,
            TYPE_TIME_SINGLE,
            TYPE_TIME_SINGLE,
            TYPE_TIME_OVERALL,
            TYPE_TIME_OVERALL,
            TYPE_TIME_OVERALL};

    public static final int[] achivementsCriteria = {
            500,
            1500,
            5000,
            10*60,
            30*60,
            60*60,
            5*60*60,
            10*60*60,
            30*60*60};

    public static final int[] achivementsCost = {
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9};

    public ConstantsAchiveEng() {
//        achivementsName = new String[NUM_ACHIVEMENTS];
    }






}

package com.mygdx.game.util;

public class ConstantsAchiveEng {

    public static final int NUM_ACHIVEMENTS = 9;

    public static final int TYPE_SCORE        = -7;
    public static final int TYPE_TIME_SINGLE  = -8;
    public static final int TYPE_TIME_OVERALL = -9;
    public static final int TYPE_LINES_SIZE   = -10;

    public static final String[] achievmentsName = {
            "achivement 1",
            "achivement 2",
            "achivement 3",
            "achivement 4",
            "achivement 5",
            "achivement 6",
            "achivement 7",
            "achivement 8",
            "achivement 9"};

    public static final String[] achievementsDescr = {
            "get 20 scores in one game",
            "get 50 scores in one game",
            "get 100 scores in one game",
            "get 200 scores in one game",
            "get 500 scores in one game",
            "get 1500 scores in one game",
            "get 5000 scores in one game",
            "get 1000 scores total",
            "get 2000 scores total",
            "get 5000 scores total",
            "get 10000 scores total",
            "get 20000 scores total",
            "get 50000 scores total",
            "get 100000 scores total",
            "play  5 min in one game",
            "play 10 min in one game",
            "play 20 min in one game",
            "play 40 min in one game",
            "play 60 min in one game",
            "play 1 hours total" ,
            "play 2 hours total" ,
            "play 5 hours total" ,
            "play 10 hours total",
            "play 24 hours total",
            "played 20 turns",
            "played 40 turns",
            "played 100 turns",
            "played 200 turns",
            "played 500 turns",
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
            5,
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
//        achievmentsName = new String[NUM_ACHIVEMENTS];
    }



}

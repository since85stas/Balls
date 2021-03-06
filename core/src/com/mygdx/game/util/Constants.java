package com.mygdx.game.util;

public class Constants {

    public Constants () {

    }

    // константы экрана
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 240.0f;
    public static final float HUD_MARGIN   = 30.f;

    // ограничение на шаг по времени
    public static final float TIME_STEP = 0.01f;

    // константы для ячейки
    public static float BALL_SIZE_RATIO = 0.7f;
    public static float BALL_PREVIEW_RATIO = 0.4f;
    public static float BALL_VELOCITY = 20.f ;
    public static float DEFORMATION_VELOCITY = 15f;
    public static float DEFORMATION_RATIO = 0.2f;
    public static float UPPER_OFFSET = 0.1f;

    // константы для движения шарика
    public static float MOVE_VEL = 0.01f;

    // константы для игры
    public static float SCORED_PER_BALL = 10.f;

    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int UP = 2;
    public final static int DOWN = 3;


}

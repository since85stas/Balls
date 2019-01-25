package com.mygdx.game.results;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.LinesGame;
import com.mygdx.game.util.ConstantsAchiveEng;

public class AchivementsList {

    private static String TAG = AchivementsList.class.getName();

    private LinesGame game;
//    private String

    public static Achivement[] achivements;

//    public static AchivementsList achivementsInstance = new AchivementsList();

    public AchivementsList(LinesGame game) {
        this.game = game;
    }

    public boolean generateAchivemnets() {
        boolean isOk = true;

        achivements = new Achivement[ConstantsAchiveEng.NUM_ACHIVEMENTS];

        for (int i = 0; i < achivements.length ; i++) {
            try {
                achivements[i] = new Achivement(
                        ConstantsAchiveEng.achivementsName[i],
                        ConstantsAchiveEng.achivementsDescr[i],
                        ConstantsAchiveEng.achivementsCost[i],
                        ConstantsAchiveEng.achivementsType[i],
                        ConstantsAchiveEng.achivementsCriteria[i]
                );
            } catch (Exception e) {
                Gdx.app.log(TAG,"Achivm except ",e);
            }
        }

        return isOk;
    }

    public boolean checkAchivements() {
        boolean achivementComplete = false;

        for (int i = 0; i < achivements.length; i++) {
            switch (achivements[i].getType()){
                case ConstantsAchiveEng.TYPE_SCORE:

            }
        }

        return achivementComplete;
    }




}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private  LinesGame lineGame;
    private  SpriteBatch batch ;
    GameField gameField ;



	public  GameScreen (LinesGame lineGame,SpriteBatch batch){
		this.lineGame = lineGame;
		this.batch      = batch ;

	}


    @Override
    public void show() {
        gameField = new GameField(this);
    }

    @Override
    public void render(float delta) {
        update(delta);

        batch.begin();




        gameField.render(batch);
        //batch.draw();
//        batch.begin();
//        batch.draw(background,0,0);
//        for (int i=0;i<8;i++) {
//            batch.draw(ground, i*200.f-player.getPosition().x%200.f, 0);
//        }
//        player.render(batch);
//        for (int i =0 ; i<enemies.length;i++) {
//            enemies[i].render(batch,player.getPosition().x - getPlayerAnchor());
//        }
       batch.end();
    }

    public void update (float dt) {
        //player.update(dt);
    }
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}

    @Override
    public void hide() {

    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    @Override
    public void resize(int width, int height) {

    }
}

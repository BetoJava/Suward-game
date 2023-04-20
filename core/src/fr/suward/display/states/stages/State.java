package fr.suward.display.states.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import fr.suward.assets.Assets;
import fr.suward.managers.StageManager;

public abstract class State extends Stage {

    protected Stack bgTable;
    protected Table mainTable;
    protected Skin skin;
    protected Image bg = new Image(new Texture("img/background.png"));
    protected Image blackBG = new Image(new Texture("img/blackBG.png"));

    protected boolean isFadingOut = false;
    protected boolean isFadingIn = false;
    protected float actualFadingTime = 0f;
    protected float fadingTime;

    protected final float movingCoefficient = 0.0506f;

    public State() {
        super();
        createTable();
        skin = Assets.getAssetManager().get(Assets.SKIN_COMPOSER);
        Gdx.input.setInputProcessor(this);
    }


    private void createTable() {
        bgTable = new Stack();
        bgTable.setFillParent(true);
        mainTable = new Table();
        mainTable.setDebug(false); // si true : affiche les bordures des Actions //
        // BG //
        bg.scaleBy(movingCoefficient);
        moveBG(Gdx.input.getX(), Gdx.input.getY());

        bgTable.add(bg);
        bgTable.add(mainTable);
        bgTable.add(blackBG);
        blackBG.setColor(0,0,0,1);
        blackBG.setVisible(false);

        this.addActor(bgTable);


    }

    @Override
    public void draw() {
        float dt = Gdx.graphics.getDeltaTime();
        fading(dt);
        act(dt);
        try {
            super.draw();
        } catch (Exception e) {
            System.out.println("Blc, dans State ligne 65");
            this.getBatch().end();
            super.draw();
        }

        if(!(this instanceof BattleState)) {
            moveBG(Gdx.input.getX(), Gdx.input.getY());
        }
    }




    public Table getMainTable() {
        return mainTable;
    }

    protected void moveBG(int screenX, int screenY) {
        float scaleOffsetX = - 1900 * movingCoefficient;
        float scaleOffsetY = - 990 * movingCoefficient;
        bg.setPosition(-(screenX - 950) * movingCoefficient + scaleOffsetX/2, (screenY - 495) * movingCoefficient + scaleOffsetY/2);
    }

    protected void moveBG() {
        moveBG(Gdx.input.getX(), Gdx.input.getY());
    }

    public void fadeOut(float time) {
        blackBG.setVisible(true);
        isFadingOut = true;
        fadingTime = time;
        actualFadingTime = 0;
    }

    public void fadeIn(float time) {
        isFadingIn = true;
        fadingTime = time;
        actualFadingTime = 0;
    }

    private void fading(float dt) {
        if(isFadingOut) {
            blackBG.setColor(0,0,0, (actualFadingTime - 0.06f)/fadingTime);
            actualFadingTime += dt;

            if(actualFadingTime >= fadingTime) {
                actualFadingTime = 0;
                isFadingOut = false;
                StageManager.get().setNextStage();
            }
        } else if(isFadingIn) {
            actualFadingTime += dt;
            blackBG.setColor(0,0,0, (fadingTime - actualFadingTime) /fadingTime);

            if(actualFadingTime >= fadingTime) {
                actualFadingTime = 0;
                blackBG.setVisible(false);
                isFadingIn = false;
            }
        }
    }

    public boolean isFading() {
        return isFadingIn || isFadingOut;
    }
}

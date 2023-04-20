package fr.suward.display.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Entity;
import fr.suward.managers.ClientManager;

public class TimelineCard extends Stack {

    private Image character;
    private Image bg;

    private int entityID;

    private Texture bgTurn;
    private Texture bgNotTurn;

    public TimelineCard(int id, boolean isFirst) {



        entityID = id;
        Entity e = ClientManager.get().getGameManager().getEntityFromID(entityID);

        int w = 64;
        int h = 70;

        if(e.getTeam() == 1) {
            bgTurn = Assets.TEXTURES.get("blueTurnCard");
            bgNotTurn = Assets.TEXTURES.get("blueCard");
        } else {
            bgTurn = Assets.TEXTURES.get("redTurnCard");
            bgNotTurn = Assets.TEXTURES.get("redCard");
        }

        if(isFirst) {
            setBackground(bgTurn);
        } else {
            setBackground(bgNotTurn);
        }

        character = new Image(e.getClassData().getImage(64));
        //character.setSize(w, h);
        add(character);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {

            }
        });




        //setSize(w, h);
    }


    public Image getCharacter() {
        return character;
    }

    public int getEntityID() {
        return entityID;
    }

    private void setBackground(Texture t) {
        bg = new Image(t);
        add(bg);
    }
}

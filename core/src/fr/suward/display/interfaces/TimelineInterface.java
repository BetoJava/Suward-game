package fr.suward.display.interfaces;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.suward.assets.Assets;
import fr.suward.display.components.TimelineCard;
import fr.suward.game.entities.Entity;
import fr.suward.managers.ClientManager;


public class TimelineInterface extends Table {

    private ImageButton head;

    private Table cardsTable;


    public TimelineInterface(Table table) {

        head = new ImageButton(new TextureRegionDrawable(Assets.TEXTURES.get("timelineHead")));
        head.setSize(Assets.TEXTURES.get("timelineHead").getWidth(), Assets.TEXTURES.get("timelineHead").getHeight());
        this.add(head).padTop(30).padBottom(4).row();

        createCardsTable();

        if(ClientManager.get().getGameManager().getTimeline() != null) {
            for(Entity e : ClientManager.get().getGameManager().getTimeline()) {
                TimelineCard tl = new TimelineCard(e.getId(), ClientManager.get().getGameManager().getTimeline().indexOf(e) == 0);
                cardsTable.add(tl).padBottom(4).row();
            }
        }


        this.setWidth(120);
        this.setHeight(650);
        this.setX(1780);
        this.setY(250);
        this.align(Align.top);

        table.add(this);
    }



    private void createCardsTable() {
        cardsTable = new Table();
        cardsTable.align(Align.top);
        cardsTable.setLayoutEnabled(true);
        cardsTable.setWidth(80);

        add(cardsTable);
        cardsTable.setDebug(true);
    }
}

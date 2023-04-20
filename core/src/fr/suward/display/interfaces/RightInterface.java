package fr.suward.display.interfaces;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.suward.assets.Assets;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.display.states.stages.BattleState;
import fr.suward.display.states.stages.WorldState;
import fr.suward.managers.GameManager;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.StartBattleStatePacket;

public class RightInterface extends Table {

    private STextButton button;

    public RightInterface(Table table, Skin skin) {

        this.setLayoutEnabled(false);

        button = new STextButton("Prêt", FontManager.robotoFont(25), FontManager.darkGray, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                GameManager gm = ClientManager.get().getGameManager();
                passTurnPressed(gm);

            }
        });
        button.setWidth(153);
        button.setHeight(88);
        button.setX(30);
        button.setY(82);
        this.add(button);

        this.setBackground(new TextureRegionDrawable(Assets.TEXTURES.get("rightInterface")));
        this.setWidth(213);
        this.setHeight(200);
        this.setX(1687);


        table.add(this);
    }

    public void passTurnPressed(GameManager gm) {
        if(gm.isBattleStarted()) {
            if(ClientManager.get().isCurrentCharacterTurn()) {
                gm.passTurn();
                button.setText("Passer");
            }
        } else if(StageManager.get().getStage() instanceof BattleState) {
            ClientManager.get().getCurrentClientCharacterOrItsSummon().setReady(!ClientManager.get().getCurrentClientCharacterOrItsSummon().isReady());
            ClientManager.get().sendInitialPosPacket();
            if(ClientManager.get().getCurrentClientCharacterOrItsSummon().isReady()) {
                button.setText("Non prêt");
            } else {
                button.setText("Prêt");
            }
        } else {
            StartBattleStatePacket request = new StartBattleStatePacket();
            ClientManager.get().getClient().sendTCP(request);
        }
    }

    public STextButton getButton() {
        return button;
    }
}

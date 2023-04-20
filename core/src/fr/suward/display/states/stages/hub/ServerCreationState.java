package fr.suward.display.states.stages.hub;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.Constants;
import fr.suward.display.states.stages.State;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;
import fr.suward.network.server.SuwardServer;

public class ServerCreationState extends State implements InputProcessor {


    private Label title;
    private SelectBox<String> mapNameSelection;

    public ServerCreationState() {
        super();
        create();

    }

    private void create () {

        title = new Label("Création de Serveur", new Label.LabelStyle(FontManager.firaFont(64, Color.BLACK, 2), Color.WHITE));
        mainTable.add(title);
        mainTable.row();

        addSelectBox(Constants.mapNames);


        addTextButton("Créer le Serveur", FontManager.lightBlue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                new SuwardServer(mapNameSelection.getSelected());
                StageManager.get().nextStage(new ServerConnectionState());
                //ClientManager.get().newGame(mapNameSelection.getSelected());
            }
        });

        addTextButton("Retour", FontManager.red).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new HubState());
            }
        });

    }

    //_________________ Extracted Methods ________________________________//
    private STextButton addTextButton(String text, Color color) {
        STextButton b = new STextButton(text, FontManager.firaFont(32), color, skin);
        mainTable.add(b).padTop(30).width(420).height(80);
        mainTable.row();
        return b;
    }

    private SelectBox<String> addSelectBox(String[] stringList) {
        mapNameSelection = new SelectBox<>(skin);
        mapNameSelection.setItems(stringList);
        mainTable.add(mapNameSelection).padTop(30).width(420).height(40);
        mainTable.row();
        return mapNameSelection;
    }


}

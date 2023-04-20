package fr.suward.display.states.stages.hub;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.display.states.stages.BattleState;
import fr.suward.display.states.stages.State;
import fr.suward.display.states.stages.WorldState;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;

import java.io.IOException;

public class ServerConnectionState extends State implements InputProcessor {


    private Label title;
    private TextField textField;
    private Label errorMsg;

    public ServerConnectionState() {
        super();
        create();

    }

    private void create () {

        title = new Label("Recherche de Serveur", new Label.LabelStyle(FontManager.firaFont(64, Color.BLACK, 2), Color.WHITE));
        mainTable.add(title);
        mainTable.row();

        errorMsg = new Label("Connection à l'IP impossible...", new Label.LabelStyle(FontManager.firaFont(20, Color.BLACK, 2), Color.RED));


        addTextField();


        errorMsg.setVisible(false);
        mainTable.add(errorMsg).padTop(5);
        mainTable.row();


        addTextButton("Connection", FontManager.blue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                errorMsg.setVisible(false);
                try {
                    ClientManager.get().connectClient(textField.getText());
                    StageManager.get().nextStage(new WorldState());

                } catch (IOException ex) {
                    ex.printStackTrace();
                    errorMsg.setVisible(true);
                }

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

    private TextField addTextField() {
        textField = new TextField("127.0.0.1", skin);
        mainTable.add(textField).padTop(30).width(420).height(40);
        mainTable.row();
        return textField;
    }



}

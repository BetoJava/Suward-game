package fr.suward.display.states.stages.hub;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.display.states.stages.character.CharacterChoice;
import fr.suward.display.states.stages.State;
import fr.suward.managers.StageManager;

public class HubState extends State implements InputProcessor {

    private Label title;

    public HubState() {
        super();
        create();

    }

    private void create () {

        title = new Label("Multijoueur", new Label.LabelStyle(FontManager.firaFont(64, Color.BLACK, 2), Color.WHITE));
        mainTable.add(title);
        mainTable.row();

        addTextButton("Héberger", FontManager.lightBlue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new ServerCreationState());
            }
        });

        addTextButton("Rejoindre un hôte", FontManager.lightBlue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new ServerConnectionState());
            }
        });

        addTextButton("Retour", FontManager.red).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new CharacterChoice());
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



}

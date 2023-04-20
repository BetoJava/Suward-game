package fr.suward.display.states.stages.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.display.states.stages.State;
import fr.suward.managers.StageManager;


public class CharacterChoice extends State {

    private Label title;

    public CharacterChoice() {
        super();
        create();
    }

    private void create () {

        title = new Label("Choix du Personnage", new Label.LabelStyle(FontManager.firaFont(64,Color.BLACK, 2), Color.WHITE));
        mainTable.add(title);
        mainTable.row();

        addTextButton("Nouveau personnage...", FontManager.lightBlue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new CharacterCreation());
            }
        });

        addTextButton("Déconnexion", FontManager.red).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
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

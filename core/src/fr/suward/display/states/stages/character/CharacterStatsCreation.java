package fr.suward.display.states.stages.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.suward.Constants;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.display.states.stages.State;
import fr.suward.display.states.stages.hub.HubState;
import fr.suward.game.entities.Character;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;

import java.util.ArrayList;
import java.util.Objects;

public class CharacterStatsCreation extends State {

    private Label title;
    private TextureRegionDrawable ouqImage= new TextureRegionDrawable(new Texture("img/characters/illustrations/Ouq.png"));
    private TextureRegionDrawable eritImage = new TextureRegionDrawable(new Texture("img/characters/illustrations/Erit.png"));
    private TextureRegionDrawable eresImage = new TextureRegionDrawable(new Texture("img/characters/illustrations/Eres.png"));
    private TextureRegionDrawable bramImage = new TextureRegionDrawable(new Texture("img/characters/illustrations/Bram.png"));
    private Image image = new Image();
    private TextField textField;
    private Table leftTable;
    private Table rightTable;

    private Character c;

    private int cpPoint = 14;
    private Label cpLabel;

    public CharacterStatsCreation() {
        super();
        create();
    }

    private void create () {
        c = ClientManager.get().getAccount().getCharacters().get(ClientManager.get().getAccount().getCharacters().size() - 1);
        if(Objects.equals(c.getClassData().getClassName(), "Ouq")) {
            image.setDrawable(ouqImage);
        } else if(Objects.equals(c.getClassData().getClassName(), "Erit")) {
            image.setDrawable(eritImage);
        } else if(Objects.equals(c.getClassData().getClassName(), "Eres")) {
            image.setDrawable(eresImage);
        } else if(Objects.equals(c.getClassData().getClassName(), "Bram")) {
            image.setDrawable(bramImage);
        }

        leftTable = new Table();
        rightTable = new Table();
        mainTable.add(leftTable);
        mainTable.add(rightTable);


        title = new Label(c.getClassData().getClassName(), new Label.LabelStyle(FontManager.firaFont(64, Color.BLACK, 2), Color.WHITE));
        leftTable.add(title);
        leftTable.row();

        leftTable.add(image).width(450).height(450);
        leftTable.row();

        addTextButton("Créer", FontManager.lightBlue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new HubState());
            }
        });

        addTextButton("Retour", FontManager.red).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new CharacterChoice());
            }
        });

        cpLabel = new Label("Caractéristiques Primaires (" + cpPoint + ")", new Label.LabelStyle(FontManager.firaFont(24,Color.BLACK, 1), Color.WHITE));
        rightTable.add(cpLabel);
        mainTable.row();


    }

    public void updateCpPoint() {
        cpLabel.setText("Caractéristiques Primaires (" + cpPoint + ")");
    }



    //_________________ Extracted Methods ________________________________//
    private STextButton addTextButton(String text, Color color) {
        STextButton b = new STextButton(text, FontManager.firaFont(24), color, skin);
        leftTable.add(b).padTop(15).width(315).height(60);
        leftTable.row();
        return b;
    }

    public int getCpPoint() {
        return cpPoint;
    }

    public void addCpPoint(int toAdd) {
        this.cpPoint += toAdd;
    }
}

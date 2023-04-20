package fr.suward.display.states.stages.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.Constants;
import fr.suward.game.entities.Character;
import fr.suward.display.states.stages.State;
import fr.suward.display.states.stages.hub.HubState;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;
import fr.suward.network.accounts.Account;

import java.util.Objects;


public class CharacterCreation extends State {

    private Label title;
    private TextureRegionDrawable ouqImage= new TextureRegionDrawable(new Texture("img/characters/illustrations/Ouq.png"));
    private TextureRegionDrawable eritImage = new TextureRegionDrawable(new Texture("img/characters/illustrations/Erit.png"));
    private TextureRegionDrawable dolmaImage = new TextureRegionDrawable(new Texture("img/characters/illustrations/Dolma.png"));
    private TextureRegionDrawable eresImage = new TextureRegionDrawable(new Texture("img/characters/illustrations/Eres.png"));
    private TextureRegionDrawable bramImage = new TextureRegionDrawable(new Texture("img/characters/illustrations/Bram.png"));
    private Image image = new Image(ouqImage);
    private TextField textField;

    private int classID;

    public CharacterCreation() {
        super();
        create();
    }

    private void create () {

        title = new Label("Création du Personnage", new Label.LabelStyle(FontManager.firaFont(64,Color.BLACK, 2), Color.WHITE));
        mainTable.add(title);
        mainTable.row();

        mainTable.add(image).width(450).height(450);
        mainTable.row();

        Label pseudoLabel = new Label("Pseudo :", new Label.LabelStyle(FontManager.firaFont(24,Color.BLACK, 1), Color.WHITE));
        mainTable.add(pseudoLabel);
        mainTable.row();

        textField = new TextField("", skin);
        mainTable.add(textField).padTop(5).width(315).height(40);
        mainTable.row();

        Table classTable = new Table();

        for(final String className : Constants.className) {
            Button b = new Button(skin);
            if(className != "Pasdoeuf") {
                b.add(new Image(new Texture("img/characters/" + className + ".png")));
            } else {

            }

            b.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    if(Objects.equals(className, "Ouq")) {
                        image.setDrawable(ouqImage);
                        classID = Constants.OUQ;
                    } else if(Objects.equals(className, "Erit")) {
                        image.setDrawable(eritImage);
                        classID = Constants.ERIT;
                    } else if(Objects.equals(className, "Dolma")) {
                        image.setDrawable(dolmaImage);
                        classID = Constants.DOLMA;
                    } else if(Objects.equals(className, "Eres")) {
                        image.setDrawable(eresImage);
                        classID = Constants.ERES;
                    } else if(Objects.equals(className, "Bram")) {
                        image.setDrawable(bramImage);
                        classID = Constants.BRAM;
                    } else if(Objects.equals(className, "Pasdoeuf")) {
                        image.setDrawable(bramImage);
                        classID = Constants.PASDOEUF;
                    } else if(Objects.equals(className, "Maître de Chaî")) {
                        image.setDrawable(bramImage);
                        classID = Constants.MAITRE_DE_CHAI;
                    } else if(Objects.equals(className, "Pasdstuff")) {
                        image.setDrawable(bramImage);
                        classID = Constants.PASDSTUFF;
                    } else if(Objects.equals(className, "Oeuf")) {
                        image.setDrawable(bramImage);
                        classID = Constants.OEUF;
                    } else if(Objects.equals(className, "Moustache de Gégé")) {
                        image.setDrawable(bramImage);
                        classID = Constants.MOUSTACHE_DE_GEGE;
                    }

                }
            });
            classTable.add(b).padRight(5).width(162.5f).height(162.5f);
        }

        mainTable.add(classTable).padTop(15);
        mainTable.row();

        addTextButton("Choisir", FontManager.lightBlue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                accountCreation();
                StageManager.get().nextStage(new HubState());
                //StageManager.get().nextStage(new CharacterStatsCreation());
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
        STextButton b = new STextButton(text, FontManager.firaFont(24), color, skin);
        mainTable.add(b).padTop(15).width(315).height(60);
        mainTable.row();
        return b;
    }

    private void accountCreation() {
        Account a = new Account(textField.getText());

        Character c = new Character(textField.getText(), Constants.classFromClassName(classID));
        a.addCharacter(c);
        ClientManager.get().setAccount(a);
    }



}

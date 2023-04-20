package fr.suward.display.states.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassTest;
import fr.suward.display.states.stages.character.CharacterChoice;
import fr.suward.managers.StageManager;
import fr.suward.network.accounts.Account;


public class MainMenu extends State implements InputProcessor {

    private Image image;

    public MainMenu() {
        super();
        create();
    }

    private void create() {
        addImage();

        // Buttons _________________________________________________________________ //
        addTextButton("Connexion", FontManager.blue).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new CharacterChoice());
            }
        });
        addTextButton("Inscription", FontManager.green).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                StageManager.get().nextStage(new CharacterChoice());
            }
        });
        addTextButton("Paramètres", FontManager.gray).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Character c = new Character("Testos", new ClassTest());
                Account a = new Account("test");
                a.addCharacter(c);
                a.setAccountExp(100);
                a.setWards(18506);

                a.save();

                Account b = new Account("test2");
                b.load("test");
            }
        });
        addTextButton("Quitter", FontManager.red).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });
    }



    //___________________ Extracted Methods ______________________________//
    private STextButton addTextButton(String text, Color color) {
        STextButton b = new STextButton(text, FontManager.firaFont(40), color, skin);
        mainTable.add(b).padTop(30).width(420).height(80);
        mainTable.row();
        return b;
    }

    private void addImage() {
        Table t = new Table();

        image = new Image(new Texture("img/icon/icon256.png"));
        t.add(image).padBottom(20);
        t.add(new Label("uward", new Label.LabelStyle(FontManager.firaFont(104,Color.BLACK, 2), Color.WHITE))).padTop(40).padLeft(-40);
        t.padLeft(-60);
        mainTable.add(t);
        mainTable.row();
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        ((OrthographicCamera)this.getCamera()).zoom += amountY/2;
        return true;
    }


}

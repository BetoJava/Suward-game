package fr.suward.display.interfaces;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;
import fr.suward.assets.Assets;
import fr.suward.display.components.SpellButton;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.util.ArrayList;

public class SpellInterface extends Table {

    private ArrayList<Texture> spellTextures = new ArrayList<>();
    private Table spellTable;

    private ArrayList<Spell> spells;


    public SpellInterface(Table table) {
        create();
        table.add(this);
    }

    public void create() {
        spells = ClientManager.get().getCurrentClientCharacterOrItsSummon().getClassData().getSpells();

        spellTable = new Table();
        spellTable.align(Align.topLeft);
        spellTable.padLeft(34);
        spellTable.padTop(3);
        this.add(spellTable);

        this.setBackground(new TextureRegionDrawable(Assets.TEXTURES.get("spellInterface")));
        this.setWidth(643);
        this.setHeight(200);
        this.setX(1045);
        this.align(Align.topLeft);

    }

    public void clearSpells() {
        while(!spellTable.getChildren().isEmpty()) {
            spellTable.removeActor(spellTable.getChild(0));
        }
        spellTable.reset();
        spellTable.align(Align.topLeft);
        spellTable.padLeft(34);
        spellTable.padTop(3);

    }

    public void addSpellsButtons(ArrayList<Spell> spells) {
        for(int i = 0; i < spells.size(); i++) {
            SpellButton b = new SpellButton(spells.get(i), i);
            spellTable.add(b).padTop(1).padRight(1);

            if(i == 8) {
                spellTable.row();
            }
        }
    }

    public void render(SpriteBatch batch) {
        try {
            ArrayList<SpellButton> spellButtonsToShadow = new ArrayList<>();
            for(Actor sb : spellTable.getChildren()) {
                SpellButton s = (SpellButton) sb;
                if(s.equals(ClientManager.get().getOverSpellButton())) {
                    s.createEffectLines();
                    s.displaySpellPopup(batch);
                }
                if(s.isDisable()) {
                    spellButtonsToShadow.add(s);
                }
            }
            for(SpellButton s : spellButtonsToShadow) {
                s.renderShadow(batch, spellTable.getHeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Table getSpellTable() {
        return spellTable;
    }
}

package fr.suward.game.entities.classes;


import com.badlogic.gdx.graphics.Texture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.entities.stats.Stats;

import java.util.ArrayList;


public abstract class ClassData {

    protected String className;
    protected int classID;
    protected Character character;
    @JsonIgnore
    protected ArrayList<Spell> spells = new ArrayList<>();

    protected int turnDuration = 60;

    @JsonIgnore
    protected Texture image64;
    @JsonIgnore
    protected Texture image128;

    protected Texture texture;

    protected ClassData(String className, Texture image64, Texture image128) {
        this.className = className;
        this.image64 = image64;
        this.image128 = image128;
        defineSpells();
    }

    protected ClassData(int classID, String className, Texture texture) {
        this.className = className;
        this.classID = classID;
        this.texture = texture;
        image64 = Assets.CHARACTERS.get(className + "64");
        defineSpells();
    }

    public Texture getImage(int size) {
        if(size == 128) {
            return image128;
        }
        return image64;
    }

    public Texture getTexture() {
        return texture;
    }

    public abstract void applyStats(Stats stats);

    public abstract void defineSpells();

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public String getClassName() {
        return className;
    }

    public int getClassID() {
        return classID;
    }

    protected void add(Spell s) {
        s.setIndex(spells.size());
        spells.add(s);
    }

    public abstract void beginningOfTurn(Character character);

    public abstract void endingOfTurn(Character character);

    public int getTurnDuration() {
        return turnDuration;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}

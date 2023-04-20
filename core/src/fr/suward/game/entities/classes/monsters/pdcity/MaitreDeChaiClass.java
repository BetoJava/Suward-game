package fr.suward.game.entities.classes.monsters.pdcity;

import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.pdcity.mdc.*;
import fr.suward.game.entities.stats.Stats;

public class MaitreDeChaiClass extends ClassData {

    public MaitreDeChaiClass() {
        super(Constants.MAITRE_DE_CHAI, "Maître De Chaî", Assets.CHARACTERS.get("Maître De Chaî"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 3100);
        stats.apply("pm", 12);
        stats.apply("pc", 0);
        stats.apply("pd", 6);
        stats.apply("po", 2);
        stats.apply("crit", 0);

        stats.apply("sag", 800);
        stats.apply("fo", 800);
        stats.apply("agi", 800);
        stats.apply("pui", 0);

        stats.apply("ini", 7);
        stats.apply("fuite", 8);
        stats.apply("tacle", 3);

        stats.apply("dg dist", 0);
        stats.apply("dg mel", 0);
        stats.apply("do", 54);
        stats.apply("do eau", 0);
        stats.apply("do feu", 0);
        stats.apply("do air", 0);
        stats.apply("do foudre", 0);
        stats.apply("do crit", 0);
        stats.apply("do pou", 0);

        stats.apply("res dist", 5);
        stats.apply("res mel", -10);
        stats.apply("res eau", 6);
        stats.apply("res feu", -6);
        stats.apply("res air", 6);
        stats.apply("res foudre", -6);
        stats.apply("res crit", 10);
        stats.apply("res pou", 8);

    }

    @Override
    public void defineSpells() {
        add(new Chai());
        add(new Explochai());
        add(new Haleine());
        add(new Fermentation());
        add(new ManzaCoca());
        add(new Transposition());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

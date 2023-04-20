package fr.suward.game.entities.classes.monsters.pdcity;

import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.pdcity.pasdstuff.*;
import fr.suward.game.entities.stats.Stats;

public class PasdstuffClass extends ClassData {

    public PasdstuffClass() {
        super(Constants.PASDSTUFF, "Pasdstuff", Assets.CHARACTERS.get("Pasdstuff"));
        turnDuration = 60;
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 3200);
        stats.apply("pm", 11);
        stats.apply("pc", 0);
        stats.apply("pd", 5);
        stats.apply("po", 0);
        stats.apply("crit", 0);

        stats.apply("sag", 700);
        stats.apply("fo", 700);
        stats.apply("agi", 700);
        stats.apply("pui", 700);

        stats.apply("ini", 3);
        stats.apply("fuite", 1);
        stats.apply("tacle", 6);

        stats.apply("dg dist", 0);
        stats.apply("dg mel", 0);
        stats.apply("do", 54);
        stats.apply("do eau", 0);
        stats.apply("do feu", 0);
        stats.apply("do air", 0);
        stats.apply("do foudre", 0);
        stats.apply("do crit", 0);
        stats.apply("do pou", 0);

        stats.apply("res dist", 0);
        stats.apply("res mel", 0);
        stats.apply("res eau", 32);
        stats.apply("res feu", 24);
        stats.apply("res air", 12);
        stats.apply("res foudre", -34);
        stats.apply("res crit", 0);
        stats.apply("res pou", 0);

    }

    @Override
    public void defineSpells() {
        add(new Tentaculation());
        add(new Moinsdstuff());
        add(new Ventouse());
        add(new Pasdoeil());
        add(new Cavite());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

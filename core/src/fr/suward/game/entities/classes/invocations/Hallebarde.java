package fr.suward.game.entities.classes.invocations;

import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.classes.erit.Fouguas;
import fr.suward.game.entities.stats.Stats;

public class Hallebarde extends ClassData {

    public Hallebarde() {
        super(-1, "Hallebarde", Assets.CHARACTERS.get("Hallebarde"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 650);
        stats.apply("pm", 6);
        stats.apply("pc", 0);
        stats.apply("pd", 4);
        stats.apply("po", 0);
        stats.apply("crit", 0);

        stats.apply("sag", 300);
        stats.apply("fo", 0);
        stats.apply("agi", 0);
        stats.apply("pui", 300);

        stats.apply("ini", 50);
        stats.apply("fuite", 5);
        stats.apply("tacle", 3);

        stats.apply("dg dist", 0);
        stats.apply("dg mel", 0);
        stats.apply("do", 0);
        stats.apply("do eau", 0);
        stats.apply("do feu", 20);
        stats.apply("do air", 20);
        stats.apply("do foudre", 0);
        stats.apply("do crit", 0);
        stats.apply("do pou", 0);

        stats.apply("res dist", 0);
        stats.apply("res mel", 0);
        stats.apply("res eau", 10);
        stats.apply("res feu", 10);
        stats.apply("res air", 10);
        stats.apply("res foudre", 10);
        stats.apply("res crit", 0);
        stats.apply("res pou", 0);


    }

    @Override
    public void defineSpells() {
        add(new Fouguas());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

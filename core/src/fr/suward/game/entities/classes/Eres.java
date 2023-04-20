package fr.suward.game.entities.classes;

import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.spells.classes.others.DiagoTest;
import fr.suward.game.entities.spells.classes.ouq.Hook;
import fr.suward.game.entities.spells.classes.ouq.Puissance;
import fr.suward.game.entities.stats.Stats;

public class Eres extends ClassData {


    public Eres() {
        super(Constants.ERES, "Eres", Assets.CHARACTERS.get("Eres"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 4000);
        stats.apply("pm", 12);
        stats.apply("pc", 5);
        stats.apply("pd", 6);
        stats.apply("po", 2);
        stats.apply("crit", 25);

        stats.apply("sag", 0);
        stats.apply("fo", 800);
        stats.apply("agi", 800);
        stats.apply("pui", 0);

        stats.apply("ini", 80);
        stats.apply("fuite", 5);
        stats.apply("tacle", 3);

        stats.apply("dg dist", 0);
        stats.apply("dg mel", 0);
        stats.apply("do", 0);
        stats.apply("do eau", 0);
        stats.apply("do feu", 60);
        stats.apply("do air", 60);
        stats.apply("do foudre", 0);
        stats.apply("do crit", 0);
        stats.apply("do pou", 0);

        stats.apply("res dist", 0);
        stats.apply("res mel", 0);
        stats.apply("res eau", 0);
        stats.apply("res feu", 0);
        stats.apply("res air", 0);
        stats.apply("res foudre", 0);
        stats.apply("res crit", 0);
        stats.apply("res pou", 0);


    }

    @Override
    public void defineSpells() {
        add(new Hook());
        add(new Puissance());
        add(new DiagoTest());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

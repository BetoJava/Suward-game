package fr.suward.game.entities.classes;


import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.spells.classes.ouq.*;
import fr.suward.game.entities.stats.Stats;

public class Ouq extends ClassData {


    public Ouq() {
        super(Constants.OUQ, "Ouq", Assets.CHARACTERS.get("Ouq"));
        turnDuration = 120;
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 5000);
        stats.apply("pm", 12);
        stats.apply("pc", 5);
        stats.apply("pd", 6);
        stats.apply("po", 2);
        stats.apply("crit", 25);

        stats.apply("sag", 0);
        stats.apply("fo", 800);
        stats.apply("agi", 800);
        stats.apply("pui", 0);

        stats.apply("ini", 90);
        stats.apply("fuite", 1);
        stats.apply("tacle", 6);

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
        stats.apply("res eau", 20);
        stats.apply("res feu", 20);
        stats.apply("res air", 20);
        stats.apply("res foudre", 20);
        stats.apply("res crit", 0);
        stats.apply("res pou", 0);

    }

    @Override
    public void defineSpells() {
        add(new Fracas());
        add(new CroixIncendiaire());
        add(new Absorption());
        add(new AttaqueAerienne());
        add(new Hook());
        add(new TransfertHorizontal());
        add(new Puissance());
        add(new Surf());
        add(new Perfisition());
        add(new Cicatrisation());
        add(new Transcendance());
        add(new InvocEpeeSanglante());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

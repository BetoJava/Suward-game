package fr.suward.game.entities.classes;

import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.spells.classes.dolma.*;
import fr.suward.game.entities.spells.classes.erit.*;
import fr.suward.game.entities.stats.Stats;

public class Dolma extends ClassData {


    public Dolma() {
        super(Constants.DOLMA, "Dolma", Assets.CHARACTERS.get("Dolma"));
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

        stats.apply("sag", 800);
        stats.apply("fo", 800);
        stats.apply("agi", 800);
        stats.apply("pui", 800);

        stats.apply("ini", 80);
        stats.apply("fuite", 5);
        stats.apply("tacle", 3);

        stats.apply("dg dist", 0);
        stats.apply("dg mel", 0);
        stats.apply("do", 0);
        stats.apply("do eau", 60);
        stats.apply("do feu", 0);
        stats.apply("do air", 0);
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
        add(new Impesanteur());
        add(new ExtinctionMassive());
        add(new TrouNoir());
        add(new Vitae());
        add(new CuryVitae());
        add(new PriereOffensive());
        add(new PriereProtectrice());
        add(new Gravitae());
        add(new TraverseeTemporelle());
        add(new ProtectionDivine());
        add(new InvocHypercube());
        add(new InvocMurDePierre());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }

}


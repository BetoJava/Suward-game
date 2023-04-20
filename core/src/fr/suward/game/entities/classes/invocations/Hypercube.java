package fr.suward.game.entities.classes.invocations;

import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.classes.dolma.ExtinctionMassive;
import fr.suward.game.entities.spells.classes.dolma.HyperSoutient;
import fr.suward.game.entities.spells.classes.dolma.Impesanteur;
import fr.suward.game.entities.spells.classes.dolma.MiseAuCube;
import fr.suward.game.entities.spells.classes.erit.Fouguas;
import fr.suward.game.entities.stats.Stats;

public class Hypercube extends ClassData {

    public Hypercube() {
        super(-1, "Hypercube", Assets.CHARACTERS.get("Hypercube"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 500);
        stats.apply("pm", 6);
        stats.apply("pc", 0);
        stats.apply("pd", 4);

        stats.apply("sag", 300);

        stats.apply("ini", 50);
        stats.apply("fuite", 5);
        stats.apply("tacle", 3);

        stats.apply("do eau", 20);

        stats.apply("res eau", 25);
        stats.apply("res feu", 18);
        stats.apply("res air", 18);
        stats.apply("res foudre", 18);

    }

    @Override
    public void defineSpells() {
        add(new MiseAuCube());
        add(new HyperSoutient());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

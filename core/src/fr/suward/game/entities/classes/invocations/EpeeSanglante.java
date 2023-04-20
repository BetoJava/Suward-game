package fr.suward.game.entities.classes.invocations;

import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.classes.erit.Fouguas;
import fr.suward.game.entities.spells.classes.ouq.AttiranceSanguinaire;
import fr.suward.game.entities.stats.Stats;

public class EpeeSanglante extends ClassData {

    public EpeeSanglante() {
        super(-1, "EpeeSanglante", Assets.CHARACTERS.get("EpeeSanglante"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 650);
        stats.apply("pm", 6);
        stats.apply("pd", 4);

        stats.apply("sag", 300);
        stats.apply("fo", 300);
        stats.apply("agi", 300);
        stats.apply("pui", 300);

        stats.apply("ini", 50);
        stats.apply("fuite", 5);
        stats.apply("tacle", 3);

        stats.apply("do", 20);

        stats.apply("res eau", 10);
        stats.apply("res feu", 10);
        stats.apply("res air", 10);
        stats.apply("res foudre", 10);


    }

    @Override
    public void defineSpells() {
        add(new AttiranceSanguinaire());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

package fr.suward.game.entities.classes.invocations;

import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.classes.erit.Fouguas;
import fr.suward.game.entities.stats.Stats;

public class MurDePierre extends ClassData {

    public MurDePierre() {
        super(-1, "MurDePierre", Assets.CHARACTERS.get("MurDePierre"));
        turnDuration = 0;
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 80);

        stats.apply("res eau", 65);
        stats.apply("res feu", 65);
        stats.apply("res air", 65);
        stats.apply("res foudre", 65);
    }

    @Override
    public void defineSpells() {

    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

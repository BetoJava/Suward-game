package fr.suward.game.entities.classes.monsters.pdcity;

import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.pdcity.moustache.*;
import fr.suward.game.entities.stats.Stats;

public class MoustacheDeGegeClass extends ClassData {

    public MoustacheDeGegeClass() {
        super(Constants.MOUSTACHE_DE_GEGE, "Moustache De Gégé", Assets.CHARACTERS.get("Moustache De Gégé"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 3200);
        stats.apply("pm", 12);
        stats.apply("pd", 5);

        stats.apply("sag", 750);
        stats.apply("fo", 750);
        stats.apply("agi", 750);
        stats.apply("pui", 750);

        stats.apply("ini", 1);
        stats.apply("fuite", 2);
        stats.apply("tacle", 3);

        stats.apply("do", 48);

        stats.apply("res eau", 12);
        stats.apply("res feu", -15);
        stats.apply("res air", 12);
        stats.apply("res foudre", 12);

    }

    @Override
    public void defineSpells() {
        add(new PoilSoyeux());
        add(new Caresse());
        add(new PoilUrtican());
        add(new DansePoilue());
        add(new CriDeGege());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

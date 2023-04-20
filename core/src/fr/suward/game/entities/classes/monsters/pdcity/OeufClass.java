package fr.suward.game.entities.classes.monsters.pdcity;

import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.pdcity.oeuf.*;
import fr.suward.game.entities.stats.Stats;

public class OeufClass  extends ClassData {

    public OeufClass() {
        super(Constants.OEUF, "Oeuf", Assets.CHARACTERS.get("Oeuf"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 3000);
        stats.apply("pm", 11);
        stats.apply("pc", 0);
        stats.apply("pd", 4);
        stats.apply("po", 2);
        stats.apply("crit", 0);

        stats.apply("sag", 700);
        stats.apply("fo", 700);
        stats.apply("agi", 700);
        stats.apply("pui", 700);

        stats.apply("ini", 4);
        stats.apply("fuite", 2);
        stats.apply("tacle", 2);

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
        stats.apply("res eau", 12);
        stats.apply("res feu", -10);
        stats.apply("res air", 25);
        stats.apply("res foudre", 11);
        stats.apply("res crit", 0);
        stats.apply("res pou", 0);

    }

    @Override
    public void defineSpells() {
        add(new Troeuf());
        add(new Liberation());
        add(new Fracture());
        add(new Roulade());
        add(new Detente());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

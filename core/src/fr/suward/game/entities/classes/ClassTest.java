package fr.suward.game.entities.classes;

import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.spells.classes.ouq.Hook;
import fr.suward.game.entities.stats.Stats;

public class ClassTest extends ClassData {

    public ClassTest(){
        super("Bétonneur", Assets.CHARACTERS.get("betonneur64"), Assets.CHARACTERS.get("betonneur128"));
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 100);
        stats.apply("pm", 12);
        stats.apply("pc", 4);
        stats.apply("pd", 8);
    }

    @Override
    public void defineSpells() {
        add(new Hook());
        add(new Hook());
        add(new Hook());
        add(new Hook());
        add(new Hook());
    }

    @Override
    public void beginningOfTurn(Character character) {

    }

    @Override
    public void endingOfTurn(Character character) {

    }
}

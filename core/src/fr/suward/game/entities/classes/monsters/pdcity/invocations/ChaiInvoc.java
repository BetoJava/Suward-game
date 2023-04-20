package fr.suward.game.entities.classes.monsters.pdcity.invocations;

import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.pdcity.mdc.ExplosionDeChai;
import fr.suward.game.entities.stats.Stats;
import fr.suward.game.pathfinding.Pathfinding;
import fr.suward.game.pathfinding.TargetArea;
import fr.suward.managers.ClientManager;

public class ChaiInvoc extends ClassData {

    public ChaiInvoc() {
        super(-1, "Chaî", Assets.CHARACTERS.get("Chaî"));
        turnDuration = 0;
    }

    private int turnBeforeExplosion = 2;

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 650);
        stats.apply("res pou", -20);

    }

    @Override
    public void defineSpells() {
        add(new ExplosionDeChai());
    }

    @Override
    public void beginningOfTurn(Character character) {
        turnBeforeExplosion -= 1;
        if(turnBeforeExplosion <= 0) {
            spells.get(0).setManaCost(0);
            TargetArea targetArea = Pathfinding.getTargetArea(ClientManager.get().getMap(), character, spells.get(0), character.getPosition());
            // Pour effectuer l'action qu'une seule fois //
            if(ClientManager.get().getClient().getID() == character.getSummoner().getId()) {
                spells.get(0).use(character, targetArea.getEntitiesInArea(),null, targetArea.getAreaPos(), character.getPosition());
            }
            character.die();
        } else {
            //ClientManager.get().getGameManager().passTurn();
        }
    }

    @Override
    public void endingOfTurn(Character character) {

    }
}


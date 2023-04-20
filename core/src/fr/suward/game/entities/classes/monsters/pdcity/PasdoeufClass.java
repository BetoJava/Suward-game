package fr.suward.game.entities.classes.monsters.pdcity;

import fr.suward.Constants;
import fr.suward.assets.Assets;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.entities.spells.pdcity.pasdoeuf.*;
import fr.suward.game.entities.stats.Stats;
import fr.suward.game.pathfinding.Pathfinding;
import fr.suward.game.pathfinding.TargetArea;
import fr.suward.managers.ClientManager;

public class PasdoeufClass extends ClassData {

    public PasdoeufClass() {
        super(Constants.PASDOEUF, "Pasdoeuf", Assets.CHARACTERS.get("Pasdoeuf"));
        turnDuration = 60;
    }

    @Override
    public void applyStats(Stats stats) {
        stats.apply("pv", 8000);
        stats.apply("pm", 14);
        stats.apply("pc", 0);
        stats.apply("pd", 3);
        stats.apply("po", 2);
        stats.apply("crit", 10);

        stats.apply("sag", 800);
        stats.apply("fo", 800);
        stats.apply("agi", 800);
        stats.apply("pui", 800);

        stats.apply("ini", 250);
        stats.apply("fuite", 14);
        stats.apply("tacle", 4);

        stats.apply("dg dist", 0);
        stats.apply("dg mel", 0);
        stats.apply("do", 60);
        stats.apply("do eau", 0);
        stats.apply("do feu", 0);
        stats.apply("do air", 0);
        stats.apply("do foudre", 0);
        stats.apply("do crit", 0);
        stats.apply("do pou", 0);

        stats.apply("res dist", 0);
        stats.apply("res mel", 0);
        stats.apply("res eau", 10);
        stats.apply("res feu", 10);
        stats.apply("res air", 10);
        stats.apply("res foudre", 10);
        stats.apply("res crit", 0);
        stats.apply("res pou", -17);

    }

    @Override
    public void defineSpells() {
        add(new Cry());
        add(new Concentration());
        add(new OeufPourri());
        add(new Massacre());
        add(new ChaiNucleaire());
        add(new Roll());
        add(new GrosFront());
        add(new AutoPilotage());
        add(new CigaretteAfterPex());
        add(new DepressionPartagee());
    }

    @Override
    public void beginningOfTurn(Character character) {
        if (character.getStats().get("pv") < character.getStats().getStat("pv").getMax() * 0.5f) {
            for (Spell s : spells) {
                if (s.getName() == "Cry") {
                    s.setTargetZone(Spell.CIRCLE_ZONE_WITHOUT_CENTER);
                }
                if (s.getName() == "Concentration") {
                    s.setTargetZone(Spell.CIRCLE_ZONE_WITHOUT_CENTER);
                }
            }
        } else {
            for (Spell s : spells) {
                if (s.getName() == "Cry") {
                    s.setTargetZone(Spell.INVERT_CIRCLE_ZONE);
                }
                if (s.getName() == "Concentration") {
                    s.setTargetZone(Spell.INVERT_CIRCLE_ZONE);
                }
            }
        }
        if(ClientManager.get().getClient().getID() == character.getSummonerOrSelf().getId()) {
            for (Spell s : spells) {
                if (s.getName() == "Cry" || s.getName() == "Concentration") {
                    TargetArea targetArea = Pathfinding.getTargetArea(ClientManager.get().getMap(), character, s, character.getPosition());
                    s.use(character, targetArea.getEntitiesInArea(), null, targetArea.getAreaPos(), character.getPosition());
                }
            }
        }

    }


    @Override
    public void endingOfTurn(Character character) {
        if(character.getStats().hasEffectWithName(AutoPilotage.effectName)) {
            Spell.teleport(character, character.getBeginningTurnPosition());
        }
        int value = (int)(0.02*character.getStats().getStat("pv").getMax());
        character.takeDamage(-value, 0,0);
    }
}

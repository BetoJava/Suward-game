package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.entities.spells.SpellBoost;

import java.awt.*;
import java.util.ArrayList;

public class ChaiNucleaire extends Spell {

    public ChaiNucleaire() {
        super();
        name = "Chai Nucléaire";
        textureID = 34;
        damageLines.add(new DamageLine(62, 70, DamageLine.WATER_ELEMENT, false, false));
        minRange = 1;
        maxRange = 8;
        critic = 10;
        concentrationCost = 6;
        maxUses = 3;
        isRangeVariable = true;
        zoneSize = 4;

        effectDescription = "Occasionne des dommages Feu en zone de 4 cases aux ennemis seulement.\nLes dégats sont augmenté de 25%.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        if(targets.contains(caster)) {
            targets.remove(caster);
        }
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) hit(isCritical, caster, target, pos);
        }
        if(!targets.isEmpty()) {
            spell.addSpellBoost(new SpellBoost(17, 1000), caster);
        }
    }

    @Override
    public void startAction() {

    }

    @Override
    public void endAction() {

    }

    @Override
    protected void addEffect() {

    }
}

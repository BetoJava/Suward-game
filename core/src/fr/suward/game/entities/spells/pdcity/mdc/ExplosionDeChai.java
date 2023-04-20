package fr.suward.game.entities.spells.pdcity.mdc;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class ExplosionDeChai extends Spell {

    public ExplosionDeChai() {
        super();
        name = "Explosion De Chai";
        textureID = 72;
        damageLines.add(new DamageLine(500, 500, DamageLine.WATER_ELEMENT, false, false));
        minRange = 0;
        maxRange = 0;
        manaCost = 100;
        maxUses = 1;
        isRangeVariable = false;
        zoneSize = 2;

        effectDescription = "Occasionne des dommages Eau aux ennemis.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        float multiplier = 1;
        for(Effect e : caster.getStats().getEffects()) {
            if(e.getName() == Fermentation.effectName) {
                multiplier += 0.65;
                zoneSize = 4;
            }
        }
        for(Entity target : targets) {
            if(target.getId() != caster.getId()) {
                hit(isCritical, caster, target, pos, multiplier);
            }
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

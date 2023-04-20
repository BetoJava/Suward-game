package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.*;

import java.awt.*;
import java.util.ArrayList;

public class CuryVitae extends Spell {

    public CuryVitae() {
        super();
        name = "Cury Vitae";
        textureID = 129;
        damageLines.add(new DamageLine(18, 18, DamageLine.WATER_ELEMENT, false, true));
        minRange = 0;
        maxRange = 0;
        critic = 10;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 1;
        isRangeVariable = false;

        zoneSize = 1;
        targetZone = SQUARE_ZONE;

        effectDescription = "Soigne les alliés en zone.\nLes soins du sort sont augmentés de 100% pour chaque cible supplémentaire (max 300%).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        int amount = 0;
        for(Entity e : targets) {
            if(!e.isStatic() && areSameTeam(e, caster)) {
                amount ++;
            }
        }
        damageLines.get(0).multiply(Math.min(amount, 4));

        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
        }

        damageLines.get(0).setBaseDamage(18);
        damageLines.get(0).setMinDamage(18);
        damageLines.get(0).setMaxDamage(18);
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

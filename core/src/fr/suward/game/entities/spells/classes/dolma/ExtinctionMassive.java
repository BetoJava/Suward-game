package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.*;

import java.awt.*;
import java.util.ArrayList;

public class ExtinctionMassive extends Spell {

    public ExtinctionMassive() {
        super();
        name = "Extinction Massive";
        textureID = 126;
        damageLines.add(new DamageLine(20, 20, DamageLine.WATER_ELEMENT, false, false));
        minRange = 2;
        maxRange = 6;
        critic = 10;
        manaCost = 4;
        maxUses = 1;
        isRangeVariable = false;
        isSightLine = false;
        zoneSize = 1;
        targetZone = SQUARE_ZONE;
        isDistanceReduced = false;

        effectDescription = "Occasionne des dommages Eau aux ennemis en zone.\nLes dégats du sort sont augmentés de 100% pour chaque cible supplémentaire (max 400%).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        int amount = 0;
        for(Entity e : targets) {
            if(!e.isStatic()) {
                amount ++;
            }
        }

        damageLines.get(0).multiply(Math.min(amount, 5));

        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
        }

        damageLines.get(0).setBaseDamage(20);
        damageLines.get(0).setMinDamage(20);
        damageLines.get(0).setMaxDamage(20);

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

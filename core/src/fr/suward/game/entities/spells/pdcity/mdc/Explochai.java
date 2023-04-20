package fr.suward.game.entities.spells.pdcity.mdc;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Explochai extends Spell {

    public Explochai() {
        super();
        name = "Explochaî";
        textureID = 72;
        damageLines.add(new DamageLine(17, 19, DamageLine.FIRE_ELEMENT, false, false));
        damageLines.add(new DamageLine(17, 19, DamageLine.WATER_ELEMENT, false, false));
        damageLines.add(new DamageLine(17, 19, DamageLine.AIR_ELEMENT, false, false));
        minRange = 4;
        maxRange = 7;
        critic = 8;
        manaCost = 5;
        maxUses = 1;
        zoneSize = 3;
        isDistanceReduced = false;
        isRangeVariable = false;

        effectDescription = "Occasionne des dommages Feu, Eau et Air aux ennemis en zone de 3 cases.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
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

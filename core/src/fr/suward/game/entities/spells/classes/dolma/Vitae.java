package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Vitae extends Spell {

    public Vitae() {
        super();
        name = "Vitae";
        textureID = 128;
        damageLines.add(new DamageLine(30, 30, DamageLine.WATER_ELEMENT, false, true));
        minRange = 1;
        maxRange = 5;
        critic = 15;
        manaCost = 3;
        maxUses = 3;
        maxUsesOnTarget = 2;
        isRangeVariable = true;

        effectDescription = "Soigne la cible.";
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

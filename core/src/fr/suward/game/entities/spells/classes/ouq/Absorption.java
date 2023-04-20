package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Absorption extends Spell {


    public Absorption() {
        super();
        name = "Absorption";
        textureID = 115;
        damageLines.add(new DamageLine(22, 25, DamageLine.FIRE_ELEMENT, true, false));
        minRange = 1;
        maxRange = 5;
        critic = 12;
        manaCost = 3;
        maxUsesOnTarget = 2;
        maxUses = 3;
        isRangeVariable = false;

        effectDescription = "Vol de la vie Feu aux ennemis.";
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

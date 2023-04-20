package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class AttiranceSanguinaire extends Spell {

    public AttiranceSanguinaire() {
        super();
        name = "Attirance Sanguinaire";
        textureID = 72;
        damageLines.add(new DamageLine(18, 24, DamageLine.FIRE_ELEMENT, true, false));
        push = -1;
        minRange = 1;
        maxRange = 3;
        critic = 5;
        manaCost = 4;
        maxUses = 3;
        isRangeVariable = false;
        castZone = Spell.CROSS_CAST;

        effectDescription = "Vol de la vie Feu aux ennemis.\nAttire d'une case.";
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

package fr.suward.game.entities.spells.pdcity.oeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Troeuf extends Spell {

    public Troeuf() {
        super();
        name = "Troeuf";
        textureID = 72;
        damageLines.add(new DamageLine(30, 34, DamageLine.AIR_ELEMENT, false, false));
        minRange = 1;
        maxRange = 7;
        critic = 10;
        manaCost = 4;
        maxUses = 2;
        push = 1;
        isRangeVariable = true;
        castZone = Spell.CROSS_CAST;
        zoneSize = 1;
        targetZone = Spell.ROW_ZONE;

        effectDescription = "Occasionne des dommages Feu aux ennemis.\nRepousse d'une case.";
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

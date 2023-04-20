package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class CroixIncendiaire extends Spell {

    public CroixIncendiaire() {
        super();
        name = "Croix Incendiaire";
        textureID = 114;
        damageLines.add(new DamageLine(38, 44, DamageLine.FIRE_ELEMENT, false, false));
        minRange = 1;
        maxRange = 7;
        critic = 10;
        manaCost = 4;
        maxUses = 2;
        zoneSize = 3;
        targetZone = CROSS_ZONE;
        isRangeVariable = false;

        castZone = Spell.CROSS_CAST;
        effectDescription = "Occasionne des dommages Feu aux ennemis en zone de 3 cases.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) hit(isCritical, caster, target, pos);
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

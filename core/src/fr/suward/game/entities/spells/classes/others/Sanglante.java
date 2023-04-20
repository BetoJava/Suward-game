package fr.suward.game.entities.spells.classes.others;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.entities.spells.SpellBoost;

import java.awt.*;
import java.util.ArrayList;

public class Sanglante extends Spell {

    public static int id = 8;

    public Sanglante() {
        super();
        name = "Sanglante";
        textureID = 15;
        damageLines.add(new DamageLine(20, 35, DamageLine.FIRE_ELEMENT, false, false));
        minRange = 2;
        maxRange = 6;
        critic = 10;
        manaCost = 3;
        travelCost = 2;
        concentrationCost = -1;
        maxUses = 3;
        isTargetNecessary = true;

        zoneSize = 1;
        targetZone = CROSS_ZONE;
        effectDescription = "Occasionne des dommages Feu aux ennemis.\nAugmente les dégâts de base du sort de 5 (3 tours).";
    }



    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
        }
        spell.addSpellBoost(new SpellBoost(5, 3), caster);

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

package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Lansso extends Spell {

    public static String effectName = "Lanssoté";
    public static int id = 2;

    public Lansso() {
        super();
        name = "Lansso";
        textureID = 106;
        damageLines.add(new DamageLine(25, 28, DamageLine.WATER_ELEMENT, false, false));
        push = -1;
        minRange = 1;
        maxRange = 6;
        critic = 15;
        manaCost = 3;
        maxUses = 3;
        maxUsesOnTarget = 2;
        isRangeVariable = false;

        effectDescription = "Occasionne des dommages Eau aux ennemis.\nAttire la cible de 1 case.\nSur ciblé : retire 1 PD (max 2).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " perd 1 PD pendant 1 tour.");
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
        Effect e = new Effect(effectName,2);
        e.add(new Boost("pd", -1, 2));
        effects.add(e);
    }

}

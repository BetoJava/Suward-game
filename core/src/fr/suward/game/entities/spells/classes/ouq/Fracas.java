package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Fracas extends Spell {

    public static String effectName = "Fracas";

    public Fracas() {
        super();
        name = "Fracas";
        textureID = 119;
        damageLines.add(new DamageLine(23, 25, DamageLine.FIRE_ELEMENT, false, false));
        damageLines.add(new DamageLine(23, 25, DamageLine.AIR_ELEMENT, false, false));
        minRange = 1;
        maxRange = 1;
        critic = 12;
        manaCost = 5;
        maxUses = 3;
        maxUsesOnTarget = 2;
        isRangeVariable = false;

        effectDescription = "Occasionne des dommages Feu et Air aux ennemis.\nRetire 1 PD à la cible (1 tour).";
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

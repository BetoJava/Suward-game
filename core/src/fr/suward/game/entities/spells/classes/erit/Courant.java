package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Courant extends Spell {

    public static String effectName = "Court circuité";
    public static int id = 1;

    public Courant() {
        super();
        name = "Courant";
        textureID = 103;
        damageLines.add(new DamageLine(33, 36, DamageLine.LIGHTNING_ELEMENT, false, false));
        minRange = 1;
        maxRange = 6;
        critic = 15;
        manaCost = 4;
        maxUses = 3;
        isRangeVariable = true;

        effectDescription = "Occasionne des dommages Foudre aux ennemis.\nSur ciblé : retire 1 PO à la cible (1 tour) (max 2).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            if(target.getStats().countEffectWithName(effectName) < 2) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " perd 1 PO pendant 1 tour.");
                }
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
        e.add(new Boost("po", -1, 2));
        effects.add(e);
    }
}

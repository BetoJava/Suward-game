package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Chatilance extends Spell {

    public static String effectName = "Chatié";
    public static int id = 1;

    public Chatilance() {
        super();
        name = "Chatilance";
        textureID = 104;
        damageLines.add(new DamageLine(46, 48, DamageLine.LIGHTNING_ELEMENT, false, false));
        minRange = 1;
        maxRange = 2;
        critic = 15;
        manaCost = 5;
        maxUses = 3;
        maxUsesOnTarget = 2;
        isRangeVariable = false;

        effectDescription = "Occasionne des dommages Foudre aux ennemis.\nSur ciblé : augmente ses dommages subis de 7% (2 tours).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            if(target.getStats().countEffectWithName(effectName) < 2 && target.getStats().hasEffectWithName(Ciblage.effectName)) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, " Les dommages subis par " + target.getPseudo() + " sont augmentés de 7% (2 tours).");
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
        e.add(new Boost("% dmg subis", 7, 3));
        effects.add(e);
    }
}

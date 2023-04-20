package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.*;

import java.awt.*;
import java.util.ArrayList;


public class TransfertHorizontal extends Spell {

    public static String effectName = "Transfert Horizontal";

    public TransfertHorizontal() {
        super();
        name = "Transfert Horizontal";
        textureID = 123;
        damageLines.add(new DamageLine(17, 17, DamageLine.AIR_ELEMENT, false, false, true));
        minRange = 1;
        maxRange = 5;
        manaCost = 3;
        maxUses = 2;
        maxUsesOnTarget = 1;
        isTargetNecessary = true;

        castZone = CROSS_CAST;

        effectDescription = "Applique un poison Air pendant 2 tours à la cible.\nPrend la place de la cible.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            for(Effect e : spell.getEffects()) {
                applyDamageLinesToPoisons(e);
                e.addEffectToTarget(target, caster, target.getPseudo() + " est empoisonné pendant 2 tours");
            }
            teleport(caster, pos);
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
        Effect effect = new Effect(effectName,3);
        effect.add(new Poison(3));
        effects.add(effect);
    }

}

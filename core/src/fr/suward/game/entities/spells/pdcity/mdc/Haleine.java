package fr.suward.game.entities.spells.pdcity.mdc;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.*;

import java.awt.*;
import java.util.ArrayList;

public class Haleine extends Spell {

    public static String effectName = "Poison d'Haleine";

    public Haleine() {
        super();
        name = "Haleine";
        textureID = 72;
        damageLines.add(new DamageLine(8, 10, DamageLine.AIR_ELEMENT, false, false, true));
        minRange = 1;
        maxRange = 6;
        push = 1;
        manaCost = 2;
        maxUses = 2;
        isRangeVariable = false;
        zoneSize = 2;
        targetZone = Spell.LINE_ZONE;
        castZone = Spell.CROSS_CAST;

        effectDescription = "Repousse d'une case et applique un poison Air pendant 2 tours (max 2).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            for(Effect e : spell.getEffects()) {
                applyDamageLinesToPoisons(e);
                e.addEffectToTarget(target, caster, target.getPseudo() + " est empoisonné pendant 2 tours");
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
        Effect effect = new Effect(effectName,3);
        effect.add(new Poison(3));
        effects.add(effect);
    }
}

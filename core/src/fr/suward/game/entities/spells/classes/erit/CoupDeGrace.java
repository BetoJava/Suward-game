package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class CoupDeGrace extends Spell {

    public static String effectName = "Court circuité";
    public static int id = 1;

    public CoupDeGrace() {
        super();
        name = "Coup de grâce";
        textureID = 105;
        damageLines.add(new DamageLine(34, 35, DamageLine.WATER_ELEMENT, false, false));
        minRange = 1;
        maxRange = 3;
        critic = 15;
        concentrationCost = 4;
        maxUses = 2;
        isRangeVariable = false;

        castZone = Spell.CROSS_CAST;
        effectDescription = "Occasionne des dommages Eau aux ennemis.\nSur ciblé : consomme l'état et double les dégats de base du sorts.\nSi le ciblé est achevé, le lanceur regagne 4 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(target.getStats().hasEffectWithName(Ciblage.effectName)) {
                hit(isCritical, caster, target, pos, 2);
                target.getStats().removeEffects(Ciblage.effectName);
            } else {
                hit(isCritical, caster, target, pos);
            }

            if(target.getStats().get("pv") <= 0) {
                caster.getStats().add("pc", 4);
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

    }
}

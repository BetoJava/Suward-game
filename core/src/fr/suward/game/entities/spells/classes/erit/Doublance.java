package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Doublance extends Spell {

    public static String effectName = "Court circuité";

    public Doublance() {
        super();
        name = "Doublance";
        textureID = 101;
        damageLines.add(new DamageLine(14, 15, DamageLine.LIGHTNING_ELEMENT, false, false));
        damageLines.add(new DamageLine(14, 15, DamageLine.WATER_ELEMENT, false, false));
        minRange = 3;
        maxRange = 8;
        critic = 10;
        manaCost = 3;
        maxUses = 3;
        maxUsesOnTarget = 2;
        isRangeVariable = true;

        effectDescription = "Occasionne des dommages Foudre et Eau aux ennemis.\nVol 100 de puissance et sagesse (2 tours).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            effects.get(0).addEffectToTarget(target, caster, target.getPseudo() + " perd 100 de puissance et 100 de sagesse pour 2 tours.");
            effects.get(1).addEffectToTarget(caster, caster, caster.getPseudo() + " gagne 100 de puissance et 100 de sagesse pour 2 tours.");
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
        Effect e = new Effect(effectName,3);
        e.add(new Boost("sag", -100, 3));
        e.add(new Boost("pui", -100, 3));
        effects.add(e);
        Effect e2 = new Effect(effectName,3);
        e2.add(new Boost("sag", 100, 3));
        e2.add(new Boost("pui", 100, 3));
        effects.add(e2);
    }
}

package fr.suward.game.entities.spells.pdcity.pasdstuff;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Pasdoeil extends Spell {

    public static String effectName = "Pasdoeil";

    public static int id = 5;

    public Pasdoeil() {
        super();
        name = "Pasdoeil";
        textureID = 72;
        damageLines.add(new DamageLine(10, 30, DamageLine.WATER_ELEMENT, false, false));
        minRange = 4;
        maxRange = 8;
        critic = 9;
        manaCost = 2;
        maxUses = 4;
        isRangeVariable = true;

        effectDescription = "Occasionne des dommages Eau aux ennemis et vol 1 PD et 1 PO (1 tour) (max 2).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            if(target.getStats().countEffectWithName(effectName) < 2) {
                effects.get(0).addEffectToTarget(target, caster, target.getPseudo() + " perd 1 PD et 1 PO (1 tour).");

            }
        }
        if(!targets.isEmpty()) {
            effects.get(1).addEffectToTarget(caster, caster, caster.getPseudo() + " gagne 1 PD et 1 PO (1 tour).");
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
        e.add(new Boost("po", -1, 2));
        effects.add(e);
        Effect e1 = new Effect(effectName,1);
        e1.add(new Boost("pd", 1, 2));
        e1.add(new Boost("po", 1, 2));
        effects.add(e1);
    }
}

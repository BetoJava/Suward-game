package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Gravitae extends Spell {

    public static String effectName = "Gravitae";
    public Gravitae() {
        super();
        name = "Gravitae";
        textureID = 132;
        minRange = 1;
        maxRange = 6;
        critic = 15;
        concentrationCost = 3;
        maxUses = 1;
        maxDelay = 2;
        isRangeVariable = false;
        zoneSize = 2;
        targetZone = SQUARE_ZONE;

        effectDescription = "Retire 2 PD et applique l'état pesanteur aux ennemis (2 tours).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " perd 2 PD et rentre dans l'état pesanteur pour 2 tours.");
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
        Effect e = new Effect(effectName,3);
        e.add(new Boost("pd", -2, 3));
        e.add(new Boost(Effect.GRAVITY, 3));
        effects.add(e);
    }
}

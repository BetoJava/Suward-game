package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class ProtectionDivine extends Spell {

    public static String effectName = "Protection Divine";

    public ProtectionDivine() {
        super();
        name = "Protection Divine";
        textureID = 131;
        minRange = 1;
        maxRange = 5;
        concentrationCost = 2;
        maxUses = 1;
        maxDelay = 4;
        isRangeVariable = false;

        effectDescription = "Applique l'état invulnérable à distance pendant 1 tour à l'allié ciblé.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(areSameTeam(target, caster)) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " rentre dans l'état invulnérable à distance 1 tour.");
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
        e.add(new Boost(Effect.DISTANCE_INVULNERABLE, 2));
        effects.add(e);
    }
}

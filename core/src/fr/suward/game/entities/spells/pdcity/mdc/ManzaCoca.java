package fr.suward.game.entities.spells.pdcity.mdc;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class ManzaCoca extends Spell {

    public static String effectName = "ManzaCoca";

    public ManzaCoca() {
        super();
        name = "ManzaCoca";
        textureID = 72;
        minRange = 1;
        maxRange = 1;
        manaCost = 1;
        maxUses = 1;
        maxDelay = 1;
        isRangeVariable = false;

        effectDescription = "Si lancé sur un Chaî : gagne 4 PD et 2 PC mais tue le Chaî.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(target.getClassData().getClassName() == "Chaî" && areSameTeam(caster, target)) {
                caster.getStats().add("pc",2);
                for(Effect e : spell.getEffects()) {
                    e.addEffectToTarget(caster, caster, caster.getPseudo() + " gagne 4 PD pendant 1 tour et 2 PC ");
                }
                target.die();
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
        Effect effect = new Effect(effectName,2);
        effect.add(new Boost("pd", 4, 2));
        effects.add(effect);
    }
}

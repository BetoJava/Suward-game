package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Puissance extends Spell {

    public static String effectName = "Puissance";

    public Puissance() {
        super();
        name = "Puissance";
        textureID = 121;
        minRange = 0;
        maxRange = 4;
        concentrationCost = 3;
        maxUses = 1;
        maxDelay = 3;

        effectDescription = "Augmente de 50 les dommages de la cible pendant 2 tours.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(areSameTeam(caster, target)) {
                for(Effect e : spell.getEffects()) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 50 dommages pendant 2 tours.");
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
        Effect effect = new Effect(effectName,3);
        effect.add(new Boost("do", 50, 3));
        effects.add(effect);
    }

}

package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class HyperSoutient extends Spell {

    public static String effectName = "Hyper Soutient";

    public HyperSoutient() {
        super();
        name = "Hyper Soutient";
        textureID = 97;
        minRange = 1;
        maxRange = 2;
        manaCost = 2;
        maxUses = 1;
        isRangeVariable = false;

        effectDescription = "Donne 1 PM (2 tour).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(areSameTeam(caster, target)) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 1 PM pendant 2 tour.");
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
        e.add(new Boost("pm", 1, 3));
        effects.add(e);
    }
}

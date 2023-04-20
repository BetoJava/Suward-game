package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Dephasage extends Spell {

    public static String effectName = "Déphasé";
    public static int id = 4;

    public Dephasage() {
        super();
        name = "Déphasage";
        textureID = 110;
        minRange = 0;
        maxRange = 0;
        zoneSize = 3;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = false;


        effectDescription = "-2 PM, -3 PC, -3 PD (1t).\nDans un tour : +3 PM, +3 PC, +3 PD (1t)";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : spell.getEffects()) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " perd 2 PM, 3 PC et 3 PD pendant 1 tour");
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
        effect.add(new Boost("pm", -2, 2));
        effect.add(new Boost("pc", -3, 2));
        effect.add(new Boost("pd", -3, 2));
        effect.add(new Boost("pm", 3, 2, 1));
        effect.add(new Boost("pc", 3, 2, 1));
        effect.add(new Boost("pd", 3, 2, 1));
        effects.add(effect);
    }

}

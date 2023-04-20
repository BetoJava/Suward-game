package fr.suward.game.entities.spells.pdcity.oeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Detente extends Spell {

    public static String effectName = "Détendu";

    public Detente() {
        super();
        name = "Détente";
        textureID = 77;
        minRange = 0;
        maxRange = 0;
        zoneSize = 2;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = false;


        effectDescription = "Retire 2 PD (1t) et ajoute 3 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            target.getStats().add("pc",3);
            for(Effect e : spell.getEffects()) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 3 PC et perd 2 PD pendant 1 tour");
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
        effect.add(new Boost("pd", -2, 2));
        effects.add(effect);
    }

}


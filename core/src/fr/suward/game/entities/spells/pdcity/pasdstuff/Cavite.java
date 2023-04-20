package fr.suward.game.entities.spells.pdcity.pasdstuff;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Cavite extends Spell {

    public static String effectName = "Cavité";

    public Cavite() {
        super();
        name = "Cavité";
        textureID = 72;
        minRange = 1;
        maxRange = 1;
        concentrationCost = 6;
        maxUses = 3;
        isRangeVariable = false;

        effectDescription = "Retire 1000 de fuite pendant 2 tours et applique l'état enraciné pendant 1 tour.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " perd 1000 fuite (2 tours) et rentre dans l'état enraciné (1 tour).");
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
        e.add(new Boost("fuite", -1000, 3));
        e.add(new Boost(Effect.ROOTED, 2));
        effects.add(e);
    }
}

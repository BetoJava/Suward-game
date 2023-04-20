package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class DepressionPartagee extends Spell {

    public static String effectName = "Dépression Partagée";

    public DepressionPartagee() {
        super();
        name = "Dépression Partagée";
        textureID = 45;
        minRange = 1;
        maxRange = 6;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 4;
        isRangeVariable = false;

        effectDescription = "Les dommages reçus sont partagés avec la cible (2 tours).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " rentre en dépression...");
            }
        }
        for(Effect e : effects) {
            e.addEffectToTarget(caster, caster, caster.getPseudo() + " rentre en dépression...");
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
        effects.add(e);
    }
}

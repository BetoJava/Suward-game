package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class AutoPilotage extends Spell {

    public static String effectName = "Auto-Pilotage";

    public AutoPilotage() {
        super();
        name = "Auto-Pilotage";
        textureID = 20;
        minRange = 0;
        maxRange = 0;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 4;
        isRangeVariable = false;

        effectDescription = "Ajoute 10 PM.\nRevient à la position de départ du tour à la fin du tour.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : spell.getEffects()) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 10 PD.");
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
        effect.add(new Boost("pd", 10, 2));
        effects.add(effect);
    }
}

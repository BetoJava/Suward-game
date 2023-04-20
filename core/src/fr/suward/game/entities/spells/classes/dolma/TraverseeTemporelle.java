package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class TraverseeTemporelle extends Spell {

    public static String effectName = "Traversée Temporelle";

    public TraverseeTemporelle() {
        super();
        name = "Traversée Temporelle";
        textureID = 130;
        minRange = 0;
        maxRange = 6;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 2;
        isRangeVariable = false;

        effectDescription = "Donne 3 PD (1t). A la fin du tour de la cible, celle-ci revient à sa position de début de tour.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 3 PD pendant 1 tour.");
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
        e.add(new Boost("pd", 3, 2));
        effects.add(e);
    }
}

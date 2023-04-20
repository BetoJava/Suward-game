package fr.suward.game.entities.spells.pdcity.oeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Fracture extends Spell {

    public static String effectName = "Fracturé";
    public static DamageLine damageLine = new DamageLine(8, 8, DamageLine.AIR_ELEMENT, false, false);

    public Fracture() {
        super();
        name = "Fracture";
        textureID = 72;
        damageLines.add(damageLine);
        minRange = 1;
        maxRange = 5;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = true;

        effectDescription = "Fracture un ennemi et applique 6% d'érosion pendant 2 tours." +
                            "\nInflige des dommages Air pour chaque case poussée ou attirée pendant 3 tours.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) {
                for(Effect e : spell.getEffects()) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " est fracturé pendant 3 tours et est érodé de 6% pendant 2 tours.");
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
        effect.add(new Boost("ero", 6, 2));
        effects.add(effect);
    }
}

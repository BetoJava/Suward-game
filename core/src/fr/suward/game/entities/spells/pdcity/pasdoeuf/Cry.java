package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Cry extends Spell {

    public static String effectName = "Cry";

    public Cry() {
        super();
        name = "Cry";
        textureID = 72;
        damageLines.add(new DamageLine(40, 40, DamageLine.WATER_ELEMENT, false, false));
        minRange = 0;
        maxRange = 0;
        critic = 0;
        manaCost = 0;
        maxUses = 1;
        isRangeVariable = false;
        isDistanceReduced = false;

        targetZone = Spell.INVERT_CIRCLE_ZONE;
        zoneSize = 6;

        effectDescription = "Occasionne des dommages Eau aux ennemis.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) {
                hit(isCritical, caster, target, pos);
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " est érodé de 2% pour 3 tours.");
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
        Effect e = new Effect(effectName,4);
        e.add(new Boost("ero", 2, 4));
        effects.add(e);
    }
}

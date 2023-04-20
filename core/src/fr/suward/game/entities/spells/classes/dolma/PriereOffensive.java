package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class PriereOffensive extends Spell {

    public static String effectName = "Prière Offensive";

    public PriereOffensive() {
        super();
        name = "Prière Offensive";
        textureID = 134;
        minRange = 0;
        maxRange = 0;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 1;
        isRangeVariable = false;
        zoneSize = 2;
        targetZone = SQUARE_ZONE;

        effectDescription = "Pour chaque allié dans la zone, les alliés gagne 15 Dommages (1t) (max 75).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        int amount = 0;
        for(Entity entity : targets) if(entity.getTeam() == caster.getTeam()) amount ++;
        Effect e = new Effect(effectName,2);
        e.add(new Boost("do", Math.min(15*amount,75), 2));
        for(Entity target : targets) {
            if(areSameTeam(caster, target)) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " gagne " + Math.min(15*amount,75) + " Dommages pendant 1 tour.");

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
        e.add(new Boost("do", 15, 2));
        effects.add(e);
    }
}

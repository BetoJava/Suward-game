package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Cicatrisation extends Spell {

    public Cicatrisation() {
        super();
        name = "Cicatrisation";
        textureID = 118;
        minRange = 0;
        maxRange = 4;
        critic = 15;
        manaCost = 3;
        maxUses = 2;
        maxUsesOnTarget = 1;
        isRangeVariable = false;

        effectDescription = "Soigne 8% des PV max. Retire l'état Transcendance.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            heal(target, (int) (target.getStats().getStat("pv").getMax()*0.08)+1);
            ArrayList<Effect> toRemove = new ArrayList<>();
            for(Effect e : target.getStats().getEffects()) {
                if(e.getName() == Transcendance.effectName) toRemove.add(e);
            }
            for(Effect e : toRemove) target.getStats().getEffects().remove(e);
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

    }

}

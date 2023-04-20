package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class DonSanguin extends Spell {

    public DonSanguin() {
        super();
        name = "Don Sanguin";
        textureID = 72;
        minRange = 1;
        maxRange = 2;
        manaCost = 2;
        maxUses = 2;
        maxUsesOnTarget = 1;
        isRangeVariable = false;

        effectDescription = "Donne gagner 1 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            target.getStats().add("pc",1);
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

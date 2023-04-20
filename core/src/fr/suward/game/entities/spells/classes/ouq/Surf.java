package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Surf extends Spell {

    public Surf() {
        super();
        name = "Surf";
        textureID = 120;
        push = -3;
        isRangeVariable = false;
        minRange = 1;
        maxRange = 1;
        concentrationCost = 1;
        maxUses = 2;
        targetType = EMPTY_TARGET;
        targetZone = LINE_ZONE;
        zoneSize = 3;

        effectDescription = "Avance l'utilisateur jusqu'à 3 cases.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        Point arrival = new Point(caster.getPosition().x, caster.getPosition().y);
        if(Math.abs(caster.getPosition().x - pos.x) > Math.abs(caster.getPosition().y - pos.y)) {
            if(caster.getPosition().x > pos.x) {
                arrival.x -= (-push);
            } else {
                arrival.x += (-push);
            }
        } else {
            if(caster.getPosition().y > pos.y) {
                arrival.y -= (-push);
            } else {
                arrival.y += (-push);
            }
        }

        forward(caster, arrival);

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

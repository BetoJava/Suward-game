package fr.suward.game.entities.spells.pdcity.oeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Liberation extends Spell {

    public Liberation() {
        super();
        name = "Libération";
        textureID = 72;
        minRange = 0;
        maxRange = 0;
        push = 5;
        concentrationCost = 3;
        maxUses = 1;
        isRangeVariable = false;
        zoneSize = 1;
        targetZone = Spell.CIRCLE_ZONE_WITHOUT_CENTER;

        effectDescription = "Repousse de 5 cases les entitées aux corps à corps.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
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

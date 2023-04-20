package fr.suward.game.entities.spells.pdcity.moustache;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class PoilSoyeux extends Spell {

    public PoilSoyeux() {
        super();
        name = "Poil Soyeux";
        textureID = 52;
        damageLines.add(new DamageLine(50, 50, DamageLine.FIRE_ELEMENT, false, true));
        minRange = 1;
        maxRange = 1;
        manaCost = 3;
        maxUses = 2;
        isRangeVariable = false;
        zoneSize = 1;
        targetZone = Spell.ROW_ZONE;

        effectDescription = "Soigne les alliés.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(areSameTeam(caster, target)) {
                hit(isCritical, caster, target, pos);
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
    }
}

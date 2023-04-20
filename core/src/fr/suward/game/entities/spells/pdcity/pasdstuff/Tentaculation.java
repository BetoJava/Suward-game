package fr.suward.game.entities.spells.pdcity.pasdstuff;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Tentaculation extends Spell {

    public Tentaculation() {
        super();
        name = "Tentaculation";
        textureID = 72;
        damageLines.add(new DamageLine(85, 100, DamageLine.WATER_ELEMENT, false, false));
        minRange = 1;
        maxRange = 1;
        critic = 8;
        manaCost = 6;
        maxUses = 1;
        isRangeVariable = false;

        effectDescription = "Occasionne de gros dommages Eau aux ennemis.";
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

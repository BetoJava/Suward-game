package fr.suward.game.entities.spells.pdcity.pasdstuff;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Ventouse extends Spell {

    public Ventouse() {
        super();
        name = "Ventouse";
        textureID = 26;
        damageLines.add(new DamageLine(25, 27, DamageLine.WATER_ELEMENT, false, false));
        push = -2;
        minRange = 1;
        maxRange = 7;
        critic = 10;
        manaCost = 3;
        maxUses = 2;

        effectDescription = "Occasionne des dommages Eau aux ennemis.\nAttire la cible.";
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

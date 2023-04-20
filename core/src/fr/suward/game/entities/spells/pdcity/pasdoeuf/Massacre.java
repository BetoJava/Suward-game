package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Massacre extends Spell {

    public Massacre() {
        super();
        name = "Massacre";
        textureID = 9;
        damageLines.add(new DamageLine(52, 60, DamageLine.LIGHTNING_ELEMENT, false, false));
        minRange = 1;
        maxRange = 2;
        critic = 8;
        manaCost = 4;
        maxUses = 4;
        maxUsesOnTarget = 2;
        isRangeVariable = false;

        effectDescription = "Occasionne des dommages Foudre aux ennemis.";
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

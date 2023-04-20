package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class OeufPourri extends Spell {

    public OeufPourri() {
        super();
        name = "Oeuf Pourri";
        textureID = 24;
        damageLines.add(new DamageLine(14, 16, DamageLine.AIR_ELEMENT, false, false));
        minRange = 2;
        maxRange = 11;
        critic = 10;
        manaCost = 2;
        maxUses = 6;
        maxUsesOnTarget = 1;
        isRangeVariable = true;
        isSightLine = false;

        effectDescription = "Occasionne des dommages Air aux ennemis.";
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

package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Fouguas extends Spell {

    public Fouguas() {
        super();
        name = "Fouguas";
        textureID = 72;
        damageLines.add(new DamageLine(22, 24, DamageLine.WATER_ELEMENT, true, false));
        minRange = 1;
        maxRange = 2;
        critic = 5;
        manaCost = 4;
        maxUses = 3;
        isRangeVariable = false;
        castZone = Spell.CROSS_CAST;

        effectDescription = "Occasionne des dommages Eau aux ennemis.\nFait gagner 1 PC à son invocateur.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        boolean hasHitFoe = false;
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) {
                hit(isCritical, caster, target, pos);
                hasHitFoe = true;
            }
        }
        if(hasHitFoe) {
            caster.getSummoner().getStats().add("pc",1);
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

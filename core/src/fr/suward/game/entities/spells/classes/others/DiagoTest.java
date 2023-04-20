package fr.suward.game.entities.spells.classes.others;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class DiagoTest extends Spell {

    public static int id = -2;

    public DiagoTest() {
        super();
        name = "Lacornik";
        textureID = 26;
        damageLines.add(new DamageLine(26, 29, DamageLine.AIR_ELEMENT, true, false));
        damageLines.add(new DamageLine(26, 29, DamageLine.FIRE_ELEMENT, true, false));
        damageLines.add(new DamageLine(26, 29, DamageLine.LIGHTNING_ELEMENT, true, false));
        damageLines.add(new DamageLine(26, 29, DamageLine.WATER_ELEMENT, true, false));
        minRange = 2;
        maxRange = 15;
        critic = 15;
        manaCost = 6;
        maxUses = 3;
        zoneSize = 4;

        targetZone = CIRCLE_ZONE;
        effectDescription = "Occasionne des dommages Air, Feu, Foudre et Eau aux ennemis.\nAugmente les dommages critiques de 20.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
        }
        for(Effect e : effects) {
            e.addEffectToTarget(caster, caster,"");
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
        Effect effect = new Effect("Test",2);
        effect.add(new Boost("do crit", 20, 2));
        effects.add(effect);
    }


}

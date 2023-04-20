package fr.suward.game.entities.spells.classes.others;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Heal extends Spell {

    public static int id = 7;

    public Heal() {
        super();
        name = "Heal";
        textureID = 52;
        damageLines.add(new DamageLine(50, 50, DamageLine.FIRE_ELEMENT, false, true));
        minRange = 0;
        maxRange = 15;
        critic = 15;
        manaCost = 1;
        maxUses = 100;
        isSightLine = false;

        effectDescription = "Occasionne des dommages Air aux ennemis.\nAttire la cible.\nRetire 20% de fuite et d'esquive.";
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

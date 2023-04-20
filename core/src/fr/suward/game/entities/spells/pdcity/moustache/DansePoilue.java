package fr.suward.game.entities.spells.pdcity.moustache;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class DansePoilue extends Spell {

    public static int id = 5;

    public DansePoilue() {
        super();
        name = "Danse Poilue";
        textureID = 72;
        damageLines.add(new DamageLine(40, 44, DamageLine.FIRE_ELEMENT, false, false));
        minRange = 0;
        maxRange = 0;
        push = 1;
        critic = 10;
        manaCost = 4;
        maxUses = 2;
        isRangeVariable = false;
        zoneSize = 2;
        targetZone = Spell.CIRCLE_ZONE_WITHOUT_CENTER;

        effectDescription = "Occasionne des dommages Feu aux ennemis et repousse d'une case.";
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

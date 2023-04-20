package fr.suward.game.entities.spells.pdcity.oeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Roulade extends Spell {

    public Roulade() {
        super();
        name = "Roulade";
        textureID = 72;
        minRange = 2;
        maxRange = 3;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 2;
        isRangeVariable = false;
        isSightLine = false;
        targetType = Spell.EMPTY_TARGET;
        effectDescription = "Téléporte sur la case ciblée et ajoute 1 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        teleport(caster, pos);
        caster.getStats().add("pc",1);
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

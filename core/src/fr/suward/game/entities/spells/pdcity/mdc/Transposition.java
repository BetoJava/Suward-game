package fr.suward.game.entities.spells.pdcity.mdc;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Transposition extends Spell {

    public Transposition() {
        super();
        name = "Transposition";
        textureID = 72;
        minRange = 1;
        maxRange = 5;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = true;
        targetType = Spell.ALLY_TARGET;

        effectDescription = "Echange de place avec l'allié.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        teleport(caster, pos);
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

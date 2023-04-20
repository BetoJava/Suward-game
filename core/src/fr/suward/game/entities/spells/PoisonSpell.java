package fr.suward.game.entities.spells;

import fr.suward.game.entities.Entity;

import java.awt.*;
import java.util.ArrayList;

public class PoisonSpell extends Spell {

    private float multiplier = 1f;

    public PoisonSpell() {
        super();
        index = 100;
    }

    public PoisonSpell(float multiplier) {
        super();
        this.multiplier = multiplier;
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(false, caster, target, pos, multiplier);
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

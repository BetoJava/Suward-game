package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Transcendance extends Spell {

    public static String effectName = "Transcendance";

    public Transcendance() {
        super();
        name = "Transcendance";
        textureID = 116;
        minRange = 0;
        maxRange = 0;
        concentrationCost = 3;
        maxUses = 1;
        isRangeVariable = false;

        effectDescription = "Augmente de 20% les dommages finaux et ajoute 1 PD pendant 4 tours.\nAjoute 13% d'érosion et retire 5% des PV max.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            target.takeDamage((int) (target.getStats().getStat("pv").getMax()*0.05), 0, 0);
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 20% dommages finaux, 1 PD, 13% d'érosion pendant 4 tours.");
            }
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
        Effect e = new Effect(effectName,5);
        e.add(new Boost("dg mel", 20, 5));
        e.add(new Boost("dg dist", 20, 5));
        e.add(new Boost("pd",1, 5));
        e.add(new Boost("ero",13, 5));
        effects.add(e);
    }

}

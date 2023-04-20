package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Fragmentation extends Spell {

    public static String effectName = "Fragmenté";
    public static int id = 0;

    public Fragmentation() {
        super();
        name = "Fragmentation";
        textureID = 108;
        damageLines.add(new DamageLine(6, 6, DamageLine.LIGHTNING_ELEMENT, false, false));
        minRange = 0;
        maxRange = 0;
        concentrationCost = 3;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 2;
        isRangeVariable = false;

        effectDescription = "Occasionne des dégats foudre pour chaque PM et PC utilisés sur chaque cible des attaques du lanceur (1 tour).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : spell.getEffects()) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " rentre dans l'état fragmenté.");
            }
            for(Spell s : target.getClassData().getSpells()) {
                if(!s.getDamageLines().isEmpty() && s.getClass() != this.getClass()) {
                    int cost = s.getManaCost() + s.getConcentrationCost();
                    s.getDamageLines().add(new DamageLine(6*cost, 6*cost, DamageLine.LIGHTNING_ELEMENT, false, false, 1));
                }
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
        Effect effect = new Effect(effectName,2);
        effects.add(effect);
    }

}

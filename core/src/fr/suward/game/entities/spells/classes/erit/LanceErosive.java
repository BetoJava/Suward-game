package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class LanceErosive extends Spell {

    public static String effectName = "Lance érodé";

    public LanceErosive() {
        super();
        name = "Lance érosive";
        textureID = 107;
        damageLines.add(new DamageLine(16, 18, DamageLine.WATER_ELEMENT, false, false));
        minRange = 1;
        maxRange = 6;
        critic = 15;
        manaCost = 2;
        maxUses = 3;
        maxUsesOnTarget = 2;

        castZone = Spell.DIAGONAL_CAST;
        effectDescription = "Occasionne des dommages Eau aux ennemis.\nSur ciblé : érode de 11% (2 tours) (max 2).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            if(target.getStats().countEffectWithName(effectName) < 2 && target.getStats().hasEffectWithName(Ciblage.effectName)) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " est érodé de 11% pour 2 tours.");
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
        Effect e = new Effect(effectName,3);
        e.add(new Boost("ero", 11, 3));
        effects.add(e);
    }
}

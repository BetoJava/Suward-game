package fr.suward.game.entities.spells.pdcity.moustache;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Poison;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class PoilUrtican extends Spell {

    public static String effectName = "Poison de Poil Urtican";

    public PoilUrtican() {
        super();
        name = "Poil Urtican";
        textureID = 24;
        damageLines.add(new DamageLine(17, 17, DamageLine.FIRE_ELEMENT, false, false, true));
        minRange = 6;
        maxRange = 10;
        critic = 10;
        manaCost = 3;
        maxUses = 3;
        maxUsesOnTarget = 1;
        isRangeVariable = true;

        effectDescription = "Applique un poison Feu pendant 2 tours aux ennemis.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : spell.getEffects()) {
                applyDamageLinesToPoisons(e);
                e.addEffectToTarget(target, caster, target.getPseudo() + " est empoisonné pendant 2 tours");
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
        Effect effect = new Effect(effectName,3);
        effect.add(new Poison(3));
        effects.add(effect);
    }
}

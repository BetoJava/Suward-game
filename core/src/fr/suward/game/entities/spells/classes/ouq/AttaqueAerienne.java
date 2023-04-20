package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class AttaqueAerienne extends Spell {

    public static String effectName = "Attaque Aerienne";

    public AttaqueAerienne() {
        super();
        name = "Attaque Aerienne";
        textureID = 124;
        damageLines.add(new DamageLine(34, 36, DamageLine.AIR_ELEMENT, false, false));
        push = 1;
        minRange = 1;
        maxRange = 4;
        critic = 15;
        manaCost = 4;
        maxUses = 1;
        isRangeVariable = false;
        isSightLine = false;
        targetType = EMPTY_TARGET;
        zoneSize = 1;
        isPushFromCenter = true;

        targetZone = CROSS_ZONE;
        effectDescription = "Occasionne des dommages Air, téléporte sur la case ciblée et repousse d'une case.\nApplique l'état pesanteur aux cibles.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        if(targets.contains(caster)) targets.remove(caster);
        teleport(caster, pos);
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " rentre dans l'état pesanteur pendant 1 tour.");
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
        Effect e = new Effect(effectName,2);
        e.add(new Boost(Effect.GRAVITY,2));
        effects.add(e);
    }

}

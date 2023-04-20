package fr.suward.game.entities.spells.pdcity.pasdstuff;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Moinsdstuff extends Spell {

    public static String effectName = "Moinsd'stuff";

    public Moinsdstuff() {
        super();
        name = "Moinsdstuff";
        textureID = 72;
        damageLines.add(new DamageLine(28, 32, DamageLine.WATER_ELEMENT, false, false));
        minRange = 1;
        maxRange = 4;
        critic = 10;
        manaCost = 4;
        maxUses = 2;
        isRangeVariable = false;
        castZone = Spell.DIAGONAL_CAST;
        zoneSize = 2;
        targetZone = Spell.SQUARE_ZONE;

        effectDescription = "Occasionne des dommages Eau aux ennemis et retire 10% de résistance pendant 2 tours.\nPour chaque ennemi, gagne 1 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        int amountOfFoes = 0;
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) {
                amountOfFoes ++;
                hit(isCritical, caster, target, pos);
                if(target.getStats().countEffectWithName(effectName) < 2) {
                    for(Effect e : effects) {
                        e.addEffectToTarget(target, caster, target.getPseudo() + " perd 10% de résistance (2 tours).");
                    }
                }
            }
        }
        caster.getStats().add("pc",amountOfFoes);
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
        e.add(new Boost("res eau", -10, 3));
        e.add(new Boost("res feu", -10, 3));
        e.add(new Boost("res air", -10, 3));
        e.add(new Boost("res foudre", -10, 3));
        effects.add(e);
    }
}

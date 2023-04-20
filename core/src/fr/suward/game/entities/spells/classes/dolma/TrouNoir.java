package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class TrouNoir extends Spell {

    public TrouNoir() {
        super();
        name = "Trou Noir";
        textureID = 127;
        damageLines.add(new DamageLine(33, 34, DamageLine.WATER_ELEMENT, false, false));
        minRange = 1;
        maxRange = 6;
        critic = 10;
        manaCost = 5;
        maxUses = 1;
        isRangeVariable = false;
        zoneSize = 3;
        targetZone = SQUARE_ZONE;

        effectDescription = "Occasionne des dommages Eau aux ennemis.\nChaque état pesanteur consommé par le lanceur augmente les dégats de base du sort de 25.\nLes boost du sort disparaissent après utilisation.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(!areSameTeam(target, caster)) hit(isCritical, caster, target, pos);
        }
        spell.getSpellBoosts().clear();
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

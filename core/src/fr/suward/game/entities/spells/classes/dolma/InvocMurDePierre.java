package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.classes.invocations.MurDePierre;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class InvocMurDePierre extends Spell {

    public InvocMurDePierre() {
        super();
        name = "Mur de Pierre";
        textureID = 136;
        minRange = 1;
        maxRange = 3;
        concentrationCost = 1;
        manaCost = 1;
        maxUses = 1;
        maxDelay = 4;
        isRangeVariable = false;
        isSummonSpell = true;

        targetType = EMPTY_TARGET;

        zoneSize = 1;
        targetZone = ROW_ZONE;
        castZone = CROSS_CAST;
        effectDescription = "Invoque 3 Murs de Pierre (100 PV, 50% résistance) qui bloquent les lignes de vue.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Point p : zone) {
            boolean isEmpty = true;
            for(Entity e : targets) {
                if(e.getPosition().x == p.x && e.getPosition().y == p.y) {
                    isEmpty = false;
                    break;
                }
            }
            if(!isEmpty) continue;
            MurDePierre h = new MurDePierre();
            Character c = new Character("Mur de Pierre", h);
            summon(caster, p, c);
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

package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Hook extends Spell {

    public static int id = 7;

    public Hook() {
        super();
        name = "Hook";
        textureID = 122;
        damageLines.add(new DamageLine(22, 25, DamageLine.AIR_ELEMENT, false, false));
        push = -2;
        minRange = 1;
        maxRange = 5;
        critic = 15;
        manaCost = 3;
        maxUses = 2;

        castZone = Spell.CROSS_CAST;
        effectDescription = "Occasionne des dommages Air aux ennemis.\nAttire la cible.\nRetire 20% de fuite et d'esquive.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            hit(isCritical, caster, target, pos);
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " perd 20% de fuite et d'esquive pendant 2 tours.");
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
        Effect e = new Effect("Accroché",2);
        e.add(new Boost("fuite", -0.2f, 2, false, "fuite"));
        e.add(new Boost("esquive", -0.2f, 2, false, "esquive"));
        effects.add(e);
    }

}

package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Canalisation extends Spell {

    public static String effectName = "Canalisation";
    public static int id = 1;

    public Canalisation() {
        super();
        name = "Canalisation";
        textureID = 109;
        minRange = 0;
        maxRange = 0;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 4;
        isRangeVariable = false;
        zoneSize = 3;
        targetZone = Spell.CROSS_ZONE;

        effectDescription = "Donne 2 PM pour 2 tours aux alliés.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(target.getStats().countEffectWithName(effectName) < 1) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 2 PM pendant 2 tours.");
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
        e.add(new Boost("pm", 2, 3));
        effects.add(e);
    }
}

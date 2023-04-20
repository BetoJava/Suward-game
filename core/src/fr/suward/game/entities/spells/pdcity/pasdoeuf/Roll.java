package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Roll extends Spell {

    public static String effectName = ":roll:";

    public Roll() {
        super();
        name = ":roll:";
        textureID = 63;
        minRange = 4;
        maxRange = 6;
        critic = 10;
        manaCost = 4;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = false;
        isSightLine = false;

        castZone = Spell.CROSS_CAST;
        targetType = Spell.EMPTY_TARGET;
        targetZone = Spell.TRAIL_ZONE;
        effectDescription = "Traverse jusqu'à la case ciblée.\nRetire 3PM aux cible sur la trainée.";

    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        teleport(caster, pos);
        for(Entity target : targets) {
            for(Effect e : effects) {
                e.addEffectToTarget(target, caster, target.getPseudo() + "  perd 3 PM pour 1 tour.");
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
        e.add(new Boost("pm", -3, 2));
        effects.add(e);
    }
}

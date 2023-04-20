package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class GrosFront extends Spell {

    public static String effectName = "Gros Front";

    public GrosFront() {
        super();
        name = "Gros Front";
        textureID = 80;
        minRange = 0;
        maxRange = 0;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 4;
        isRangeVariable = false;

        effectDescription = "+ 5 PC/PO/PM + 100% Dommages finaux dans 1 tour.\nPour chaque coup reçu pendant 1 tour, retire :\n   - 1 PC/PO/PM\n   - 10% Res/Dommages finaux";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            for(Effect e : spell.getEffects()) {
                e.addEffectToTarget(target, caster, target.getPseudo() + " gagnera 5 PC/PO/PM et 50% Dommages finaux dans 1 tour.\nPour chaque coup reçu pendant 1 tour, retire : 1 PC/PO/PM et 10% Res/Dommages finaux");
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
        effect.add(new Boost("pm", 5, 2, 1));
        effect.add(new Boost("pc", 5, 2, 1));
        effect.add(new Boost("po", 5, 2, 1));
        effect.add(new Boost("dg mel", 100, 2, 1));
        effect.add(new Boost("dg dist", 100, 2, 1));
        effects.add(effect);
    }
}

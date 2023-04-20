package fr.suward.game.entities.spells.pdcity.mdc;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Fermentation extends Spell {

    public static String effectName = "Fermenté";

    public Fermentation() {
        super();
        name = "Fermentation";
        textureID = 72;
        minRange = 1;
        maxRange = 5;
        concentrationCost = 2;
        maxUses = 3;
        isRangeVariable = false;
        isSightLine = false;

        effectDescription = "Sur Chaî : +2 PO de zone d'explosion et +65% DG.\n" +
                            "Sur Allié : +25% Resistance (2 tours)";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(areSameTeam(caster, target)) {
                for(Effect e : spell.getEffects()) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " est fermenté pendant 2 tours");
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
        Effect effect = new Effect(effectName,3);
        effect.add(new Boost("res eau", 25, 3));
        effect.add(new Boost("res foudre", 25, 3));
        effect.add(new Boost("res feu", 25, 3));
        effect.add(new Boost("res air", 25, 3));
        effects.add(effect);
    }
}

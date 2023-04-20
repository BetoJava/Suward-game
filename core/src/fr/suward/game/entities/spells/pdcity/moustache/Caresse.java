package fr.suward.game.entities.spells.pdcity.moustache;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class Caresse extends Spell {

    public static String effectName = "Caresse de Gégé";

    public Caresse() {
        super();
        name = "Caresse de Gégé";
        textureID = 52;
        minRange = 1;
        maxRange = 4;
        manaCost = 2;
        maxUses = 3;
        maxUsesOnTarget = 1;
        isRangeVariable = true;

        effectDescription = "Donne 2 PO (1 tour) et soigne 5% des PV max de l'allié.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(areSameTeam(caster, target)) {
                heal(target, (int) (target.getStats().getStat("pv").getMax()*0.05)+1);
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " gagne 2 PO pour 1 tour.");
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
        Effect e = new Effect(effectName,2);
        e.add(new Boost("po", 2, 2));
        effects.add(e);
    }
}

package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.assets.FontManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class MiseAuCube extends Spell {

    public static String effectName = "Mise au Cube";
    public static String effectName1 = "Mise au Cube I";
    public static String effectName2 = "Mise au Cube II";

    public MiseAuCube() {
        super();
        name = "Mise au Cube";
        textureID = 127;
        damageLines.add(new DamageLine(18, 20, DamageLine.WATER_ELEMENT, false, false));
        minRange = 1;
        maxRange = 5;
        critic = 10;
        manaCost = 4;
        maxUses = 3;

        isRangeVariable = false;

        effectDescription = "Occasionne des dommages Eau aux ennemis.\nApplique une charge cubique supplémentaire.\nSi la cible possède 3 charges, applique l'état pesanteur (2 tours).";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(!areSameTeam(target, caster)) {
                hit(isCritical, caster, target, pos);
                int count1 = 0;
                int count2 = 0;
                ArrayList<Effect> toRemove = new ArrayList<>();
                for(Effect e : target.getStats().getEffects()) {
                    if(e.getName() == effectName1) {
                        count1 ++;
                        toRemove.add(e);
                    } else if(e.getName() == effectName2) {
                        count2 ++;
                        toRemove.add(e);
                    }
                }
                for(Effect e : toRemove) {
                    target.getStats().getEffects().remove(e);
                }
                Effect effect;
                if(count1 + count2 == 0) {
                    effect = new Effect(effectName1, 1000);
                } else if(count2 > 0) {
                    effect = new Effect(effectName, 1000);
                    effect.add(new Boost(Effect.GRAVITY, 3));
                } else {
                    effect = new Effect(effectName2, 1000);
                }
                effect.addEffectToTarget(target, caster, "");
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

    }


}

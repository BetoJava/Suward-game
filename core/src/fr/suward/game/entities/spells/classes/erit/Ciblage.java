package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;


public class Ciblage extends Spell {

    public static String effectName = "Ciblé";
    public static float healCoefficient = 0.12f;
    public static int id = 0;

    public Ciblage() {
        super();
        name = "Ciblage";
        textureID = 111;
        minRange = 1;
        maxRange = 7;
        manaCost = 2;
        maxUses = 1;
        maxDelay = 1;
        isSightLine = false;
        isRangeVariable = false;


        effectDescription = "Applique l'état ciblé à le cible : 12% des dommages infligés à la cible sont convertis en soin pour le lanceur.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) {
                if(target.getStats().countEffectWithName(effectName) <= 0) {
                    removeEffectOnOldTarget(caster);
                    for(Effect e : spell.getEffects()) {
                        e.addEffectToTarget(target, caster, target.getPseudo() + " est ciblé par " + caster.getPseudo() + ".");
                    }
                }
            }
        }
    }

    private void removeEffectOnOldTarget(Entity caster) {
        for(Entity e : ClientManager.get().getAliveEntities()) {
            if(e.getTeam() != caster.getTeam()) {
                Effect toRemove = null;
                for(Effect ef : e.getStats().getEffects()) {
                    if(ef.getName() == effectName) {
                        toRemove = ef;
                        break;
                    }
                }
                if(toRemove != null) {
                    e.getStats().getEffects().remove(toRemove);
                    break;
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
        Effect effect = new Effect(effectName,100000000);
        effects.add(effect);
    }

}

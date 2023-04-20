package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class Perfisition extends Spell {

    public static String effectName = "Perfisition";

    public Perfisition() {
        super();
        name = "Perfisition";
        textureID = 117;
        minRange = 1;
        maxRange = 6;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 3;
        isTargetNecessary = true;

        castZone = Spell.CROSS_CAST;
        effectDescription = "Sur Epée Sanglante : tue la cible et récupère ses PV en soin. Le sort peut être relancé.\nSur ennemi : vol 30 Dommages et applique l'état insoignable pendant 2 tours.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        Entity target = targets.get(0);
        if(target.getTeam() != caster.getTeam()) {
            effects.get(0).addEffectToTarget(target, caster, target.getPseudo() + " perd 30 Dommages et rentre dans l'état Insoignable pendant 2 tours.");
            effects.get(1).addEffectToTarget(caster, caster, caster.getPseudo() + " gagne 30 Dommages pendant 2 tours.");
        } else if(target.getClassData().getClassName() == "EpeeSanglante") {
            int heal = target.getStats().get("pv");
            System.out.println(target.getPseudo());
            System.out.println(heal);
            heal(caster, heal);
            System.out.println(heal);
            target.takeDamage(heal,0 ,0);
            System.out.println(heal);
            spell.setDelay(0);
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
        e.add(new Boost("do", -30, 3));
        e.add(new Boost(Effect.INCURABLE, 3));
        effects.add(e);
        Effect e2 = new Effect(effectName,3);
        e2.add(new Boost("do", 30, 3));
        effects.add(e2);
    }

}

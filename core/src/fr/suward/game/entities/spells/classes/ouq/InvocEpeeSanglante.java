package fr.suward.game.entities.spells.classes.ouq;

import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.classes.invocations.EpeeSanglante;
import fr.suward.game.entities.classes.invocations.Hallebarde;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;


public class InvocEpeeSanglante extends Spell {

    public static String invocationName = "Epée Sanglante";

    public InvocEpeeSanglante() {
        super();
        name = "Epée Sanglante";
        textureID = 113;
        minRange = 1;
        maxRange = 3;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = false;
        isSummonSpell = true;

        targetType = Spell.EMPTY_TARGET;
        effectDescription = "Invoque une Epée Sanglante qui attire et vol de la vie aux ennemis.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        EpeeSanglante h = new EpeeSanglante();
        Character c = new Character("Epée Sanglante de " + caster.getPseudo(), h);
        summon(caster, pos, c, true);
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

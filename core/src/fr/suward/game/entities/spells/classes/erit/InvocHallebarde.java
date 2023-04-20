package fr.suward.game.entities.spells.classes.erit;

import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.classes.invocations.Hallebarde;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;


public class InvocHallebarde extends Spell {

    public static String invocationName = "Hallebarde";

    public InvocHallebarde() {
        super();
        name = "Hallebarde";
        textureID = 102;
        minRange = 1;
        maxRange = 3;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = false;
        isSummonSpell = true;

        targetType = Spell.EMPTY_TARGET;
        effectDescription = "Invoque une Hallebarde qui fait gagner 1 PC par attaque sur un ennemi.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        Hallebarde h = new Hallebarde();
        Character c = new Character("Hallebarde de " + caster.getPseudo(), h);
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

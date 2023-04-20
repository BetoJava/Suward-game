package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.classes.invocations.Hypercube;
import fr.suward.game.entities.spells.Spell;

import java.awt.*;
import java.util.ArrayList;

public class InvocHypercube extends Spell {

    public InvocHypercube() {
        super();
        name = "Hypercube";
        textureID = 135;
        minRange = 1;
        maxRange = 3;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 3;
        isRangeVariable = false;
        isSummonSpell = true;

        targetType = Spell.EMPTY_TARGET;
        effectDescription = "Invoque un Hypercube qui peut appliquer l'état pesanteur et donner 1 PM aux alliés.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        Hypercube h = new Hypercube();
        Character c = new Character("Hypercube de " + caster.getPseudo(), h);
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

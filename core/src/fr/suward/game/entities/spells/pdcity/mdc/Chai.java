package fr.suward.game.entities.spells.pdcity.mdc;

import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.classes.invocations.Hallebarde;
import fr.suward.game.entities.classes.monsters.pdcity.invocations.ChaiInvoc;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class Chai extends Spell {

    public Chai() {
        super();
        name = "Chaî";
        textureID = 72;
        minRange = 1;
        maxRange = 4;
        manaCost = 3;
        maxUses = 1;
        targetType = Spell.EMPTY_TARGET;
        isSummonSpell = true;
        isRangeVariable = false;
        isSightLine = false;
        zoneSize = 2;

        effectDescription = "Invoque une bouteille de Chaî qui explose dans 1 tour en cercle de 2 cases.\n" +
                            "Chaî : 650 PV";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        ChaiInvoc cd = new ChaiInvoc();
        Character c = new Character("Chaî de " + caster.getPseudo(), cd);
        c.setStatic(true);
        summon(caster, pos, c);
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

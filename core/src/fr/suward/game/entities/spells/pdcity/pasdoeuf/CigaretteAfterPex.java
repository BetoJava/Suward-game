package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.assets.FontManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class CigaretteAfterPex extends Spell {

    public CigaretteAfterPex() {
        super();
        name = "Cigarette After Pex";
        textureID = 19;
        minRange = 0;
        maxRange = 0;
        concentrationCost = 8;
        maxUses = 1;
        maxDelay = 2;
        isRangeVariable = false;

        effectDescription = "Soigne 15% des PV max.\n Regagne 6 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        caster.getStats().add("pc", 6);
        for(Entity target : targets) {
            heal(target, (int) (target.getStats().getStat("pv").getMax()*0.15)+1);
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

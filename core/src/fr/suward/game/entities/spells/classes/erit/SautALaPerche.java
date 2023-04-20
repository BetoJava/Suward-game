package fr.suward.game.entities.spells.classes.erit;

import fr.suward.assets.FontManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class SautALaPerche extends Spell {


    public SautALaPerche() {
        super();
        name = "Saut à la Perche";
        textureID = 112;
        damageLines.add(new DamageLine(33, 36, DamageLine.LIGHTNING_ELEMENT, false, false));
        minRange = 2;
        maxRange = 5;
        critic = 10;
        manaCost = 4;
        maxUses = 1;
        maxDelay = 2;
        isRangeVariable = false;
        isSightLine = false;
        isDistanceReduced = false;

        castZone = Spell.CROSS_CAST;
        targetType = Spell.EMPTY_TARGET;
        targetZone = Spell.TRAIL_ZONE;
        effectDescription = "Occasionne des dommages Foudre aux ennemis.\nTraverse jusqu'à la case ciblée.\nGagne 1 PC par cible sur la trainée.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        teleport(caster, pos);
        if(targets.size() > 0) {
            caster.getStats().add("pc", targets.size());
            ClientManager.get().sendMessageInChat(caster.getPseudo() + " gagne " + targets.size() + " PC !", FontManager.flashyGreen);
        }
        for(Entity target : targets) {
            if(!areSameTeam(caster, target)) {
                hit(isCritical, caster, target, pos);
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

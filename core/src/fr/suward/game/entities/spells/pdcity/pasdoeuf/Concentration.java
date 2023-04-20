package fr.suward.game.entities.spells.pdcity.pasdoeuf;

import fr.suward.assets.FontManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class Concentration extends Spell {

    public Concentration() {
        super();
        name = "Concentration";
        textureID = 72;
        minRange = 0;
        maxRange = 0;
        critic = 0;
        manaCost = 0;
        maxUses = 1;
        isRangeVariable = false;
        isDistanceReduced = false;

        targetZone = Spell.INVERT_CIRCLE_ZONE;
        zoneSize = 6;
        effectDescription = "Ajoute 5 PC au lanceur.\nPour chaque entité hors invocation dans la zone, retire 1 PC au lanceur.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        int amount = 5 - targets.size();
        caster.getStats().add("pc", amount);
        if(amount > 0) {
            ClientManager.get().sendMessageInChat(caster.getPseudo() + " gagne " + amount + " PC !", FontManager.flashyGreen);
        } else if(amount < 0){
            ClientManager.get().sendMessageInChat(caster.getPseudo() + " perd " + amount + " PC !", FontManager.flashyGreen);
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

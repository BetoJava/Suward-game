package fr.suward.network.packets;

import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class SpellCastingPacket {

    private Spell spell;
    private int casterID;
    private ArrayList<Integer> targetsID = new ArrayList<>();
    private ArrayList<Point> entitiesPositions;
    private ArrayList<Point> zone;
    private Point clickPos;

    private boolean isCritical;
    private ArrayList<Integer> rds; // For special randoms //

    public SpellCastingPacket() {

    }

    public SpellCastingPacket(Spell spell, Entity caster, ArrayList<Entity> targets, ArrayList<Point> entitiesPositions, ArrayList<Point> zone, Point clickPos) {
        this.spell = spell; //NON ID seulement
        this.casterID = caster.getId();
        for(Entity e : targets) {
            this.targetsID.add(e.getId());
        }
        this.entitiesPositions = entitiesPositions;
        this.zone = zone;
        this.clickPos = clickPos;
    }

    public SpellCastingPacket(Spell spell, Entity caster, ArrayList<Entity> targets, ArrayList<Point> entitiesPositions, ArrayList<Point> zone, Point clickPos, boolean isCritical, ArrayList<Integer> rds) {
        this.spell = spell;
        this.casterID = caster.getId();
        for(Entity e : targets) {
            this.targetsID.add(e.getId());
        }
        this.entitiesPositions = entitiesPositions;
        this.zone = zone;
        this.clickPos = clickPos;
        this.isCritical = isCritical;
        this.rds = rds;
    }

    public Spell getSpell() {
        return spell;
    }

    public Entity getCaster() {
        return ClientManager.get().getGameManager().getEntityFromID(casterID);
    }

    public ArrayList<Entity> getTargets() {
        ArrayList<Entity> targets = new ArrayList<>();
        for(int id : targetsID) {
            targets.add(ClientManager.get().getGameManager().getEntityFromID(id));
        }
        return targets;
    }

    public ArrayList<Point> getEntitiesPositions() {
        return entitiesPositions;
    }

    public ArrayList<Point> getZone() {
        return zone;
    }

    public Point getClickPos() {
        return clickPos;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public ArrayList<Integer> getRds() {
        return rds;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }
}

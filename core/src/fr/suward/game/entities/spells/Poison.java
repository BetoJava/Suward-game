package fr.suward.game.entities.spells;

import fr.suward.game.entities.Entity;
import fr.suward.game.utils.BattleFunctions;
import fr.suward.managers.ClientManager;
import fr.suward.network.packets.SpellCastingPacket;

import java.awt.*;
import java.util.ArrayList;

public class Poison {

    private int casterID;
    private int duration;
    private int delay = -1;

    private DamageLine damageLine;

    public Poison(){

    }

    public Poison(int turnRemaining) {
        this.duration = turnRemaining;

    }

    public Poison(int turnRemaining, int delay) {
        this(turnRemaining);
        this.delay = delay;
    }

    public Poison(int casterID, DamageLine damageLine, int turnRemaining) {
        this.damageLine = damageLine;
        this.casterID = casterID;
        this.duration = turnRemaining;
        this.damageLine.setPoison(false);

    }

    public Poison(int casterID, DamageLine damageLine, int turnRemaining, int delay) {
        this(casterID, damageLine, turnRemaining);
        this.delay = delay;
    }

    public boolean update() {
        if(delay <= 0) {
            duration -= 1;
        }
        delay -= 1;
        return duration <= 0;

    }

    public void act(Entity target) {
        act(target, 1);
    }

    public void act(Entity target, final float multiplier) {
        Entity caster = ClientManager.get().getGameManager().getEntityFromID(casterID);
        if(ClientManager.get().getClient().getID() == caster.getSummonerOrSelf().getId()) {
            ArrayList<Entity> targets = new ArrayList<>();
            targets.add(target);
            PoisonSpell poisonSpell = new PoisonSpell(multiplier);
            poisonSpell.getDamageLines().add(damageLine);
            ClientManager.get().getClient().sendTCP(new SpellCastingPacket(poisonSpell, caster, targets, null, null, target.getPosition()));
        }
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public DamageLine getDamageLine() {
        return damageLine;
    }

    public void setDamageLine(DamageLine damageLine) {
        this.damageLine = damageLine;
    }

    public void setCasterID(int casterID) {
        this.casterID = casterID;
    }

    public int getCasterID() {
        return casterID;
    }
}

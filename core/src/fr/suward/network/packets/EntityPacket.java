package fr.suward.network.packets;




import fr.suward.game.entities.stats.Stats;

import java.awt.*;

public class EntityPacket {

    private int classID;
    private String pseudo;
    protected int niv;
    protected int id;

    protected Stats stats;

    protected int team ;
    protected int iniOrder;
    protected int turn;
    protected boolean isReady;
    protected Point position;
    protected boolean isInvocation;
    protected boolean isStatic;

    private int exp;
    private int power;
    private int statPoints;

    public EntityPacket(int classID, String pseudo, int niv, int id, Stats stats, int team, int iniOrder, int turn, boolean isReady, Point position, boolean isInvocation, boolean isStatic, int exp, int power, int statPoints) {
        this.classID = classID;
        this.pseudo = pseudo;
        this.niv = niv;
        this.id = id;
        this.stats = stats;
        this.team = team;
        this.iniOrder = iniOrder;
        this.turn = turn;
        this.isReady = isReady;
        this.position = position;
        this.isInvocation = isInvocation;
        this.isStatic = isStatic;
        this.exp = exp;
        this.power = power;
        this.statPoints = statPoints;
    }

    public EntityPacket() {

    }

    public int getClassID() {
        return classID;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getNiv() {
        return niv;
    }

    public int getId() {
        return id;
    }

    public Stats getStats() {
        return stats;
    }

    public int getTeam() {
        return team;
    }

    public int getIniOrder() {
        return iniOrder;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isReady() {
        return isReady;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isInvocation() {
        return isInvocation;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public int getExp() {
        return exp;
    }

    public int getPower() {
        return power;
    }

    public int getStatPoints() {
        return statPoints;
    }
}

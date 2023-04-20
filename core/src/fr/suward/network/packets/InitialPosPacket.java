package fr.suward.network.packets;

public class InitialPosPacket {

    private int entityID;
    private int x;
    private int y;
    private boolean isReady;
    private int team;


    public InitialPosPacket() {

    }

    public InitialPosPacket(int entityID, int x, int y, boolean isReady, int team) {
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.isReady = isReady;
        this.team = team;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEntityID() {
        return entityID;
    }

    public boolean isReady() {
        return isReady;
    }

    public int getTeam() {
        return team;
    }
}

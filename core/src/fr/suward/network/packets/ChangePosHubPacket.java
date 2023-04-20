package fr.suward.network.packets;

public class ChangePosHubPacket {

    private int x;
    private int y;
    private int id;

    public ChangePosHubPacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChangePosHubPacket(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public ChangePosHubPacket() {

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

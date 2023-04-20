package fr.suward.network.packets;

public class BoostPacket {

    private int value;
    private int targetID;
    private String name;

    public BoostPacket() {
    }

    public BoostPacket(int targetID, String name, int value) {
        this.value = value;
        this.name = name;
        this.targetID = targetID;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTargetID() {
        return targetID;
    }

    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }
}

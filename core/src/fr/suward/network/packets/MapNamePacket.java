package fr.suward.network.packets;

public class MapNamePacket {

    private String name;

    public MapNamePacket() {

    }

    public MapNamePacket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

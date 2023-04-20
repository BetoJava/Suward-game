package fr.suward.network.packets;

public class StartBattleStatePacket {

    private String mapName;

    public StartBattleStatePacket() {

    }

    public StartBattleStatePacket(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }
}

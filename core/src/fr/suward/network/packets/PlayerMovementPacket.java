package fr.suward.network.packets;

import fr.suward.game.pathfinding.Path;

public class PlayerMovementPacket {

    private int entityID;
    private Path path;

    public PlayerMovementPacket() {
    }

    public PlayerMovementPacket(int entityID, Path path) {
        this.entityID = entityID;
        this.path = path;
    }

    public int getEntityID() {
        return entityID;
    }

    public Path getPath() {
        return path;
    }
}

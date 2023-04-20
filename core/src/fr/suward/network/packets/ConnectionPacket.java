package fr.suward.network.packets;


import java.util.ArrayList;

/***
 * Packet de nouvelle connexion :
 *   1. Le Client envoie son Character avec un ConnectionPacket
 *   2. Le Serveur ajoute l'entité à sa liste d'entités
 *   3. Le Serveur envoie le nom de la map et la liste des entités avec un ConnectionPacket
 */
public class ConnectionPacket {

    // From Client //
    private EntityPacket entity;

    // From Server //
    private String mapName;
    private ArrayList<EntityPacket> entityPackets;

    public ConnectionPacket() {

    }

    public ConnectionPacket(EntityPacket entity) {
        this.entity = entity;
    }

    public ConnectionPacket(String mapName, ArrayList<EntityPacket> entityPackets) {
        this.mapName = mapName;
        this.entityPackets = entityPackets;
    }



    public EntityPacket getEntity() {
        return entity;
    }

    public String getMapName() {
        return mapName;
    }

    public ArrayList<EntityPacket> getEntityPackets() {
        return entityPackets;
    }
}

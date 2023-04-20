package fr.suward.network.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import fr.suward.managers.GameManager;
import fr.suward.game.entities.Entity;
import fr.suward.managers.PacketManager;
import fr.suward.network.packets.StartingBattlePacket;
import fr.suward.network.receivers.ServerReceiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SuwardServer {


    private Server server;
    private String mapName;

    private GameManager gameManager;

    public SuwardServer(String mapName) {
        gameManager = new GameManager(mapName);
        create();
    }

    public void create() {

        server = new Server(1000000, 1000000);
        Kryo kryo = server.getKryo();

        PacketManager.registerPackets(kryo);

        server.start();

        // Bind to TCP port //
        try {
            server.bind(6845);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new ServerReceiver(this));
    }

    public void update() {

        for(Entity e : gameManager.getAliveEntities()) {
        }
        if(!gameManager.isBattleStarted() && gameManager.isEveryOneReady()) {
            gameManager.setBattleStarted(true);

            ArrayList<Integer> timeline = createTimeline();
            gameManager.setTimeline(timeline);
            server.sendToAllTCP(new StartingBattlePacket(timeline));
        }

    }


    private ArrayList<Integer> createTimeline() {
        ArrayList<Entity> timeline = new ArrayList<>();

        ArrayList<Entity> entities = gameManager.getAliveEntities();
        ArrayList<Entity> blueTeam = new  ArrayList<>();
        ArrayList<Entity> redTeam = new  ArrayList<>();
        int blueIni = 0;
        int redIni = 0;

        for(Entity e : entities) {
            if(e.getTeam() == 1) {
                blueIni += e.getStats().get("ini");
                blueTeam.add(e);
            } else {
                redIni += e.getStats().get("ini");
                redTeam.add(e);
            }
        }

        sort(blueTeam);
        sort(redTeam);

        if(blueIni > redIni) {
            timeline.add(blueTeam.remove(0));
        }

        while(timeline.size() < entities.size()) {
            if(!redTeam.isEmpty()) {
                timeline.add(redTeam.remove(redTeam.size() - 1));
            }
            if(!blueTeam.isEmpty()) {
                timeline.add(blueTeam.remove(blueTeam.size() - 1));
            }
        }


        ArrayList<Integer> tl = new ArrayList<>();
        for(int i = 0; i < timeline.size(); i++) {
            tl.add(timeline.get(i).getId());
        }

        return tl;

    }

    public void sort(ArrayList<Entity> entities) {
        Collections.sort(entities, new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                if(o1.getStats().get("ini") == o2.getStats().get("ini")) {
                    return 0;
                } else if (o1.getStats().get("ini") > o2.getStats().get("ini")) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }


    public GameManager getGameManager() {
        return gameManager;
    }

    public Server getServer() {
        return server;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}

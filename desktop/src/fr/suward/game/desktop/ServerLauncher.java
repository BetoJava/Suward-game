package fr.suward.game.desktop;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.classes.ClassTest;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.stats.Stat;
import fr.suward.game.entities.stats.Stats;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerLauncher {

    private static Server server;

    public static void main(String[] args) {
        server = new Server();
        Kryo kryo = server.getKryo();
        kryo.register(ClassTest.class);
        kryo.register(Character.class);
        kryo.register(ClassData.class);
        kryo.register(Point.class);
        kryo.register(Stat.class);
        kryo.register(Stats.class);
        kryo.register(Entity.class);
        kryo.register(ArrayList.class);
        kryo.register(HashMap.class);
        server.start();
        try {
            server.bind(6845);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addListener(server);
    }

    private static void addListener(Server server) {
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Entity) {
                    Entity request = (Entity)object;

                    Character response = new Character("Thanks",new ClassTest());
                    connection.sendTCP(response);
                }
                if (object instanceof String) {
                    String request = (String)object;
                }
            }
        });
    }
}

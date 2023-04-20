package fr.suward.network.receivers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import fr.suward.display.states.stages.BattleState;
import fr.suward.managers.ClientManager;
import fr.suward.managers.GameManager;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.*;
import fr.suward.network.server.SuwardServer;


public class ServerReceiver extends Listener {

    private SuwardServer suwardServer;

    public ServerReceiver(SuwardServer suwardServer) {
        this.suwardServer = suwardServer;
    }


    public void received (Connection connection, Object object) {
        GameManager gm = suwardServer.getGameManager();

        if(object instanceof ConnectionPacket) {
            ConnectionPacket request = (ConnectionPacket) object;
            Entity newEntity = new Character(request.getEntity());
            newEntity.setId(connection.getID());
            gm.addEntity(newEntity);

            ConnectionPacket response = new ConnectionPacket(gm.getMapIso().getMapName(), gm.getEntityPackets());
            suwardServer.getServer().sendToAllTCP(response);
        }

        if(object instanceof ChatMessagePacket) {
            suwardServer.getServer().sendToAllExceptTCP(connection.getID(), object);
        }

        if(object instanceof InitialPosPacket) {
            InitialPosPacket request = (InitialPosPacket) object;
            gm.update(request);

            suwardServer.update();
            suwardServer.getServer().sendToAllTCP(object);
        }

        if(object instanceof PassTurnPacket) {
            PassTurnPacket request = (PassTurnPacket) object;
            gm.updateServer(request);

            suwardServer.getServer().sendToAllTCP(object);
        }

        if(object instanceof PlayerMovementPacket) {
            PlayerMovementPacket request = (PlayerMovementPacket) object;
            suwardServer.getServer().sendToAllTCP(object);
        }

        if(object instanceof SpellCastingPacket) {
            SpellCastingPacket request = (SpellCastingPacket) object;
            // Generate randoms //
            request.setCritical(request.getSpell().getRandoms(request.getCaster()));
            // Act on server //
            //request.getSpell().use(request);

            suwardServer.getServer().sendToAllTCP(request);
        }

        if(object instanceof StartBattleStatePacket) {
            StartBattleStatePacket response;
            if(suwardServer.getMapName() != null) {
                response = new StartBattleStatePacket(suwardServer.getMapName());
            } else {
                response = new StartBattleStatePacket("max");
            }

            suwardServer.getServer().sendToAllTCP(response);
        }

        if(object instanceof MapNamePacket) {
            MapNamePacket request = (MapNamePacket) object;
            suwardServer.setMapName(request.getName());
        }

        if(object instanceof ChangePosHubPacket) {
            ChangePosHubPacket request = (ChangePosHubPacket) object;
            request.setId(connection.getID());
            suwardServer.getServer().sendToAllTCP(request);
        }

        if(object instanceof EndBattlePacket) {
            gm.endBattle();
        }

        if(object instanceof BoostPacket) {
            suwardServer.getServer().sendToAllTCP(object);
        }
    }

}

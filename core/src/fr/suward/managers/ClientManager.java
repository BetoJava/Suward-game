package fr.suward.managers;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import fr.suward.assets.FontManager;
import fr.suward.display.components.SpellButton;
import fr.suward.display.popups.DamageDisplay;
import fr.suward.display.popups.PreviewPopup;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.map.Map;
import fr.suward.game.map.MapIso;
import fr.suward.display.states.stages.BattleState;
import fr.suward.game.pathfinding.Path;
import fr.suward.network.packets.*;
import fr.suward.network.accounts.Account;
import fr.suward.network.receivers.ClientReceiver;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class ClientManager {

    private static ClientManager clientManager = new ClientManager();

    public static ClientManager get() {
        return clientManager;
    }

    private GameManager gameManager;

    private Account account;

    private Client client;

    public boolean godMod = false;

    private Spell spellLock;
    private SpellButton overSpellButton;
    private ArrayList<DamageDisplay> damageToRender = new ArrayList<>();
    private ArrayList<PreviewPopup> previewPopupList = new ArrayList<>();

    public ClientManager() {
        createClient();
    }


    public void createClient() {
        gameManager = new GameManager();
        client = new Client(100000,100000);
        PacketManager.registerPackets(client.getKryo());
        client.start();
        client.addListener(new ClientReceiver(gameManager));
        client.addListener(new Listener() {
            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                System.out.println(connection.getID() + " s'est déconnecté.");
                System.out.println(connection.getEndPoint());
                try {
                    client.connect(3000,gameManager.getIP(), connection.getRemoteAddressTCP().getPort());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("La reconnexion a échouée...");
                }
            }
        });
    }

    public void newGame(String mapName) {
        gameManager.getMapIso().getMap().loadMap(mapName);
        connectClient();

        StageManager.get().nextStage(new BattleState());
    }

    public void connectClient() {
        try {
            client.connect(3000, gameManager.getIP(), 6845);
            account.getCharacters().get(account.getCharacters().size() - 1).setId(client.getID());
            ConnectionPacket request = new ConnectionPacket(account.getCharacters().get(account.getCharacters().size() - 1).getEntityPacket());
            client.sendTCP(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connectClient(String IP) throws IOException {
        client.connect(3000, IP, 6845);
        account.getCharacters().get(account.getCharacters().size() - 1).setId(client.getID());
        ConnectionPacket request = new ConnectionPacket(account.getCharacters().get(account.getCharacters().size() - 1).getEntityPacket());
        client.sendTCP(request);
    }

    public void sendInitialPosPacket() {
        Entity e = getCurrentPlayer();
        int id = getCurrentPlayer().getId();
        client.sendTCP(new InitialPosPacket(id, e.getPosition().x, e.getPosition().y, e.isReady(), e.getTeam()));
    }

    public void sendPlayerMovementPacket(Path path) {
        int id = getCurrentClientCharacterOrItsSummon().getId();
        client.sendTCP(new PlayerMovementPacket(id, path));
    }

    public void moveCharacter(Point mouseIsoLoc) {
        if(!gameManager.isBattleStarted()) {
            getCurrentClientCharacterOrItsSummon().setPosition(mouseIsoLoc);
            sendInitialPosPacket();
        }
    }

    public void moveCharacter(Point mouseIsoLoc, Path path) {
        sendPlayerMovementPacket(path);
    }

    public void sendMessageInChat(String text, Color color) {
        BattleState state = (BattleState) StageManager.get().getStage();
        state.getChatInterface().getQueueLocalMessages().addLast(new LocalMessagePacket(text, color));
    }

    public void writeMessageInChat(String text, Color color) {
        BattleState state = (BattleState) StageManager.get().getStage();
        state.getChatInterface().getQueueLocalMessages().addLast(new LocalMessagePacket(text, color));
    }

    public void sendMessageInChat(String text) {
        // For debug
        sendMessageInChat(text, FontManager.lightBlue);
    }


    // ----------- Getters and Setters ------------------------- //

    public Map getMap() {
        return gameManager.getMapIso().getMap();
    }

    public MapIso getMapIso() {
        return gameManager.getMapIso();
    }

    public void setMap(Map map) {
        gameManager.getMapIso().setMap(map);
    }

    public void setMap(String mapName) {
        gameManager.getMapIso().getMap().loadMap(mapName);
    }

    public ArrayList<Entity> getAliveEntities() {
        return gameManager.getAliveEntities();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Get the entity instance of the current turn character (can be a summon)
     * @return
     */
    public Entity getCurrentTurnCharacter() {
        for(Entity e : getAliveEntities()) {
            if(e.isItsTurn()) {
                return e;
            }
        }
        return getCurrentPlayer();
    }

    /**
     * Get the entity instance of the current player (not summon)
     * @return
     */
    public Entity getCurrentPlayer() {
        return gameManager.getEntityFromID(client.getID());
    }

    public Entity getCurrentClientCharacterOrItsSummon() {
        for(Entity e : getAliveEntities()) {
            if(e.isItsTurn() && e.getSummoner() != null) {
                if(e.getSummoner().getId() == client.getID()) {
                    return e;
                }
            }
        }
        Entity e = getCurrentPlayer();
        if(e != null) {
            return e;
        } else {
            return account.getCharacters().get(0);
        }
    }

    public boolean isCurrentCharacterTurn() {
        Entity currentTurnEntity = gameManager.getTimeline().get(0);
        // If it is an summon of the current Character // -> Change here if I want that summon can summon by adding a for loop
        boolean b = false;
        if(currentTurnEntity.getSummoner() != null) {
            b = client.getID() == currentTurnEntity.getSummoner().getId();
        }
        return client.getID() == currentTurnEntity.getId() || b;
    }

    public Spell getSpellLock() {
        return spellLock;
    }

    public void setSpellLock(Spell spellLock) {
        for(Entity e : gameManager.getAliveEntities()) {
            e.setPreviewPopup(null);
        }
        this.spellLock = spellLock;
    }

    public ArrayList<DamageDisplay> getDamageToRender() {
        return damageToRender;
    }

    public SpellButton getOverSpellButton() {
        return overSpellButton;
    }

    public void setOverSpellButton(SpellButton overSpellButton) {
        this.overSpellButton = overSpellButton;
    }

    public ArrayList<PreviewPopup> getPreviewPopupList() {
        return previewPopupList;
    }

    public Account getAccount() {
        return account;
    }
}

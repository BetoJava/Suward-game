package fr.suward.managers;

import com.badlogic.gdx.utils.Queue;
import fr.suward.Constants;
import fr.suward.display.states.stages.BattleState;
import fr.suward.display.states.stages.WorldState;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.map.MapIso;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.*;

import java.util.ArrayList;

public class GameManager {

    private String IP = "192.168.1.12";

    private ArrayList<Integer> entitiesID = new ArrayList<>();
    private ArrayList<Entity> aliveEntities = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> team1Entities = new ArrayList<>();
    private ArrayList<Entity> team2Entities = new ArrayList<>();

    private ArrayList<Entity> timeline;
    private MapIso mapIso;

    private int turn = 0;

    private Queue<Integer> idQueue = new Queue<Integer>();

    private boolean battleStarted = false;
    private boolean battleEnded = false;

    public GameManager() {
        mapIso = new MapIso();
    }

    public GameManager(String mapName) {
        mapIso = new MapIso(mapName);
    }

    /**
     * Si le pseudo n'est pas déjà présent :
     * - ajout de l'entité dans la liste des entités
     * - ajout du pseudo dans la liste des pseudos d'entités
     * - ajout de l'entité dans la liste de sa team
     * - définit sa team
     * - définit sa position
     */
    public void addEntity(Entity e) {
        if(!entitiesID.contains(e.getId())) {
            aliveEntities.add(e);
            entities.add(e);
            entitiesID.add(e.getId());

            // Set entity team //
            if(aliveEntities.size() %2 == 0) {
                team1Entities.add(e);
                e.setTeam(1);
            } else {
                team2Entities.add(e);
                e.setTeam(2);
            }

        } else {
            Entity entity = getEntityFromID(e.getId());
            entity.setPosition(e.getPosition());
            entity.setTeam(e.getTeam());
            entity.setPseudo(e.getPseudo());
        }
    }


    public void createNewEntity(Entity entity) {
        // Create ID //
        int newID = 0;
        for(Entity e : entities) {
            if(e.getId() > newID) {
                newID = e.getId();
            }
        }
        newID += 1;
        entitiesID.add(newID);
        entity.setId(newID);

        aliveEntities.add(entity);
        entities.add(entity);
        timeline.add(1,entity);

    }

    /**
     * Met à jour la map et la liste des entités suite à une nouvelle connexion sur le Serveur.
     */
    public void update(ConnectionPacket request) {
        for(EntityPacket ep : request.getEntityPackets()) {
            Entity e = new Character(ep);
            e.setReady(false);
            addEntity(e);
        }
    }


    public void update(StartBattleStatePacket request) {
        mapIso.getMap().loadMap(request.getMapName());
        int i = 0;
        for(Entity e : ClientManager.get().getAliveEntities()) {

            if(i % 2 == 0) {
                team1Entities.add(e);
                e.setTeam(1);
                e.setPosition(mapIso.getMap().getBluePos().get(team1Entities.size() - 1));
            } else {
                team2Entities.add(e);
                e.setTeam(2);
                e.setPosition(mapIso.getMap().getRedPos().get(team2Entities.size() - 1));
            }
            i++;
        }
        WorldState worldState = (WorldState) StageManager.get().getStage();
        worldState.setBattleLaunched(true);
    }

    /**
     * Met à jour la position initial du joueur.
     */
    public void update(InitialPosPacket request) {
        Entity e = getEntityFromID(request.getEntityID());
        e.setPosition(request.getX(), request.getY());
        e.setReady(request.isReady());
        e.setTeam(request.getTeam());
    }

    public void update(PassTurnPacket request) {
        nextTurn();
    }


    public void update(ChangePosHubPacket request) {
        getEntityFromID(request.getId()).setPosition(request.getX(), request.getY());
    }

    public void updateServer(PassTurnPacket request) {
        timeline.get(0).endTurn(true);
        iterateTimeLine();
        timeline.get(0).turn(true);
    }

    public void update(PlayerMovementPacket request) {
        Entity entity = getEntityFromID(request.getEntityID());
        entity.moveCharacter(request.getPath());
    }

    public void updateWhenEntityKilled() {
        int team1Count = 0;
        int team2Count = 0;
        for(Entity e : aliveEntities) {
            if(e.getTeam() == 1) {
                team1Count ++;
            } else {
                team2Count ++;
            }
        }
        if(team1Count == 0 || team2Count == 0) {
            endBattle();
        }
    }

    public boolean isEveryOneReady() {
        for(Entity e : aliveEntities) {
            if(!e.isReady()) {
                return false;
            }
        }
        return true;
    }

    public void startBattle(ArrayList<Integer> timeline) {
        for(Entity e : entities) {
            e.getStats().reset();
        }
        battleEnded = false;
        battleStarted = true;
        setTimeline(timeline);

        this.timeline.get(0).turn();

        BattleState state = (BattleState) StageManager.get().getStage();
        state.getRightInterface().getButton().setText("Passer");
        state.newTimeLineInterface();

        if(isCurrentCharacterTurn()) {
            state.setLeftTurnTime(this.timeline.get(0).getTurnDuration());
        } else {
            state.setLeftTurnTime(-10);
        }
    }

    public void endBattle() {
        ClientManager.get().getClient().sendTCP(new EndBattlePacket());
        ArrayList<Entity> entitiesToRemove = new ArrayList<>();
        for(Entity e : entities) {
            e.setReady(false);
            e.setItsTurn(false);
            e.getStats().reset();
            if(!aliveEntities.contains(e)) {
                aliveEntities.add(e);
            }
            if(e.getSummoner() != null) {
                entitiesToRemove.add(e);
            }
        }
        for(Entity e : entitiesToRemove) {
            entitiesID.remove((Object)(e.getId()));
            if(aliveEntities.contains(e)) aliveEntities.remove(e);
            if(entities.contains(e)) entities.remove(e);
            e.getSummoner().getSummons().remove(e);
            if(team1Entities.contains(e)) team1Entities.remove(e);
            if(team2Entities.contains(e)) team2Entities.remove(e);
        }
        team1Entities.clear();
        team2Entities.clear();
        ClientManager.get().getDamageToRender().clear();
        battleStarted = false;
        battleEnded = true;
    }

    public void passTurn() {
        if(!timeline.isEmpty()) {
            ClientManager.get().getClient().sendTCP(new PassTurnPacket());
        }
    }

    public void nextTurn() {
        timeline.get(0).endTurn();
        iterateTimeLine();
        BattleState state = (BattleState) StageManager.get().getStage();
        state.newTimeLineInterface();
        timeline.get(0).turn();

        if(isCurrentCharacterTurn()) {
            state.setLeftTurnTime(timeline.get(0).getTurnDuration());
        } else {
            state.setLeftTurnTime(-10);
        }
    }

    private void iterateTimeLine() {
        ArrayList<Entity> newTimeLine = new ArrayList<>();
        for(int i = 1; i < timeline.size(); i++) {
            newTimeLine.add(timeline.get(i));
        }
        newTimeLine.add(timeline.get(0));
        timeline = newTimeLine;
    }





    // _________ Getters and Setters _________________________ //

    public boolean isBattleStarted() {
        return battleStarted;
    }

    public boolean isBattleEnded() {
        return battleEnded;
    }

    public void setBattleStarted(boolean battleStarted) {
        this.battleStarted = battleStarted;
    }

    public void setBattleEnded(boolean battleEnded) {
        this.battleEnded = battleEnded;
    }

    public ArrayList<Entity> getAliveEntities() {
        return aliveEntities;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<EntityPacket> getEntityPackets() {
        ArrayList<EntityPacket> ep = new ArrayList<>();
        for(Entity e : aliveEntities) {
            ep.add(e.getEntityPacket());
        }
        return ep;
    }

    public String getIP() {
        return IP;
    }


    public MapIso getMapIso() {
        return mapIso;
    }

    public ArrayList<Entity> getTeam1Entities() {
        return team1Entities;
    }

    public ArrayList<Entity> getTeam2Entities() {
        return team2Entities;
    }

    public int getTurn() {
        return turn;
    }



    public Entity getEntityFromID(int id) {
        if(entitiesID.contains(id)) {
            for(Entity e : entities) {
                if(e.getId() == id) {
                    return e;
                }
            }
        }
        return null;
    }

    public void setTeam(int team, Entity entity) {
        if(entity.getTeam() == 1) {
            team1Entities.remove(entity);
        } else {
            team2Entities.remove(entity);
        }

        entity.setTeam(team);

        if(entity.getTeam() == 1) {
            team1Entities.add(entity);
        } else {
            team2Entities.add(entity);
        }
    }

    public ArrayList<Entity> getTimeline() {
        return timeline;
    }

    public boolean isCurrentCharacterTurn() {
        return ClientManager.get().isCurrentCharacterTurn();
    }

    public void setTimeline(ArrayList<Integer> timeline) {
        this.timeline = new ArrayList<>();
        for(int i : timeline) {
            this.timeline.add(getEntityFromID(i));
        }
    }


    public void update(BoostPacket request) {
        Effect effect = new Effect("cheat",100000);
        effect.add(new Boost(request.getName(), request.getValue(), 100000));
        effect.addEffectToTarget(getEntityFromID(request.getTargetID()), getEntityFromID(request.getTargetID()),"");
    }
}

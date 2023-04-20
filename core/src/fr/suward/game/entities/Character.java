package fr.suward.game.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.suward.display.states.stages.BattleState;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Poison;
import fr.suward.game.entities.spells.classes.dolma.TraverseeTemporelle;
import fr.suward.game.entities.spells.pdcity.pasdoeuf.AutoPilotage;
import fr.suward.managers.GameManager;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.pathfinding.Path;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.EntityPacket;

import java.util.ArrayList;

public class Character extends Entity {

    @JsonProperty("exp")
    private int exp = 0;
    @JsonProperty("power")
    private int power = 0;
    @JsonProperty("statPoints")
    private int statPoints = 0;

    public Character(String pseudo, ClassData classData) {
        super(pseudo, classData);
        this.classData.setCharacter(this);
    }

    public Character(EntityPacket entityPacket) {
        super(entityPacket);
        exp = entityPacket.getExp();
        power = entityPacket.getPower();
        statPoints = entityPacket.getStatPoints();
        classData.setCharacter(this);
    }

    public Character() {

    }

    public void die() {
        GameManager gm = ClientManager.get().getGameManager();
        gm.getAliveEntities().remove(this);

        int index = gm.getTimeline().indexOf(this);
        if(index >= 0) {
            gm.getTimeline().remove(index);
        }
        if(index == 0) {
            gm.passTurn();
        }
        gm.updateWhenEntityKilled();
    }

    @Override
    public void moveCharacter(Path path) {
        stats.add("pd", - path.cost);
        for(int i = 0; i < path.size(); i++) {
            position = path.get(path.size() - 1 - i);
        }
    }


    @Override
    public void turn() {
        turn(false);
    }

    @Override
    public void turn(boolean isServer) {
        setItsTurn(true);
        ClientManager.get().setSpellLock(null);
        beginningTurnPosition.x = position.x;
        beginningTurnPosition.y = position.y;
        stats.update();
        for(Spell s : classData.getSpells()) {
            s.update();
        }

        if(!isServer) classData.beginningOfTurn(this);

        for(Effect e : stats.getEffects()) {
            for(Poison p : e.getPoisons()) {
                p.act(this);
            }
        }

        if(!isServer && !isStatic) {
            BattleState state = (BattleState) StageManager.get().getStage();
            state.getSpellInterface().clearSpells();
            if(ClientManager.get().isCurrentCharacterTurn()) {
                state.getSpellInterface().addSpellsButtons(ClientManager.get().getCurrentClientCharacterOrItsSummon().getClassData().getSpells());
            } else {
                state.getSpellInterface().addSpellsButtons(ClientManager.get().getCurrentPlayer().getClassData().getSpells());
            }
        }
        Spell.updateShadow();

    }

    @Override
    public void endTurn() {
        endTurn(false);
    }

    @Override
    public void endTurn(boolean isServer) {
        setItsTurn(false);
        if(!isServer) {
            classData.endingOfTurn(this);
            endTurnEffect(this);
        }
        stats.getStat("pm").reset();
        stats.getStat("pd").reset();
        stats.add("pc", 1);
        if(!isServer) {
            Spell.updateShadow();
            // add : if isNowInvocTurn //
            BattleState state = (BattleState) StageManager.get().getStage();
            state.getSpellInterface().clearSpells();
        }
    }

    private void endTurnEffect(Character character) {
        if(character.getStats().hasEffectWithName(TraverseeTemporelle.effectName)) {
            Spell.teleport(character, character.getBeginningTurnPosition());
        }
    }

    public EntityPacket getEntityPacket() {
        return new EntityPacket(classID,pseudo,niv,id,stats,team,iniOrder,turn,isReady,position,isInvocation,isStatic,exp,power,statPoints);
    }

    public ArrayList<Character> getSummons() {
        return summons;
    }
}

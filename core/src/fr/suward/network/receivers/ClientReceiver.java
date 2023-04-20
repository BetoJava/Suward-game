package fr.suward.network.receivers;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import fr.suward.display.states.stages.BattleState;
import fr.suward.display.states.stages.WorldState;
import fr.suward.game.entities.spells.PoisonSpell;
import fr.suward.managers.ClientManager;
import fr.suward.managers.GameManager;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.*;



public class ClientReceiver extends Listener {

    private GameManager gm;

    public ClientReceiver(GameManager gameManager) {
        gm = gameManager;
    }


    public void received (Connection connection, Object object) {

        /**
         * Le Client reçoit ce packet lors d'une connextion d'un nouveau Client.
         * Le Serveur envoie ce packet avec le nom de la map et la nouvelle liste de toutes les entités.
         */
        if(object instanceof ConnectionPacket) {
            ConnectionPacket request = (ConnectionPacket) object;
            gm.update(request);
        }

        if(object instanceof ChatMessagePacket) {
            ChatMessagePacket request = (ChatMessagePacket) object;
            if(StageManager.get().getStage() instanceof BattleState) {
                BattleState battleState = (BattleState) StageManager.get().getStage();
                battleState.getChatInterface().getMessagesToReceive().addFirst(request);
            } else if(StageManager.get().getStage() instanceof WorldState) {
                WorldState worldState = (WorldState) StageManager.get().getStage();
                worldState.getChatInterface().getMessagesToReceive().addFirst(request);
            }
        }

        if(object instanceof InitialPosPacket) {
            InitialPosPacket request = (InitialPosPacket) object;
            gm.update(request);
        }

        if(object instanceof StartingBattlePacket) {
            StartingBattlePacket request = (StartingBattlePacket) object;

            gm.startBattle(request.getTimeline());
        }

        if(object instanceof PassTurnPacket) {
            PassTurnPacket request = (PassTurnPacket) object;
            gm.update(request);
        }

        if(object instanceof PlayerMovementPacket) {
            PlayerMovementPacket request = (PlayerMovementPacket) object;
            gm.update(request);
        }

        if(object instanceof SpellCastingPacket) {
            SpellCastingPacket request = (SpellCastingPacket) object;
            //request.getSpell().use(request); pas utile, le Spell du packet n'est pas la même instance que celui du joueur

            request.getCaster().updateSpell(request);
            if(request.getSpell() instanceof PoisonSpell) {
                request.getSpell().use(request);
            } else {
                request.getCaster().getClassData().getSpells().get(request.getSpell().getIndex()).use(request);
            }
        }

        if(object instanceof StartBattleStatePacket) {
            StartBattleStatePacket request = (StartBattleStatePacket) object;
            ClientManager.get().setMap(request.getMapName());
            gm.update(request);
        }

        if(object instanceof ChangePosHubPacket) {
            ChangePosHubPacket request = (ChangePosHubPacket) object;
            gm.update(request);
        }

        if(object instanceof BoostPacket) {
            BoostPacket request = (BoostPacket) object;
            gm.update(request);
        }

    }

}

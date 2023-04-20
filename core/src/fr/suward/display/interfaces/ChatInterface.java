package fr.suward.display.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import fr.suward.assets.Assets;
import fr.suward.assets.FontManager;
import fr.suward.display.states.stages.BattleState;
import fr.suward.game.entities.Entity;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.BoostPacket;
import fr.suward.network.packets.ChatMessagePacket;
import fr.suward.network.packets.LocalMessagePacket;
import fr.suward.network.packets.MapNamePacket;


public class ChatInterface extends Table {

    private TextField textField;
    private Table textTable;
    private ScrollPane scrollPane;

    private Queue<ChatMessagePacket> messagesToReceive = new Queue<>();
    private Queue<LocalMessagePacket> queueLocalMessages = new Queue<>();

    public ChatInterface(Table table) {
        Skin skin = Assets.getAssetManager().get(Assets.SKIN_COMPOSER);
        textTable = new Table();
        textTable.align(Align.topLeft);
        textField = new TextField("",skin);

        scrollPane = new ScrollPane(textTable, skin);

        this.setBackground(new TextureRegionDrawable(Assets.TEXTURES.get("chatInterface")));

        scrollPane.setScrollbarsVisible(true);
        scrollPane.setFadeScrollBars(false);

        scrollPane.addListener(new InputListener() {

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                scrollPane.scaleBy(amountY);
                return super.scrolled(event, x, y, amountX, amountY);
            }
        });

        this.setWidth(790);
        this.setHeight(200);
        table.add(this);

        this.add(scrollPane).height(167).width(788);
        this.row();
        this.add(textField).height(30).padTop(1).width(780);
    }

    public void keyDown(int keycode) {
        if(textField.hasKeyboardFocus()) {
            if(keycode == 66) {
                sendMessage();
            }
        }

    }

    public void sendMessage() {
        String text = textField.getText();
        if(text.length() <= 0) return;
        if(text.charAt(0) != '/') {
            ClientManager.get().getClient().sendTCP(new ChatMessagePacket(ClientManager.get().getCurrentPlayer().getPseudo(), textField.getText()));
            receiveMessage(ClientManager.get().getCurrentPlayer().getPseudo(), textField.getText());
        } else {
            if(text.startsWith("/map")) {
                String mapName = text.substring(5);
                ClientManager.get().getClient().sendTCP(new MapNamePacket(mapName));
                receiveMessage("Map has been changed with : " + mapName, Color.BLUE);
            }
            if(text.startsWith("/gd")) {
                if(ClientManager.get().godMod) {
                    ClientManager.get().godMod = false;
                    receiveMessage("God mode deactivated.", Color.BLUE);
                } else {
                    ClientManager.get().godMod = true;
                    receiveMessage("God mode activated.", Color.BLUE);
                }
            }
            if(text.startsWith("/give")) {
                String subText = text.substring(5);
                String[] v = subText.split(",");
                try {
                    int value = Integer.parseInt(v[2]);
                    int targetID = -1;
                    for(Entity entity: ClientManager.get().getAliveEntities()) {
                        if(entity.getPseudo() == v[1]) targetID = entity.getId();
                    }
                    ClientManager.get().getClient().sendTCP(new BoostPacket(targetID, v[0], value));
                    receiveMessage(subText, Color.BLUE);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        textField.setText("");
    }

    public void receiveMessage(String text, Color color) {
        boolean needToScroll = false;
        if(scrollPane.getScrollPercentY() == 1f) {
            needToScroll = true;
        }
        Label l = new Label(text, new Label.LabelStyle(FontManager.robotoFont(12),color));
        textTable.add(l).padLeft(5).align(Align.left).row();

        if(needToScroll) {
            scrollPane.scrollTo(0,0,0,0);
        }
    }

    public void receiveMessage(String senderPseudo, String message) {
        receiveMessage(senderPseudo + " : " + message, Color.WHITE);

    }

    public void receiveSentMessage() {
        while(!messagesToReceive.isEmpty()) {
            ChatMessagePacket p = messagesToReceive.removeFirst();
            receiveMessage(p.getSenderPseudo(), p.getMessage());
        }

        while(!queueLocalMessages.isEmpty()) {
            LocalMessagePacket p = queueLocalMessages.removeFirst();
            receiveMessage(p.getMessage(), p.getColor());
        }
    }



    public Queue<ChatMessagePacket> getMessagesToReceive() {
        return messagesToReceive;
    }

    public Queue<LocalMessagePacket> getQueueLocalMessages() {
        return queueLocalMessages;
    }

}

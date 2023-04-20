package fr.suward.network.packets;

public class ChatMessagePacket {

    private String senderPseudo;
    private String message;


    public ChatMessagePacket() {

    }

    public ChatMessagePacket(String senderPseudo, String message) {
        this.senderPseudo = senderPseudo;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderPseudo() {
        return senderPseudo;
    }
}

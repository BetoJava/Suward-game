package fr.suward.network.packets;

import com.badlogic.gdx.graphics.Color;

public class LocalMessagePacket {

    private String message;
    private Color color;

    public LocalMessagePacket() {
    }

    public LocalMessagePacket(String message, Color color) {
        this.message = message;
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public Color getColor() {
        return color;
    }
}

package fr.suward.game;

import java.util.ArrayList;

public class Credits {

    public ArrayList<String> credits = new ArrayList<>();

    public Credits() {
        add("Fira Font");
        add("Roboto Font");
        add("LibGDX");
        add("Jackson");
        add("Kryonet");
        add("https://game-icons.net/");
    }

    private void add(String txt) {
        credits.add(txt);
    }
}

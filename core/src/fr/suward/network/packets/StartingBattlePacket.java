package fr.suward.network.packets;

import java.util.ArrayList;

public class StartingBattlePacket {

    private ArrayList<Integer> timeline;


    public StartingBattlePacket() {

    }

    public StartingBattlePacket(ArrayList<Integer> timeline) {
        this.timeline = timeline;
    }


    public ArrayList<Integer> getTimeline() {
        return timeline;
    }
}

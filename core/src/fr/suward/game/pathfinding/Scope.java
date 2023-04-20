package fr.suward.game.pathfinding;

import java.awt.*;
import java.util.ArrayList;

public class Scope {

    private ArrayList<Point> seenRange = new ArrayList<>();
    private ArrayList<Point> unseenRange = new ArrayList<>();

    public Scope() {

    }

    public Scope(ArrayList<Point> seenRange, ArrayList<Point> unseenRange) {
        this.seenRange = seenRange;
        this.unseenRange = unseenRange;
    }

    public ArrayList<Point> getSeenRange() {
        return seenRange;
    }

    public ArrayList<Point> getUnseenRange() {
        return unseenRange;
    }

    public void addSeen(int x, int y) {
        seenRange.add(new Point(x, y));
    }

    public void addUnseen(int x, int y) {
        unseenRange.add(new Point(x, y));
    }
}

package fr.suward.game.pathfinding;

import java.awt.*;
import java.util.ArrayList;

/**
 * Path class, ordered from arrival tile to first tile to move.
 */
public class Path {

    private ArrayList<Point> positions = new ArrayList<>();
    public int cost = 0;

    public Path(int cost) {
        this.cost = cost;
    }

    public Path(ArrayList<Point> positions, int cost) {
        this.cost = cost;
        this.positions = positions;
    }

    public Path() {

    }

    public void add(Point p) {
        positions.add(p);
    }

    public int size() {
        return positions.size();
    }

    public Point get(int index) {
        return positions.get(index);
    }

    public void removeLast() {
        positions.remove(positions.size()-1);
    }

    public boolean contains(int x, int y) {
        return positions.contains(new Point(x,y));
    }

    public boolean contains(Point p) {
        return positions.contains(p);
    }

    public String toString() {
        String str = "";
        for(Point p : positions) {
            str += "(" + Integer.valueOf(p.x) + ", " + Integer.valueOf(p.y) + "), ";
        }
        return str;
    }


}

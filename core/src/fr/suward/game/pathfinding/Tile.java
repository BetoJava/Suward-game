package fr.suward.game.pathfinding;

import java.awt.*;
import java.util.ArrayList;

public class Tile {

    public int x;
    public int y;
    private Tile parent;

    public double H;
    public double G;
    public double T;
    public double F;

    public Tile(int x, int y, Tile parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public Tile(Point p, Tile parent) {
        x = p.x;
        y = p.y;
        this.parent = parent;
    }

    public void update(Point end) {
        G = 10 + parent.G;
        T += parent.T; // Sum of tackle on the path to this Tile //
        H = Math.abs(end.x - x) + Math.abs(end.y - y);
        F = H + G + T;
    }

    public static Tile min(ArrayList<Tile> tiles) {
        Tile mini = tiles.get(0);
        for(int i = 1; i < tiles.size(); i++) {
            if(tiles.get(i).F < mini.F) {
                mini = tiles.get(i);
            }
        }
        return mini;
    }

    public Point getPoint() {
        return new Point(x,y);
    }

    public Tile getParent() {
        return parent;
    }
}

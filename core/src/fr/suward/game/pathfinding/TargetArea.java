package fr.suward.game.pathfinding;

import fr.suward.game.entities.Entity;

import java.awt.*;
import java.util.ArrayList;

public class TargetArea {

    private ArrayList<Point> areaPos = new ArrayList<>();
    private ArrayList<Entity> entitiesInArea = new ArrayList<>();

    public TargetArea() {

    }

    public ArrayList<Entity> getEntitiesInArea() {
        return entitiesInArea;
    }

    public ArrayList<Point> getAreaPos() {
        return areaPos;
    }
}

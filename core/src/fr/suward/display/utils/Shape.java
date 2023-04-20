package fr.suward.display.utils;

import java.awt.*;


public class Shape {

    public int x;
    public int y;
    public int width;
    public int height;

    public float rotation = 0f;

    public Shape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public boolean isIn(Point p)
    {
        if(p.getX() >= x && p.getX() <= x+width && p.getY() >= y && p.getY() <= y+height)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setPoint(Point p) {
        x = p.x;
        y = p.y;
    }

}

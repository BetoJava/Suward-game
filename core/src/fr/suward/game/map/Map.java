package fr.suward.game.map;

import com.badlogic.gdx.Gdx;
import fr.suward.Constants;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Map {

    public static final int ABSOLUTE_VOID = -2;
    public static final int VOID = -1;
    public static final int GROUND = 0;
    public static final int WALL = 1;
    public static final int BLUE_POS = 2;
    public static final int RED_POS = 3;

    private int[][] data;
    private ArrayList<Point> bluePos = new ArrayList<>();
    private ArrayList<Point> redPos = new ArrayList<>();
    private String mapName;

    public Map() {

    }

    public Map(String mapName) {
        this.mapName = mapName;
        loadMap(mapName);
    }


    public void loadMap(String name) {
        mapName = name;

        data = new int[35][37];

        FileReader reader = null;
        try {
            reader = new FileReader(Gdx.files.internal(System.getProperty("user.dir") + "/map/" + name + ".txt").file());
        } catch (FileNotFoundException e) {

            try {
                reader = new FileReader(Gdx.files.internal("core/assets/map/" + name + ".txt").file());
            } catch (FileNotFoundException ex) {

            }
        }

        try {

            BufferedReader br = new BufferedReader(reader);
            String line;
            for(int i = 0; (line = br.readLine()) != null; i++) {
                String[] c = line.split(",");
                for(int j = 0; j < c.length; j++) {
                    data[i][j] = Integer.valueOf(c[j]);

                    if(data[i][j] == BLUE_POS) {
                        bluePos.add(new Point(i,j));
                    } else if (data[i][j] == RED_POS) {
                        redPos.add(new Point(i, j));
                    }
                }
            }
            br.close();
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File "+name+".txt not found...");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void set(int i, int j, int val) {
        data[i][j] = val;
    }

    public int get(int i, int j) {
        return data[i][j];
    }

    public int get(Point point) {
        return data[point.x][point.y];
    }

    public int getWidth() {
        return data.length;
    }

    public int getHeight() {
        return data[0].length;
    }

    public int[][] getData() {
        return data;
    }

    public boolean isWall(int i, int j) {
        return get(i,j) == WALL;
    }

    public boolean isVoid(int i, int j) {
        return get(i,j) == VOID ||get(i,j) == ABSOLUTE_VOID;
    }

    public boolean isGround(int i, int j) {
        return get(i,j) == GROUND || get(i,j) == RED_POS ||get(i,j) == BLUE_POS;
    }

    public String getMapName() {
        return mapName;
    }

    public ArrayList<Point> getBluePos() {
        return bluePos;
    }

    public ArrayList<Point> getRedPos() {
        return redPos;
    }

    public boolean isMapDataNull() {
        return data == null;
    }
}

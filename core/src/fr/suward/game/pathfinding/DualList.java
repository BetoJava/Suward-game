package fr.suward.game.pathfinding;

import java.util.ArrayList;

public class DualList<T> {

    private ArrayList<T> list1 = new ArrayList<>();
    private ArrayList<T> list2 = new ArrayList<>();

    public DualList() {

    }

    public DualList(ArrayList<T> list1, ArrayList<T> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    public ArrayList<T> getList1() {
        return list1;
    }

    public ArrayList<T> getList2() {
        return list2;
    }
}

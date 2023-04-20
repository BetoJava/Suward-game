package fr.suward.game.entities.stats;


public class Stat {

    protected String name;
    protected String nickname;
    protected int initialMax;
    protected int max;
    protected int actual;
    protected int boost;    // comprend les points de carac //

    public Stat(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    public Stat() {

    }

    public void init() {
        max = 0;
        initialMax = 0;
        actual = 0;
        boost = 0;
    }

    public void reset() {
        actual = initialMax;
        max = initialMax;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getInitialMax() {
        return initialMax;
    }

    public void setInitialMax(int initialMax) {
        this.initialMax = initialMax;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getBoost() {
        return boost;
    }

    public void setBoost(int boost) {
        this.boost = boost;
    }

    public void add(int value) {
        actual += value;
        max += value;
    }
}

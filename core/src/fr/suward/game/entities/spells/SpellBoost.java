package fr.suward.game.entities.spells;

public class SpellBoost {

    private int damage;
    private int duration;
    private String label;

    private int push = 0;
    private int range = 0;
    private int critic = 0;
    private int manaCost = 0;
    private int concentrationCost = 0;
    private int travelCost = 0;

    private int zoneSize = 0;

    public SpellBoost() {

    }

    public SpellBoost(int damage, int duration) {
        this.damage = damage;
        this.duration = duration;
    }

    public SpellBoost(int damage, int duration, String label) {
        this.damage = damage;
        this.duration = duration;
        this.label = label;
    }

    public boolean update() {
        this.duration -= 1;
        return duration <= 0;
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getPush() {
        return push;
    }

    public void setPush(int push) {
        this.push = push;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCritic() {
        return critic;
    }

    public void setCritic(int critic) {
        this.critic = critic;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getConcentrationCost() {
        return concentrationCost;
    }

    public void setConcentrationCost(int concentrationCost) {
        this.concentrationCost = concentrationCost;
    }

    public int getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(int travelCost) {
        this.travelCost = travelCost;
    }

    public int getZoneSize() {
        return zoneSize;
    }

    public void setZoneSize(int zoneSize) {
        this.zoneSize = zoneSize;
    }
}

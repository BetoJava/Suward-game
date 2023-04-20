package fr.suward.game.entities.spells;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.stats.Stats;

public class Boost {

    private String statName;
    private int value;
    private int duration;
    private int delay = -1;
    private String label;

    private String boostText = "";

    private float floatValue = 0f;
    private boolean isStatFromCaster;
    private String baseStatName;

    private int statusID = -1;

    public Boost() {

    }

    public Boost(int statusID, int turnRemaining) {
        this.statusID = statusID;
        this.duration = turnRemaining;
    }

    public Boost(int statusID, int turnRemaining, int delay) {
        this(statusID, turnRemaining);
        this.delay = delay;
    }

    public Boost(String statName, int value, int turnRemaining) {
        this.statName = statName;
        this.value = value;
        this.duration = turnRemaining;
        setText();
    }

    public Boost(String statName, int value, int turnRemaining, String label) {
        this(statName, value, turnRemaining);
        this.label = label;
    }

    /**
     * @param statName stat name of the boost
     * @param value amount value of the boost
     * @param turnRemaining number of turn of the boost life time. Every start of turn, it decreases by 1
     * @param delay delay in turn of the boost. Value is 0 when it is effective turn
     */
    public Boost(String statName, int value, int turnRemaining, int delay) {
        this(statName, value, turnRemaining);
        this.delay = delay;
    }

    public Boost(String statName, int value, int turnRemaining, int delay, String label) {
        this(statName, value, turnRemaining, label);
        this.delay = delay;
    }

    public Boost(String statName, float value, int turnRemaining, boolean isStatFromCaster, String baseStatName) {
        this.floatValue = value;
        this.isStatFromCaster = isStatFromCaster;
        this.baseStatName = baseStatName;
        this.statName = statName;
        this.duration = turnRemaining;
        setText(value, isStatFromCaster, baseStatName);
    }

    public Boost(String statName, float value, int turnRemaining, boolean isStatFromCaster, String baseStatName, String label) {
        this(statName, value, turnRemaining, isStatFromCaster, baseStatName);
        this.label = label;
    }

    public void setValue(Entity target, Entity caster) {
        if(isStatFromCaster) {
            value = (int) (floatValue * caster.getStats().get(baseStatName));
        } else {
            value = (int) (floatValue * target.getStats().get(baseStatName));
        }
    }

    public String getStatusText() {
        boostText = "";
        boostText += Effect.getStringStatus(statusID) + " (" + (duration - 1) + " tour";
        if(duration - 1 > 1) {
            boostText += "s";
        }
        boostText += ")";
        return boostText;
    }

    private void setText() {
        boostText += value + Stats.fullNameOf(statName).toLowerCase() + " (" + (duration - 1) + " tour";

        if(duration - 1 > 1) {
            boostText += "s";
        }

        boostText += ")";
    }

    private void setText(float value, boolean isStatFromCaster, String baseStatName) {

        int intValue = (int) (value * 100);
        boostText += intValue + "% " + Stats.fullNameOf(statName).toLowerCase();

        if(isStatFromCaster) {
            boostText += " du lanceur";
        } else {
            boostText += " de la cible";
        }

        boostText += " (" + (duration - 1) + " tour";
        if(duration - 1 > 1) {
            boostText += "s";
        }
        boostText += ")";
    }

    public boolean update() {
        if(delay <= 0) {
            duration -= 1;
        }
        delay -= 1;
        return duration <= 0;

    }

    public int getEffectiveValue() {
        if(duration > 0 && delay < 0) {
            return value;
        } else {
            return 0;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getDuration() {
        return duration;
    }

    public void addDuration(int amount) {
        duration += amount;
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

    public String getStatName() {
        return statName;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public String getBoostText() {
        return boostText;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public void setBoostText(String boostText) {
        this.boostText = boostText;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public void setStatFromCaster(boolean statFromCaster) {
        isStatFromCaster = statFromCaster;
    }

    public void setBaseStatName(String baseStatName) {
        this.baseStatName = baseStatName;
    }

    public boolean isStatFromCaster() {
        return isStatFromCaster;
    }

    public String getBaseStatName() {
        return baseStatName;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getStatusID() {
        return statusID;
    }

    @Override
    public String toString() {
        return "Boost{" +
                "statName='" + statName + '\'' +
                ", value=" + value +
                ", duration=" + duration +
                ", effectiveTurn=" + delay +
                ", statusID=" + statusID +
                '}';
    }
}

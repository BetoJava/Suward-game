package fr.suward.game.entities.spells;

public class DamageLine {

    public static final int WATER_ELEMENT = 1;
    public static final int FIRE_ELEMENT = 2;
    public static final int AIR_ELEMENT = 3;
    public static final int LIGHTNING_ELEMENT = 4;

    private int element;
    private int minDamage;
    private int maxDamage;
    private int baseDamage;
    private boolean isDrain = false;
    private boolean isHeal = false;
    private boolean isPoison = false;

    private boolean isNonPermanentLine = false;
    private int duration = 0;

    public DamageLine() {

    }

    public DamageLine(int minDamage, int maxDamage, int element) {
        init(element, minDamage, maxDamage);
    }

    public DamageLine(int minDamage, int maxDamage, int element, boolean isDrain, boolean isHeal) {
        this(minDamage, maxDamage, element, isDrain, isHeal, false);
    }

    public DamageLine(int minDamage, int maxDamage, int element, boolean isDrain, boolean isHeal, int duration) {
        this(minDamage, maxDamage, element, isDrain, isHeal);
        this.isNonPermanentLine = true;
        this.duration = duration;
    }

    public DamageLine(int minDamage, int maxDamage, int element, boolean isDrain, boolean isHeal, boolean isPoison) {
        init(element, minDamage, maxDamage);
        this.isDrain = isDrain;
        this.isHeal = isHeal;
        this.isPoison = isPoison;
    }

    public void init(int element, int minDamage, int maxDamage) {
        this.element = element;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    /**
     * Return if the damage line needs to be removed.
     * @return
     */
    public boolean update() {
        if(isNonPermanentLine) {
            duration -= 1;
            return duration <= 0;
        }
        return false;
    }


    // Getters and Setters //

    public void multiplyDamage(float multiplier) {
        baseDamage = (int)(baseDamage * multiplier);
        minDamage = (int)(minDamage * multiplier);
        maxDamage = (int)(maxDamage * multiplier);
    }

    public void multiply(float multi) {
        baseDamage = (int) (baseDamage*multi);
        minDamage = (int) (minDamage*multi);
        maxDamage = (int) (maxDamage*multi);
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public boolean isDrain() {
        return isDrain;
    }

    public void setDrain(boolean drain) {
        isDrain = drain;
    }

    public boolean isHeal() {
        return isHeal;
    }

    public void setHeal(boolean heal) {
        isHeal = heal;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void setNonPermanentLine(boolean nonPermanentLine) {
        isNonPermanentLine = nonPermanentLine;
    }

    public boolean isNonPermanentLine() {
        return isNonPermanentLine;
    }

    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
    }
}

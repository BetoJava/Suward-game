package fr.suward.game.entities.spells;

import fr.suward.game.entities.Entity;

import java.util.ArrayList;

public class Effect {

    // Status ID //
    public static final int UNMOVABLE = 0;
    public static final int INCURABLE = 1;
    public static final int ROOTED = 2;
    public static final int HEAVY = 3;
    public static final int INVULNERABLE = 4;
    public static final int DISTANCE_INVULNERABLE = 5;
    public static final int MELEE_INVULNERABLE = 6;
    public static final int PASSIVE = 7;
    public static final int GRAVITY = 8;
    public static final int UNPLAYABLE = 9; // pass the turn

    private String name;
    private int spellID = -1;

    private int effectDuration = 0;
    private ArrayList<Boost> boosts = new ArrayList<>();
    private ArrayList<Poison> poisons = new ArrayList<>();

    public Effect() {

    }

    public Effect(String name, int duration) {
        this.name = name;
        this.effectDuration = duration;
    }

    public Effect(String name, int duration, int spellID) {
        this(name, duration);
        this.spellID = spellID;
    }

    public boolean update() {
        ArrayList<Poison> poisonToRemove = new ArrayList<>();
        ArrayList<Boost> boostToRemove = new ArrayList<>();
        for(Poison p : poisons) if(p.update()) poisonToRemove.add(p);
        for(Boost b : boosts) if(b.update()) boostToRemove.add(b);
        for(Boost b : boostToRemove) boosts.remove(b);
        for(Poison p : poisonToRemove) poisons.remove(p);

        effectDuration -= 1;
        if(effectDuration <= 0) {
            actionWhenRemoved();
        }
        return effectDuration <= 0;
    }

    public void addEffectToTarget(Entity target, Entity caster, String text) {
        for (Boost b : boosts) {
            if(b.getFloatValue() != 0f) {
                b.setValue(target, caster);
            }
        }

        for (Poison p : poisons) {
            p.setCasterID(caster.getId());
        }

        Effect e = cloneThisEffect();

        if(caster.isItsTurn() && caster.getId() == target.getId()) {
            e.setEffectDuration(e.getEffectDuration() - 1);
            for(Boost b : e.getBoosts()) {
                if(b.getDelay() <= 0) b.setDuration(b.getDuration() - 1);
                b.setDelay(b.getDelay() - 1);
            }
            for(Poison p : e.getPoisons()) {
                if(p.getDelay() <= 0) p.setDuration(p.getDuration() - 1);
                p.setDelay(p.getDelay() - 1);
            }
        }
        target.addEffect(e, text);
    }

    private Effect cloneThisEffect() {
        Effect e = new Effect(name, effectDuration);
        for(Boost b : boosts) {
            Boost boost = new Boost();
            boost.setValue(b.getValue());
            boost.setStatName(b.getStatName());
            boost.setDuration(b.getDuration());
            boost.setLabel(b.getLabel());
            boost.setBoostText(b.getBoostText());
            boost.setFloatValue(b.getFloatValue());
            boost.setStatFromCaster(b.isStatFromCaster());
            boost.setBaseStatName(b.getBaseStatName());
            boost.setDelay(b.getDelay());
            boost.setStatusID(b.getStatusID());

            e.add(boost);
        }
        for(Poison p : poisons) {
            Poison poison = new Poison();
            poison.setDelay(p.getDelay());
            poison.setDuration(p.getDuration());
            // En faisant ça, l'attribut isPoison de la new dl est false //
            DamageLine dl = new DamageLine(p.getDamageLine().getMinDamage(), p.getDamageLine().getMaxDamage(), p.getDamageLine().getElement());
            poison.setDamageLine(dl);
            poison.setCasterID(p.getCasterID());

            e.add(poison);
        }
        return e;
    }

    public static String getStringStatus(int intStatus) {
        switch (intStatus) {
            case UNMOVABLE:
                return "Indéplaçable";
            case INCURABLE:
                return "Insoignable";
            case ROOTED:
                return "Enraciné";
            case HEAVY:
                return "Lourd";
            case INVULNERABLE:
                return "Invulnérable";
            case DISTANCE_INVULNERABLE:
                return "Invulnérable à distance";
            case MELEE_INVULNERABLE:
                return "Invulnérable en mélée";
            case PASSIVE:
                return "Passif";
            case UNPLAYABLE:
                return "Passe tour";
            case GRAVITY:
                return "Pesanteur";
            default:
                return "";
        }
    }

    public void add(Boost b) {
        boosts.add(b);
    }

    public void add(Poison p) {
        poisons.add(p);
    }


    public ArrayList<Boost> getBoosts() {
        return boosts;
    }

    public void action(){

    }

    public void actionWhenRemoved() {

    }

    public String getName() {
        return name;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public ArrayList<Poison> getPoisons() {
        return poisons;
    }

    public void setBoosts(ArrayList<Boost> boosts) {
        this.boosts = boosts;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    @Override
    public String toString() {
        String s = ">" + name + " : ";
        for(Boost b : boosts) {
            s += "\n   " + b.toString();
        }
        return s;
    }
}

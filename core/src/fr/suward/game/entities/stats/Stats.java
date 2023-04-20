package fr.suward.game.entities.stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.suward.aaa.HashMapDeserializer;
import fr.suward.aaa.HashMapSerializer;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.Effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Stats {

    @JsonSerialize(using = HashMapSerializer.class)
    @JsonDeserialize(using = HashMapDeserializer.class)
    private HashMap<String, Stat> stats = new HashMap<>();

    @JsonIgnore
    private ArrayList<Effect> effects = new ArrayList<>();

    public Stats(){
        init();
    }

    public int get(String name) {
        Stat s = getStat(name);
        int boost = 0;

        for (Effect e : effects) {
            for (Boost b : e.getBoosts()) {
                if(Objects.equals(b.getStatName(), name)) {
                    boost += b.getEffectiveValue();
                }
            }
        }

        return s.getActual() + s.getBoost() + boost;
    }


    public void update() {
        ArrayList<Effect> toRemove = new ArrayList<>();
        for(Effect e : effects) {
            if(e.update()) {
                toRemove.add(e);
            }
        }
        for(Effect e : toRemove) {
            effects.remove(e);
        }
    }

    public void set(String name, int value) {
        stats.get(name).setActual(value);
    }

    public int add(String name, int value) {
        stats.get(name).setActual(stats.get(name).getActual() + value);
        return value;
    }

    public int add(String name, int value, boolean canOverAdd) {
        int realValue = add(name, value);
        if(stats.get(name).getActual() > stats.get(name).getMax() && !canOverAdd) {
            realValue -= (stats.get(name).getActual() - stats.get(name).getMax());
            stats.get(name).setActual(stats.get(name).getMax());
        }
        return realValue;
    }

    public Stat getStat(String name) {
        return stats.get(name);
    }

    /**
     * Apply value to a stat.
     * @param name
     * @param value
     */
    public void apply(String name, int value) {
        stats.get(name).setMax(value);
        stats.get(name).setInitialMax(value);
        stats.get(name).reset();
    }

    public void init() {
        stats.put("pv", new Stat("Points de Vie", "PV"));
        stats.put("pm", new Stat("Points de Magie", "PM"));
        stats.put("pc", new Stat("Points de Concentration", "PC"));
        stats.put("pd", new Stat("Points de Déplacement", "PD"));

        stats.put("pb", new Stat("Points de Bouclier", "PB"));
        stats.put("ero", new Stat("Erosion", "Erosion"));

        stats.put("vita", new Stat("Vitalité", "Vita."));
        stats.put("sag", new Stat("Sagesse", "Sag."));
        stats.put("fo", new Stat("Force", "Fo."));
        stats.put("agi", new Stat("Agilité", "Agi."));
        stats.put("pui", new Stat("Puissance", "Pui."));

        stats.put("ini", new Stat("Initiative", "Ini."));
        stats.put("crit", new Stat("Critique", "Crit."));
        stats.put("po", new Stat("Portée", "PO"));
        stats.put("tacle", new Stat("Tacle", "Tac."));
        stats.put("fuite", new Stat("Fuite", "Fui."));
        stats.put("esquive", new Stat("Esquive", "Esq."));
        stats.put("ret", new Stat("Retrait", "Ret."));

        stats.put("dg dist", new Stat("Dégats distance", "Dg Dist."));
        stats.put("dg mel", new Stat("Dégats mélée", "Dg Mél."));
        stats.put("do", new Stat("Dommages", "Do."));
        stats.put("do eau", new Stat("Dommages Eau", "Do. Eau"));
        stats.put("do feu", new Stat("Dommages Feu", "Do. Feu"));
        stats.put("do air", new Stat("Dommages Air", "Do. Air"));
        stats.put("do foudre", new Stat("Dommages Foudre", "Do. Fou."));
        stats.put("do crit", new Stat("Dommages Critique", "Do. Crit."));
        stats.put("do pou", new Stat("Dommages Poussée", "Do. Pou"));

        stats.put("res dist", new Stat("Résistance Distance", "Res. Dist."));
        stats.put("res mel", new Stat("Résistance Mélée", "Res. Mél."));
        stats.put("res eau", new Stat("Résistance Eau", "Res. Eau"));
        stats.put("res feu", new Stat("Résistance Feu", "Res. Feu"));
        stats.put("res air", new Stat("Résistance Air", "Res. Air"));
        stats.put("res foudre", new Stat("Résistance Fou", "Res. Fou"));
        stats.put("res crit", new Stat("Résistance Critique", "Res. Crit"));
        stats.put("res pou", new Stat("Résistance Poussée", "Res. Pou"));

        stats.put("% dmg subis", new Stat("% Dommages subis", "% Dmg. subis"));

        for (Stat s : stats.values()) {
            s.init();
        }
    }

    public int countEffectWithName(String name) {
        int count = 0;
        for(Effect e : effects) {
            if(e.getName() == name) {
                count += 1;
            }
        }
        return count;
    }

    public boolean hasEffectWithName(String name) {
        for(Effect e : effects) {
            if(e.getName() == name) {
                return true;
            }
        }
        return false;
    }

    public int[] getBiStat(String name, String doName) {
        return new int[]{get(name), get("do " + doName)};
    }

    public void reset() {
        for (Stat s : stats.values()) {
            s.reset();
        }
        effects = new ArrayList<>();
    }

    public void addEffects(Effect effect) {
        effects.add(effect);
    }

    public void removeEffects(Effect effect) {
        effects.remove(effect);
    }

    public void removeEffects(String effectName) {
        ArrayList<Effect> toRemove = new ArrayList<>();
        for(Effect e : effects) {
            if(e.getName() == effectName) {
                toRemove.add(e);
            }
        }
        for(Effect e : toRemove) {
            effects.remove(e);
        }
    }

    public void removeEffects(int i) {
        effects.remove(i);
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public static String fullNameOf(String str) {
        switch (str) {
            case "pv":
                return " Points de Vie";
            case "pm":
                return " Points de Magie";
            case "pc":
                return " Points de Concentration";
            case "pd":
                return " Points de Déplacement";
            case "pb":
                return " Points de Bouclier";

            case "ero":
                return "% d'érosion";

            case "vita":
                return " Vitalité";
            case "sag":
                return " Sagesse";
            case "fo":
                return " Force";
            case "agi":
                return " Agilité";
            case "pui":
                return " Puissance";

            case "ini":
                return " Initiative";
            case "crit":
                return " Critique";
            case "po":
                return " Portée";
            case "tacle":
                return " Tacle";
            case "fuite":
                return " Fuite";
            case "esquive":
                return " Esquive";
            case "ret":
                return " Retrait";

            case "dg dist":
                return "% Dégats distance";
            case "dg mel":
                return "% Dégats mélée";
            case "do":
                return " Dommages";
            case "do eau":
                return " Dommages Eau";
            case "do feu":
                return " Dommages Feu";
            case "do air":
                return " Dommages Air";
            case "do foudre":
                return " Dommages Foudre";
            case "do crit":
                return " Dommages Critique";
            case "do pou":
                return " Dommages Poussée";

            case "res dist":
                return "% Résistance Distance";
            case "res mel":
                return "% Dégats mélée";
            case "res eau":
                return "% Résistance Eau";
            case "res feu":
                return "% Résistance Feu";
            case "res air":
                return "% Résistance Air";
            case "res foudre":
                return "% Résistance Foudre";
            case "res crit":
                return "% Résistance Critique";
            case "res pou":
                return "% Résistance Poussée";

            case "% dmg subis":
                return "% Dommages subis";

            default:
                return "";

        }
    }

}

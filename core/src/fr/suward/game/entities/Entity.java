package fr.suward.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.suward.Constants;
import fr.suward.assets.FontManager;
import fr.suward.display.popups.DamageDisplay;
import fr.suward.display.popups.PreviewPopup;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.entities.stats.Stats;
import fr.suward.game.pathfinding.Path;
import fr.suward.managers.ClientManager;
import fr.suward.network.packets.EntityPacket;
import fr.suward.network.packets.SpellCastingPacket;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {

    // Primary Attributes //
    protected String pseudo = "";
    @JsonProperty("classID")
    protected int classID;
    @JsonIgnore
    protected ClassData classData;
    @JsonProperty("niv")
    protected int niv = 1;
    @JsonProperty("id")
    protected int id = -2;

    // Stats //
    @JsonIgnore
    protected Stats stats;

    // Secondary Attributes //
    @JsonIgnore
    protected int team = 1;
    @JsonIgnore
    protected int iniOrder = -1;
    @JsonIgnore
    protected int turn = 0;
    @JsonIgnore
    protected int turnDuration = 30;
    @JsonIgnore
    protected boolean isItsTurn = false;
    @JsonIgnore
    protected boolean isReady = false;
    @JsonIgnore
    protected Point position = new Point(950-32, 600);
    protected Point beginningTurnPosition = new Point(0, 0);

    @JsonIgnore
    protected boolean isInvocation = false;
    @JsonIgnore
    protected boolean isStatic = false;
    @JsonIgnore
    protected ArrayList<Character> summons = new ArrayList<>();
    @JsonIgnore
    protected Entity summoner;

    @JsonIgnore
    protected PreviewPopup previewPopup;
    @JsonIgnore
    protected boolean isPopup = false;


    protected Entity(String pseudo, ClassData classData) {
        this.pseudo = pseudo;
        this.classData = classData;

        classID = classData.getClassID();

        init();
    }

    protected Entity(EntityPacket ep) {
        pseudo = ep.getPseudo();
        classID = ep.getClassID();
        classData = Constants.classFromClassName(classID);
        niv = ep.getNiv();
        id = ep.getId();
        stats = ep.getStats();
        team = ep.getTeam();
        iniOrder = ep.getIniOrder();
        turn = ep.getTurn();
        isReady = ep.isReady();
        position = ep.getPosition();
        isInvocation = ep.isInvocation();
        isStatic = ep.isStatic();

        init();
    }

    protected void init() {
        stats = new Stats();
        classData.applyStats(stats);
        turnDuration = classData.getTurnDuration();
    }

    protected Entity() {}

    public abstract void die();

    public void updateSpell(SpellCastingPacket p) {
        for(int i = 0; i < this.getClassData().getSpells().size(); i++) {
            if(classData.getSpells().get(i).getClass() == p.getSpell().getClass()) {
                classData.getSpells().get(i).setSpellBoosts(p.getSpell().getSpellBoosts());
                classData.getSpells().get(i).setDamageLines(p.getSpell().getDamageLines());
                //classData.getSpells().get(i).setEffects(p.getSpell().getEffects());
                classData.getSpells().get(i).setCritic(p.getSpell().getCritic());
                //for(int j = 0; j < this.getClassData().getSpells().get(i).getEffects().size(); j++) {
                 //   this.getClassData().getSpells().get(i).getEffects().get(j).setBoosts(p.getSpell().getEffects().get(i).getBoosts());
                //}
                break;
            }
        }
    }


    //____________________________________________________________________________________//
    public void takeDamage(int sumDamage, ArrayList<Integer> damageList, int pushDamage, int erosion) {
        boolean isIncurable = false;
        for(Effect e : stats.getEffects()) {
            for(Boost b : e.getBoosts()) {
                if(b.getStatusID() == Effect.INCURABLE) {
                    isIncurable = true;
                    break;
                }
            }
        }
        if(isIncurable) {
            ArrayList<Integer> toRemove = new ArrayList<>();
            for(int d : damageList) if(d < 0) toRemove.add(d);
            for(Integer d : toRemove) damageList.remove((Object)d);
            sumDamage = 0;
            for(int d : damageList) sumDamage += d;
        }

        sumDamage += pushDamage;
        if(stats.get("pv") - sumDamage <= 0) {
            killTarget(damageList, sumDamage);
        }

        for(int dmg : damageList) {
            if(dmg >= 0) {
                ClientManager.get().sendMessageInChat("    " + getPseudo() + " : -" + dmg + " PV.",FontManager.flashyRed);

            } else {
                ClientManager.get().sendMessageInChat("    " + getPseudo() + " : +" + (-dmg) + " PV.",FontManager.green2);
            }
        }
        if(pushDamage > 0) {
            ClientManager.get().sendMessageInChat("    " + getPseudo() + " : -" + pushDamage + " PV.",FontManager.flashyRed);

        }

        stats.add("pv", -sumDamage, false);
        // Erosion //
        if(-sumDamage < 0) {
            stats.getStat("pv").setMax(Math.max(1, (int) (stats.getStat("pv").getMax() - sumDamage * (10 + erosion)/100f)));
            if(stats.get("pv") > stats.getStat("pv").getMax()) {
                stats.set("pv", stats.getStat("pv").getMax());
            }
        }
        ClientManager.get().getDamageToRender().add(new DamageDisplay(-sumDamage, position));
    }

    public void takeDamage(int sumDamage, int pushDamage, int erosion) {
        ArrayList<Integer> damageList = new ArrayList<>();
        damageList.add(sumDamage);
        takeDamage(sumDamage, damageList, pushDamage, erosion);
    }


    public abstract EntityPacket getEntityPacket();



    //____________________ Extracted Methods _______________________________________________//
    private void killTarget(ArrayList<Integer> damageList, int sumDamage) {
        while(stats.get("pv") - sumDamage <= 0) {
            int lastDamage = - (stats.get("pv") - sumDamage);
            if(!damageList.isEmpty()) {
                lastDamage += damageList.get(damageList.size()-1);
            }
            if(lastDamage < 0) {
                sumDamage -= damageList.get(damageList.size()-1);
                damageList.remove(damageList.size()-1);
            } else {
                if(!damageList.isEmpty()) damageList.remove(damageList.size()-1);
                damageList.add(lastDamage);
                break;
            }
        }
        die();
    }


    public int removeStatusWithID(int id) {
        int amount = 0;
        for(Effect e : stats.getEffects()) {
            ArrayList<Boost> boostsToRemove = new ArrayList<>();
            for (Boost b : e.getBoosts()) {
                if(b.getStatusID() == id) {
                    boostsToRemove.add(b);
                    amount ++;
                }
            }
            for(Boost b : boostsToRemove) {
                e.getBoosts().remove(b);
            }
        }
        return amount;
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(int i, int j) {
        position = new Point(i,j);
    }
    public void setPosition(Point p) {
        position = p;
    }
    public void addX(int x) {
        position.x += x;
    }
    public void addY(int y) {
        position.y += y;
    }

    public ClassData getClassData() {
        return classData;
    }

    public void move(int dx, int dy) {
        position.x += dx;
        position.y += dy;
    }

    public Stats getStats() {
        return stats;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void addEffect(Effect effect, String msg) {
        this.stats.addEffects(effect);
        ClientManager.get().sendMessageInChat(msg, FontManager.flashyRed);
    }

    public int getId() {
        return id;
    }

    public int getClassName() {
        return classID;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInvocation() {
        return isInvocation;
    }

    public void setItsTurn(boolean itsTurn) {
        isItsTurn = itsTurn;
    }

    public boolean isItsTurn() {
        return isItsTurn;
    }

    public abstract void moveCharacter(Path path);

    public abstract void turn();

    public abstract void turn(boolean isServer);

    public abstract void endTurn();

    public abstract void endTurn(boolean isServer);

    public PreviewPopup getPreviewPopup() {
        return previewPopup;
    }

    public void setPreviewPopup(PreviewPopup previewPopup) {
        this.previewPopup = previewPopup;
    }

    public boolean isPopup() {
        return isPopup;
    }

    public void setPopup(boolean popup) {
        isPopup = popup;
    }

    public void setInvocation(boolean invocation) {
        isInvocation = invocation;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public ArrayList<Character> getSummons() {
        return summons;
    }

    public void setSummons(ArrayList<Character> summons) {
        this.summons = summons;
    }

    public void setSummoner(Entity summoner) {
        this.summoner = summoner;
    }

    public Entity getSummoner() {
        return summoner;
    }

    public Entity getSummonerOrSelf() {
        if(summoner != null) {
            return summoner;
        } else {
            return this;
        }
    }

    public int getTurnDuration() {
        return turnDuration;
    }

    public void setTurnDuration(int turnDuration) {
        this.turnDuration = turnDuration;
    }

    public Point getBeginningTurnPosition() {
        return beginningTurnPosition;
    }

    public void setBeginningTurnPosition(Point beginningTurnPosition) {
        this.beginningTurnPosition = beginningTurnPosition;
    }
}

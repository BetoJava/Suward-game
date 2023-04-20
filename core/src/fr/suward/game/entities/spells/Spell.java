package fr.suward.game.entities.spells;


import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.suward.assets.FontManager;
import fr.suward.display.components.SpellButton;
import fr.suward.display.states.stages.BattleState;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.classes.erit.Ciblage;
import fr.suward.game.entities.spells.pdcity.oeuf.Fracture;
import fr.suward.game.entities.spells.pdcity.pasdoeuf.DepressionPartagee;
import fr.suward.game.entities.spells.pdcity.pasdoeuf.GrosFront;
import fr.suward.game.utils.BattleFunctions;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.SpellCastingPacket;

import java.awt.*;
import java.util.ArrayList;

public abstract class Spell {

    public static int lastID = 9;

    // Cast Zones
    public static final int CIRCLE_CAST = 0;
    public static final int CROSS_CAST = 1;
    public static final int DIAGONAL_CAST = 2;
    public static final int STAR_CAST = 3;
    public static final int SQUARE_CAST = 4;

    // Target Zones
    public static final int CIRCLE_ZONE = 0;
    public static final int CROSS_ZONE = 1;
    public static final int ROW_ZONE = 2;
    public static final int LINE_ZONE = 3;
    public static final int SQUARE_ZONE = 4;
    public static final int T_ZONE = 5;
    public static final int TRAIL_ZONE = 6;
    public static final int INVERT_CIRCLE_ZONE = 7;
    public static final int CIRCLE_ZONE_WITHOUT_CENTER = 8;

    // Target types
    public static final int ALL_TARGET = 0;
    public static final int ALLY_TARGET = 1;
    public static final int FOE_TARGET = 2;
    public static final int SUMMON_TARGET = 3;
    public static final int ALLY_SUMMON_TARGET = 4;
    public static final int FOE_SUMMON_TARGET = 5;
    public static final int EMPTY_TARGET = 6;

    // General attributes //
    protected String name = "";
    protected int textureID;
    protected ArrayList<DamageLine> damageLines = new ArrayList<>();
    protected ArrayList<Effect> effects = new ArrayList<>();
    protected ArrayList<SpellBoost> spellBoosts = new ArrayList<>();
    protected ArrayList<Integer> targetedID = new ArrayList<>();
    protected int push = 0;
    protected int minRange = 0;
    protected int maxRange = 1;
    protected int critic = -1000;
    protected int manaCost = 0;
    protected int concentrationCost = 0;
    protected int travelCost = 0;

    // Use //
    protected int maxUses = 1;
    protected int uses = 0;
    protected int maxDelay = 0;
    protected int delay = 0;
    protected int maxUsesOnTarget = 1000;

    // Cast attributes //
    protected int castZone = CIRCLE_CAST;
    protected int targetZone = CIRCLE_ZONE;
    protected int zoneSize = 0;
    protected int targetType = ALL_TARGET;

    // Cast parameters //
    protected boolean isRangeVariable = true;
    protected boolean isSightLine = true;
    protected boolean isTargetNecessary = false;
    protected boolean isPushFromCenter = false;
    protected boolean isDistanceReduced = true;

    // Secondary attributes //
    protected String effectDescription = "";
    protected String spellType = "offensive";
    protected int priority = 100;
    protected boolean isSummonSpell = false;

    protected int index;


    protected Spell() {
        addEffect();
        init();
    }

    public void init() {
        uses = maxUses;
        delay = 0;
    }

    public void update() {
        targetedID.clear();
        for(int i = spellBoosts.size() - 1; i >= 0; i--) {
            if(spellBoosts.get(i).update()) {
                spellBoosts.remove(i);

                updateView();
            }
        }

        ArrayList<DamageLine> toRemove = new ArrayList<>();
        for(DamageLine dl : damageLines) {
            if(dl.update()) {
                toRemove.add(dl);
            }
        }
        for(DamageLine dl : toRemove) {
            damageLines.remove(dl);
        }

        delay -= 1;
        uses = maxUses;

    }

    public void updateView() {
        BattleState state = (BattleState) StageManager.get().getStage();
        for(Actor b : state.getSpellInterface().getSpellTable().getChildren()) {
            SpellButton sb = (SpellButton) b;
            sb.createEffectLines();
        }
    }

    private boolean isDisable() {
        return (delay > 0 || uses <= 0);
    }

    /**
     * Method called when the client try to use a spell. If all casting conditions are checked, the Client send to other
     * Clients that this spell is used.
     * @param caster
     * @param targets
     * @param entitiesPositions
     * @param zone
     * @param pos
     */
    public void use(Entity caster, ArrayList<Entity> targets, ArrayList<Point> entitiesPositions, ArrayList<Point> zone, Point pos, boolean sendToOtherClient) {
        if(!isDisable()) {
            if(maxUsesOnTarget < 100) {
                if(maxUsesOnTarget <= getAmountOfUsesOnTarget(targets, pos)) {
                    return;
                }
            }
            if(!isTargetNecessary || entitiesPositions.contains(pos)) {
                applyTargetType(caster, targets, entitiesPositions);
                if((caster.getStats().get("pm") >= manaCost || manaCost <= 0) && (caster.getStats().get("pc") >= concentrationCost || concentrationCost <= 0) && (caster.getStats().get("pd") >= travelCost || travelCost <= 0)) {
                    ClientManager.get().getClient().sendTCP(new SpellCastingPacket(this, caster, targets, entitiesPositions, zone, pos));
                }
            }
        }
    }

    public void use(Entity caster, ArrayList<Entity> targets, ArrayList<Point> entitiesPositions, ArrayList<Point> zone, Point pos) {
        use(caster, targets, entitiesPositions, zone, pos, true);
    }

        private void applyTargetType(Entity caster, ArrayList<Entity> targets, ArrayList<Point> entitiesPositions) {
        if(targetType == Spell.ALLY_TARGET || targetType == Spell.FOE_TARGET) {
            ArrayList<Entity> entitiesToRemove = new ArrayList<>();
            for(Entity e : targets) {
                if(e.getTeam() != caster.getTeam() && targetType == Spell.ALLY_TARGET || e.getTeam() == caster.getTeam() && targetType == Spell.FOE_TARGET) {
                    entitiesToRemove.add(e);
                    entitiesPositions.remove(e.getPosition());
                }
            }
            for(Entity e : entitiesToRemove) {
                targets.remove(e);
            }
        }
        // Faire pour les summons aussi
    }


    public void use(SpellCastingPacket p) {
        Entity caster = p.getCaster();
        caster.getStats().add("pm", -manaCost);
        caster.getStats().add("pc", -concentrationCost);
        caster.getStats().add("pd", -travelCost);
        this.uses -= 1;
        this.delay = maxDelay;
        Entity target = getTargetOnCastPos(p.getTargets(), p.getClickPos());
        if(target != null) {
            targetedID.add(target.getId());
        }

        ClientManager.get().sendMessageInChat(caster.getPseudo() + " utilise " + this.getName() + " !", FontManager.green2);
        if(p.isCritical()) {
            ClientManager.get().sendMessageInChat("    Coup Critique !", FontManager.flashyRed);
        }

        action(this, p.isCritical(), p.getRds(), caster, p.getTargets(), p.getZone(), p.getClickPos());

        updateShadow();

    }

    public static void updateShadow() {
        BattleState state = (BattleState) StageManager.get().getStage();
        try {
            for(Actor b : state.getSpellInterface().getSpellTable().getChildren()) {
                SpellButton sb = (SpellButton) b;
                sb.setDisable(sb.getSpell().isDisable());
                // disable button and add dark
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isDisabled() {
        return uses <= 0 || delay > 0;
    }

    // Action methods //
    public abstract void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos);
    public abstract void startAction();
    public abstract void endAction();


    // Fight methods //
    public int hit(boolean isCritical, Entity caster, Entity target, Point castPosition) {
        return hit(isCritical, caster, target, castPosition, 1, false);
    }

    public int hit(boolean isCritical, Entity caster, Entity target, Point castPosition, float multiplier) {
        return hit(isCritical, caster, target, castPosition, multiplier, false);
    }

    public int hit(boolean isCritical, Entity caster, Entity target, Point castPosition, float multiplier, boolean isSharedHit) {
        if(!isSharedHit) {
            int x = sharedDamage(isCritical, caster, target, castPosition);
            if (x == 1) return 0;
        }

        ArrayList<Integer> damageList = new ArrayList<>();
        int sumDamage = getDamageSum(caster, target, damageLines, castPosition, isCritical, damageList, multiplier);

        if(target.getStats().hasEffectWithName(Fracture.effectName) && push != 0) {
            for(int i = 0; i < Math.abs(push); i++) {
                damageList.add(BattleFunctions.damageLine(this, caster, target, Fracture.damageLine, castPosition, false));
                sumDamage += damageList.get(damageList.size() - 1);
            }
        }


        int totalDamage = target.getStats().get("pv");
        target.takeDamage(sumDamage, damageList, push(caster, target, castPosition), target.getStats().get("ero"));
        totalDamage = Math.max(0, totalDamage - target.getStats().get("pv"));
        int drain = 0;
        for(int i = 0; i < damageLines.size(); i++) {
            if(damageLines.get(i).isDrain()) {
                drain += damageList.get(i)/2f;
            }
        }
        if(drain > 0) {
            heal(caster, drain);
        }

        specialEffects(caster, target, totalDamage);

        return totalDamage;
    }

    private Integer sharedDamage(boolean isCritical, Entity caster, Entity target, Point castPosition) {
        if(target.getStats().hasEffectWithName(DepressionPartagee.effectName)) {
            ArrayList<Entity> entitiesEffected = new ArrayList<>();
            for(Entity entity : ClientManager.get().getAliveEntities()) {
                if(entity.getStats().hasEffectWithName(DepressionPartagee.effectName)) {
                    entitiesEffected.add(entity);
                }
            }
            for(Entity entity : entitiesEffected) {
                hit(isCritical, caster, entity, castPosition, 1f/entitiesEffected.size(), true);
            }
            return 1;
        }
        return 0;
    }

    private void specialEffects(Entity caster, Entity target, int totalDamage) {
        if(target.getStats().hasEffectWithName(Ciblage.effectName) && !areSameTeam(caster, target)) {
            heal(caster, (int)(Ciblage.healCoefficient* totalDamage));
        }
        if(target.getStats().hasEffectWithName(GrosFront.effectName)) {
            for(Effect e : target.getStats().getEffects()) {
                if(e.getName() == GrosFront.effectName) {
                    if(e.getEffectDuration() == 2) {
                        Effect effect = new Effect("Malus Gros Front",2);
                        effect.add(new Boost("pm", -1, 2));
                        effect.add(new Boost("pc", -1, 2));
                        effect.add(new Boost("po", -1, 2));
                        effect.add(new Boost("dg mel", -10, 2));
                        effect.add(new Boost("dg dist", -10, 2));
                        effect.add(new Boost("res feu", -10, 2));
                        effect.add(new Boost("res eau", -10, 2));
                        effect.add(new Boost("res air", -10, 2));
                        effect.add(new Boost("res foudre", -10, 2));
                        effect.addEffectToTarget(target, caster, target.getPseudo() + " perd 1 PM/PC/PO et 10% res et Dommages finaux.");
                        break;
                    }
                }
            }
        }

    }

    public int push(Entity caster, Entity target, Point castPosition) {
        for(Effect e : target.getStats().getEffects()) {
            for(Boost b : e.getBoosts()) {
                if(b.getStatusID() == Effect.ROOTED || b.getStatusID() == Effect.UNMOVABLE) {
                    return 0;
                }
            }
        }

        if(push > 0) {
            return BattleFunctions.push(this, caster, target, castPosition);
        } else if(push < 0) {
            BattleFunctions.push(this, caster, target, castPosition);
        }

        return 0;
    }

    public void heal(Entity target, int value) {
        for(Effect e : target.getStats().getEffects()) {
            for(Boost b : e.getBoosts()) {
                if(b.getStatusID() == Effect.INCURABLE) {
                    return;
                }
            }
        }
        target.takeDamage(-value, 0, 0);
    }

    public void summon(Entity caster, Point pos, Character c) {
        summon(caster, pos, c, false);
    }

    public void summon(Entity caster, Point pos, Character c, boolean statsFromSummoner) {
        if(statsFromSummoner) {
            for(String name : new String[]{"crit", "po", "sag", "fo", "agi", "pui",
                    "do", "do eau", "do air", "do feu", "do foudre", "do crit", "do pou"}) {
                c.getStats().apply(name, caster.getStats().getStat(name).getInitialMax());
            }
        }
        c.setPosition(pos);
        c.setTeam(caster.getTeam());
        c.setReady(true);
        c.setInvocation(true);
        c.setSummoner(caster);
        caster.getSummons().add(c);
        ClientManager.get().getGameManager().createNewEntity(c);
    }

    public void forward(Entity entity, Point pos) {
        for(Effect e : entity.getStats().getEffects()) {
            for(Boost b : e.getBoosts()) {
                if(b.getStatusID() == Effect.ROOTED || b.getStatusID() == Effect.UNMOVABLE) {
                    return;
                }
            }
        }
        BattleFunctions.forward(this, entity, pos);
    }

    public static void teleport(Entity entity, Point pos) {
        for(Effect e : entity.getStats().getEffects()) {
            for(Boost b : e.getBoosts()) {
                if(b.getStatusID() == Effect.ROOTED || b.getStatusID() == Effect.GRAVITY || b.getStatusID() == Effect.UNMOVABLE || b.getStatusID() == Effect.HEAVY) {
                    return;
                }
            }
        }
        BattleFunctions.teleport(entity, pos);
    }

    protected static boolean areSameTeam(Entity caster, Entity target) {
        return caster.getTeam() == target.getTeam();
    }

    protected abstract void addEffect();

    public void addSpellBoost(SpellBoost spellBoost, Entity caster) {

        spellBoosts.add(spellBoost);
        int boost = 0;
        for(SpellBoost sb : this.getSpellBoosts()) {
            boost += sb.getDamage();
        }
    }


    //____________________ Extracted Methods _______________________________________________//
    public int getDamageSum(Entity caster, Entity target, ArrayList<DamageLine> damageLines, Point castPosition, boolean isCritical, ArrayList<Integer> damageList, float multiplier) {
        int sumDamage = 0;
        for(DamageLine dl : damageLines) {
            if(!dl.isPoison()) {
                damageList.add((int) (BattleFunctions.damageLine(this, caster, target, dl, castPosition, isCritical)*multiplier));
                sumDamage += damageList.get(damageList.size()-1);
            }
        }
        return sumDamage;
    }


    private int addMinDamageLines(Entity caster, Entity target, ArrayList<DamageLine> damageLines, Point castPosition, boolean isCritical, ArrayList<Integer> damageList) {
        int sumDamage = 0;
        for(DamageLine dl : damageLines) {
            damageList.add(BattleFunctions.damageLine(this, caster, target, dl, castPosition, isCritical));
            sumDamage += damageList.get(damageList.size()-1);
        }
        return sumDamage;
    }

    private int addMaxDamageLines(Entity caster, Entity target, ArrayList<DamageLine> damageLines, Point castPosition, boolean isCritical, ArrayList<Integer> damageList) {
        int sumDamage = 0;
        for(DamageLine dl : damageLines) {
            damageList.add(BattleFunctions.damageLine(this, caster, target, dl, castPosition, isCritical));
            sumDamage += damageList.get(damageList.size()-1);
        }
        return sumDamage;
    }

    protected void applyDamageLinesToPoisons(Effect e) {
        for(int i = 0; i < damageLines.size(); i++) {
            e.getPoisons().get(i).setDamageLine(damageLines.get(i));
        }
    }

    protected int getAmountOfUsesOnTarget(ArrayList<Entity> targets, Point pos) {
        Entity target = getTargetOnCastPos(targets, pos);

        if(target == null) {
            return 0;
        }

        int amount = 0;
        for(int id : targetedID) {
            if(id == target.getId()) {
                amount +=1;
            }
        }
        return amount;
    }

    protected Entity getTargetOnCastPos(ArrayList<Entity> targets, Point pos) {
        Entity target = null;
        for(Entity t : targets) {
            if(t.getPosition().x == pos.x && t.getPosition().y == pos.y) {
                target = t;
                break;
            }
        }
        return target;
    }

    //_____________________ Getters and Setters __________________________//

    public boolean getRandoms(Entity caster) {
        for(DamageLine dl : damageLines) {
            dl.setBaseDamage(BattleFunctions.random(dl, spellBoosts));
        }
        return BattleFunctions.isCritic(caster, this);
    }

    public boolean getMinRandom(Entity caster) {
        int boost = 0;
        for(SpellBoost sb : spellBoosts) {
            boost += sb.getDamage();
        }

        for(DamageLine dl : damageLines) {
            dl.setBaseDamage(dl.getMinDamage() + boost);
        }
        return BattleFunctions.isCritic(caster, this);
    }

    public boolean getMaxRandom(Entity caster) {
        int boost = 0;
        for(SpellBoost sb : spellBoosts) {
            boost += sb.getDamage();
        }

        for(DamageLine dl : damageLines) {
            dl.setBaseDamage(dl.getMaxDamage() + boost);
        }
        return BattleFunctions.isCritic(caster, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DamageLine> getDamageLines() {
        return damageLines;
    }

    public void setDamageLines(ArrayList<DamageLine> damageLines) {
        this.damageLines = damageLines;
    }

    public int getPush() {
        return push;
    }

    public void setPush(int push) {
        this.push = push;
    }

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
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

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getCastZone() {
        return castZone;
    }

    public void setCastZone(int castZone) {
        this.castZone = castZone;
    }

    public int getTargetZone() {
        return targetZone;
    }

    public void setTargetZone(int targetZone) {
        this.targetZone = targetZone;
    }

    public int getZoneSize() {
        return zoneSize;
    }

    public void setZoneSize(int zoneSize) {
        this.zoneSize = zoneSize;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public boolean isRangeVariable() {
        return isRangeVariable;
    }

    public void setRangeVariable(boolean rangeVariable) {
        isRangeVariable = rangeVariable;
    }

    public boolean isSightLine() {
        return isSightLine;
    }

    public void setSightLine(boolean sightLine) {
        isSightLine = sightLine;
    }

    public boolean isTargetNecessary() {
        return isTargetNecessary;
    }

    public void setTargetNecessary(boolean targetNecessary) {
        isTargetNecessary = targetNecessary;
    }

    public boolean isPushFromCenter() {
        return isPushFromCenter;
    }

    public void setPushFromCenter(boolean pushFromCenter) {
        isPushFromCenter = pushFromCenter;
    }

    public boolean isDistanceReduced() {
        return isDistanceReduced;
    }

    public void setDistanceReduced(boolean distanceReduced) {
        isDistanceReduced = distanceReduced;
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public void setEffectDescription(String effectDescription) {
        this.effectDescription = effectDescription;
    }

    public String getSpellType() {
        return spellType;
    }

    public void setSpellType(String spellType) {
        this.spellType = spellType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isSummonSpell() {
        return isSummonSpell;
    }

    public void setSummonSpell(boolean summonSpell) {
        isSummonSpell = summonSpell;
    }

    public int getTextureID() {
        return textureID;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public ArrayList<SpellBoost> getSpellBoosts() {
        return spellBoosts;
    }

    public void setSpellBoosts(ArrayList<SpellBoost> spellBoosts) {
        this.spellBoosts = spellBoosts;
    }

    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxUsesOnTarget() {
        return maxUsesOnTarget;
    }

}

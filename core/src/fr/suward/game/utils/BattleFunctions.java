package fr.suward.game.utils;

import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.*;
import fr.suward.game.map.Map;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BattleFunctions {

    public static int damageLine(Spell spell, Entity caster, Entity target, DamageLine damageLine, Point castPosition, boolean isCritical) {

        int[] biStat = getBiStat(caster, damageLine.getElement());
        int sumPui = biStat[0];
        int sumDo = biStat[1] + caster.getStats().get("do");

        // Critical changes //
        if (isCritical) {
            sumDo += caster.getStats().get("do crit");
        }

        // Distance multiplication //
        int distance = distance(castPosition, target.getPosition());
        float multi = 1f;
        if (spell.isDistanceReduced()) {
            multi -= 0.1f * distance;
        }




        //int baseDamage = random(damageLine);

        if (damageLine.isHeal()) {
            return - heal(sumPui, sumDo, multi, damageLine.getBaseDamage());
        } else {
            return hit(caster, target, damageLine, isCritical, sumPui, sumDo, distance, multi, damageLine.getBaseDamage());
        }
    }

    public static int poisonDamageLine(Entity caster, Entity target, DamageLine damageLine) {
        int[] biStat = getBiStat(caster, damageLine.getElement());
        int sumPui = biStat[0];
        int sumDo = biStat[1] + caster.getStats().get("do");

        if (damageLine.isHeal()) {
            return - heal(sumPui, sumDo, 1, damageLine.getBaseDamage());
        } else {
            return hit(caster, target, damageLine, false, sumPui, sumDo, 0, 1, damageLine.getBaseDamage());
        }
    }

    public static int hit(Entity caster, Entity target, DamageLine damageLine, boolean isCritical, int sumPui, int sumDo, int distance, float multi, int baseDamage) {
        // Res //
        int sumRes = target.getStats().get("res " + stringStatFromElement(damageLine.getElement()));
        float sumDmgSubis = (100 + target.getStats().get("% dmg subis"))/100f;
        // Critical changes //
        if (isCritical) {
            sumRes += target.getStats().get("res crit");
        }
        // Distance changes //
        if (distance > 0) {
            multi += caster.getStats().get("dg dist") / 100f - target.getStats().get("res dist") / 100f;
        } else {
            multi += caster.getStats().get("dg mel") / 100f - target.getStats().get("res mel") / 100f;
        }
        // Invulnerable changes //
        if(distance(target.getPosition(), caster.getPosition()) > 1) {
            for(Effect e : target.getStats().getEffects()) {
                for(Boost b : e.getBoosts()) {
                    if(b.getStatusID() == Effect.DISTANCE_INVULNERABLE || b.getStatusID() == Effect.INVULNERABLE) {
                        return 0;
                    }
                }
            }
        } else {
            for(Effect e : target.getStats().getEffects()) {
                for(Boost b : e.getBoosts()) {
                    if(b.getStatusID() == Effect.MELEE_INVULNERABLE || b.getStatusID() == Effect.INVULNERABLE) {
                        return 0;
                    }
                }
            }
        }

        return (int) (sumDmgSubis * (multi * ((1 + (sumPui / 100f)) * (1 - (sumRes / 100f)) * baseDamage + sumDo)));
    }


    public static int heal(int sumPui, int sumDo, float multi, int baseDamage) {
        return (int) (multi * ((1 + (sumPui / 100f)) * baseDamage + sumDo));
    }


    public static int push(Spell spell, Entity caster, Entity target, Point castPosition) {

        int sumDo = caster.getStats().get("do pou");
        int sumRes = target.getStats().get("res pou");

        ArrayList<Point> pos = new ArrayList<>();
        for(Entity e : ClientManager.get().getAliveEntities()) {
            pos.add(e.getPosition());
        }

        Map map = ClientManager.get().getMap();
        int x;
        int y;
        int xt = (int) target.getPosition().getX();
        int yt = (int) target.getPosition().getY();

        int wallPush = 0;
        int distance = abs(spell.getPush());

        if(spell.isPushFromCenter()) {
            x = (int) castPosition.getX();
            y = (int) castPosition.getY();
        } else {
            x = (int) caster.getPosition().getX();
            y = (int) caster.getPosition().getY();
        }

        int dx = 1;
        int dy = 1;

        if(spell.getPush() < 0) {
            dx = -1;
            dy = -1;
        }

        if(x > xt) {
            dx *= -1;
        }
        if(y > yt) {
            dy *= -1;
        }

        if (diagonal(x, y, xt, yt)) {
            while(distance > 0) {
                if(isEmpty(xt+dx,yt+dy, map, pos) && isEmpty(xt+dx,yt, map, pos) && isEmpty(xt,yt+dy, map, pos)) {
                    target.addX(dx);
                    target.addY(dy);
                    xt += dy;
                    yt += dy;
                    distance -= 2;
                } else {
                    break;
                }
            }

        } else {
            boolean isXLine = xLine(x, y, xt, yt);
            boolean isYLine = yLine(x, y, xt, yt);
            while(distance > 0) {
                if(isXLine) {
                    if(isEmpty(xt + dx, yt, map, pos)) {
                        target.addX(dx);
                        xt += dx;
                        distance -= 1;

                    } else {
                        break;
                    }
                }
                if(isYLine) {
                    if(isEmpty(xt,yt+dy, map, pos)) {
                        target.addY(dy);
                        yt += dy;
                        distance -= 1;
                    } else {
                        break;
                    }
                }
            }
        }

        wallPush = distance;

        int baseDamage = 20 * wallPush;

        if(spell.getPush() > 0) {
            return (1 + ((sumDo - sumRes) / 100)) * baseDamage;
        }
            return 0;
    }

    public static void forward(Spell spell, Entity entity, Point pos) {
        ArrayList<Point> entitiesPos = new ArrayList<>();
        for(Entity e : ClientManager.get().getAliveEntities()) {
            entitiesPos.add(e.getPosition());
        }

        Map map = ClientManager.get().getMap();

        int xt = entity.getPosition().x;
        int yt = entity.getPosition().y;
        int x = pos.x;
        int y = pos.y;

        int dx = -1;
        int dy = -1;

        if(x > xt) {
            dx *= -1;
        }
        if(y > yt) {
            dy *= -1;
        }

        int distance = Math.abs(x - xt) + Math.abs(y - yt);

        if (diagonal(x, y, xt, yt)) {
            while(distance > 0) {
                if(isEmpty(xt+dx,yt+dy, map, entitiesPos) && isEmpty(xt+dx,yt, map, entitiesPos) && isEmpty(xt,yt+dy, map, entitiesPos)) {
                    entity.addX(dx);
                    entity.addX(dy);
                    xt += dy;
                    yt += dy;
                    distance -= 2;
                } else {
                    break;
                }
            }

        } else {
            boolean isXLine = xLine(x, y, xt, yt);
            boolean isYLine = yLine(x, y, xt, yt);
            while(distance > 0) {
                if(isXLine) {
                    if(isEmpty(xt + dx, yt, map, entitiesPos)) {
                        entity.addX(dx);
                        xt += dx;
                        distance -= 1;

                    } else {
                        break;
                    }
                }
                if(isYLine) {
                    if(isEmpty(xt,yt+dy, map, entitiesPos)) {
                        entity.addY(dy);
                        yt += dy;
                        distance -= 1;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static void teleport(Entity entity, Point pos) {
        int tileValue = ClientManager.get().getMap().get(pos.x, pos.y);
        if(tileValue == Map.GROUND || tileValue == Map.RED_POS || tileValue == Map.RED_POS) {
            for(Entity e : ClientManager.get().getAliveEntities()) {
                if(e.getPosition().x == pos.x && e.getPosition().y == pos.y) {
                    // Telef
                    for(Effect ef : e.getStats().getEffects()) {
                        for(Boost b : ef.getBoosts()) {
                            if(b.getStatusID() == Effect.ROOTED || b.getStatusID() == Effect.GRAVITY || b.getStatusID() == Effect.UNMOVABLE || b.getStatusID() == Effect.HEAVY) {
                                return;
                            }
                        }
                    }
                    e.setPosition(entity.getPosition().x, entity.getPosition().y);
                    entity.setPosition(pos.x, pos.y);
                    return;
                }
            }
            entity.setPosition(pos.x, pos.y);
        }

    }

    public static int random(int a, int b) {
        Random r = new Random();
        int rdPart;
        if(b - a == 0) {
            rdPart = 0;
        } else {
            rdPart = r.nextInt(Math.abs(b - a));
        }
        return Math.min(rdPart + a, rdPart + b);
    }

    public static int random(DamageLine dl, ArrayList<SpellBoost> spellBoosts) {
        int boost = 0;
        for(SpellBoost sb : spellBoosts) {
            boost += sb.getDamage();
        }
        return random(dl.getMinDamage() + boost, dl.getMaxDamage() + boost);
    }

    public static boolean isCritic(Entity caster, Spell spell) {
        int critic = caster.getStats().get("crit") + spell.getCritic();
        Random r = new Random();
        int rd = r.nextInt(99) + 1;

        return rd <= critic;
    }

    public static int distance(Point pos1, Point pos2) {
        return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y);
    }

    private static int[] getBiStat(Entity entity, int element) {
        return entity.getStats().getBiStat(stringElement(element), stringStatFromElement(element));
    }

    private static String stringElement(int intElement) {
        String name = "";
        if (intElement == DamageLine.FIRE_ELEMENT) {
            name = "fo";
        } else if (intElement == DamageLine.AIR_ELEMENT) {
            name = "agi";
        } else if (intElement == DamageLine.LIGHTNING_ELEMENT) {
            name = "pui";
        } else if (intElement == DamageLine.WATER_ELEMENT) {
            name = "sag";
        }
        return name;
    }

    public static String stringStatFromElement(String element) {
        String name = "";
        if (Objects.equals(element, "fo")) {
            name = "feu";
        } else if (Objects.equals(element, "agi")) {
            name = "air";
        } else if (Objects.equals(element, "pui")) {
            name = "foudre";
        } else if (Objects.equals(element, "sag")) {
            name = "eau";
        }
        return name;
    }

    public static String stringStatFromElement(int intElement) {
        String name = "";
        if (intElement == DamageLine.FIRE_ELEMENT) {
            name = "feu";
        } else if (intElement == DamageLine.AIR_ELEMENT) {
            name = "air";
        } else if (intElement == DamageLine.LIGHTNING_ELEMENT) {
            name = "foudre";
        } else if (intElement == DamageLine.WATER_ELEMENT) {
            name = "eau";
        }
        return name;
    }


    //___________ Extracted Methods ___________________________//
    private static int abs(int a) {
        return Math.abs(a);
    }

    private static boolean yLine(int x, int y, int xt, int yt) {
        return abs(xt - x) < abs(yt - y);
    }

    private static boolean xLine(int x, int y, int xt, int yt) {
        return abs(xt - x) > abs(yt - y);
    }

    private static boolean diagonal(int x, int y, int xt, int yt) {
        return abs(x-xt) == abs(y-yt);
    }

    private static boolean isEmpty(int i, int j, Map map, ArrayList<Point> entityPositions) {
        return map.isGround(i,j) && !in(i,j,entityPositions);
    }

    private static boolean in(int i, int j, ArrayList<Point> pos) {
        for(Point p : pos) {
            if(p.x == i && p.y == j) {
                return true;
            }
        }
        return false;
    }

}

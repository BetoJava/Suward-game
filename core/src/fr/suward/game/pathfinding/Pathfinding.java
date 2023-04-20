package fr.suward.game.pathfinding;


import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.map.Map;
import fr.suward.game.utils.BattleFunctions;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class Pathfinding {

    public static Path pathfinding(Map map, Entity entity, Point end, Point startPos) {

        // Init ________________________________________________________________________________________________________ //
        int[][] M = map.getData();
        int pd = entity.getStats().get("pd");
        int fuite = Math.max(0,entity.getStats().get("fuite"));
        if(startPos == null) startPos = entity.getPosition();

        // Impossible path _____________________________________________________________________________________________ //
        if(startPos.equals(end)) return new Path(pd+1); // PD + 1, so the player can't reach the point //
        if (end.x >= 0 && end.y >= 0 && end.x < M.length && end.y < M[0].length) {
            if (!in(M[startPos.x][startPos.y], new int[]{0, 2, 3}) || !in(M[end.x][end.y], new int[]{0, 2, 3})) {
                return new Path(pd+1);
            }
        } else return new Path(pd+1);

        if(BattleFunctions.distance(end, startPos) > pd) return new Path(pd+1);

        // Position lists creation ____________________________________________________________________________________ //
        ArrayList<Point> otherEntitiesPositions = new ArrayList<>();
        ArrayList<Point> entitiesPositions = new ArrayList<>();
        ArrayList<Entity> otherTeamEntities = new ArrayList<>();
        for(Entity e : ClientManager.get().getGameManager().getAliveEntities()) {
            if(!e.getPosition().equals(startPos)) otherEntitiesPositions.add(e.getPosition());
            entitiesPositions.add(e.getPosition());
            if(e.getTeam() != entity.getTeam()) otherTeamEntities.add(e);
        }

        // Tackle matrices _____________________________________________________________________________________________ //
        int[][] tackleTiles = new int[M.length][M[0].length];
        for(int i = 0; i < M.length; i++) for(int j = 0; j < M[0].length; j++) tackleTiles[i][j] = 0;
        for(Entity e : otherTeamEntities) {
            int x = e.getPosition().x;
            int y = e.getPosition().y;

            if(x < M.length - 1) {
                if (in(M[x + 1][y], new int[]{0, 2, 3}) && !in(new Point(x + 1, y), otherEntitiesPositions)) {
                    tackleTiles[x + 1][y] += e.getStats().get("tacle") - entity.getStats().get("fuite");
                    if(tackleTiles[x + 1][y] < 0) tackleTiles[x + 1][y] = 0;
                }
            }
            if(x > 0) {
                if (in(M[x - 1][y], new int[]{0, 2, 3}) && !in(new Point(x - 1, y), otherEntitiesPositions)) {
                    tackleTiles[x - 1][y] += e.getStats().get("tacle") - entity.getStats().get("fuite");
                    if(tackleTiles[x - 1][y] < 0) tackleTiles[x - 1][y] = 0;
                }
            }
            if(y < M[0].length - 1) {
                if (in(M[x][y + 1], new int[]{0, 2, 3}) && !in(new Point(x, y + 1), otherEntitiesPositions)) {
                    tackleTiles[x][y + 1] += e.getStats().get("tacle") - entity.getStats().get("fuite");
                    if(tackleTiles[x][y + 1] < 0) tackleTiles[x][y + 1] = 0;
                }
            }
            if(y > 0) {
                if (in(M[x][y - 1], new int[]{0, 2, 3}) && !in(new Point(x, y - 1), otherEntitiesPositions)) {
                    tackleTiles[x][y - 1] += e.getStats().get("tacle") - entity.getStats().get("fuite");
                    if(tackleTiles[x][y - 1] < 0) tackleTiles[x][y - 1] = 0;
                }
            }
        }


        // Creation of the first Tile __________________________________________________________________________________ //
        Tile start = new Tile(startPos, null);
        start.T += tackleTiles[start.x][start.y]*10;

        // Creation of the lists of Tiles ______________________________________________________________________________ //
            // Open Lists//
        ArrayList<Tile> openList = new ArrayList<>();
        openList.add(start);
        ArrayList<Point> pointOpenList = new ArrayList<>();
        pointOpenList.add(start.getPoint());
            // Close Lists //
        ArrayList<Tile> closeList = new ArrayList<>();
        ArrayList<Point> pointCloseList = new ArrayList<>();

        // Best Path and its possibility //
        Path bestPath = new Path();
        boolean isPossible = false;


        // Algorithm ___________________________________________________________________________________________________ //
        Tile endTile = new Tile(end.x,end.y,(Tile)null);
        while(!openList.isEmpty()) {
            // Choose the Tile with lowest F in the Open list //
            Tile tile = Tile.min(openList);
            Point tilePoint = tile.getPoint();
            int x = tile.x;
            int y = tile.y;

            // If the Tile is already in the closeList : continue //
            if(in(tilePoint,pointCloseList)) {
                openList.remove(tile);
                pointOpenList.remove(tilePoint);
                continue;
            }

            // If the Tile is the end Tile : finish //
            if(end.equals(tilePoint)) {
                endTile = tile;
                isPossible = true;
                break;
            }

            // Locality Tiles //
            ArrayList<Tile> locality = new ArrayList<>();
            if(x+1 < M.length) {
                // If the tile is ground //
                if(in(M[x+1][y], new int[]{0,2,3}) && !entitiesPositions.contains(new Point(x+1,y))) {
                    // If the tile is not already in the Close list //
                    if(!pointCloseList.contains(new Point(x+1,y))) {
                        locality.add(new Tile(x+1,y,tile));
                    }
                }
            }
            if(x-1 >= 0) {
                if(in(M[x-1][y], new int[]{0,2,3}) && !entitiesPositions.contains(new Point(x-1,y))) {
                    if(!pointCloseList.contains(new Point(x-1,y))) {
                        locality.add(new Tile(x-1,y,tile));
                    }
                }
            }
            if(y+1 < M[0].length) {
                if(in(M[x][y+1], new int[]{0,2,3}) && !entitiesPositions.contains(new Point(x,y+1))) {
                    if(!pointCloseList.contains(new Point(x,y+1))) {
                        locality.add(new Tile(x,y+1,tile));
                    }
                }
            }
            if(y-1 >= 0) {
                if(in(M[x][y-1], new int[]{0,2,3}) && !entitiesPositions.contains(new Point(x,y-1))) {
                    if(!pointCloseList.contains(new Point(x,y-1))) {
                        locality.add(new Tile(x,y-1,tile));
                    }
                }
            }

            // Update G, H and F of each locality Tile //
            for(Tile t : locality) {
                t.T += tackleTiles[t.x][t.y]*10;
                t.update(end);
                // Adding the Tile to the open List if it is not already in //
                if(!pointOpenList.contains(t.getPoint())) {
                    openList.add(t);
                    pointOpenList.add(t.getPoint());
                }
            }

            // Withdraw the Tile from the OL to the CL //
            closeList.add(tile);
            pointCloseList.add(tile.getPoint());
            openList.remove(tile);
            pointOpenList.remove(tile.getPoint());
        }

        // If a path has been found ____________________________________________________________________________________ //
        if(isPossible) {
            if(endTile.getParent() != null) {
                // Add tackle cost //
                bestPath.cost += endTile.getParent().T/10;
            }

            // Creating the path list //
            Tile parentTile = endTile.getParent();
            bestPath.add(endTile.getPoint());
            while(parentTile.getParent() != null) {
                bestPath.add(parentTile.getPoint());
                parentTile = parentTile.getParent();
            }
            bestPath.cost += bestPath.size();
        }

        // If cost > pd then return null //
        if(bestPath.cost > pd) {
            bestPath = new Path(bestPath.cost);
        }
        return bestPath;
    }

    public static Scope scopeFinding(Map map, Entity entity, Spell spell) {
        return scopeFinding(map, entity, spell, entity.getPosition());
    }

    public static Scope scopeFinding(Map map, Entity entity, Spell spell, Point startPoint) {
        int x = startPoint.x;
        int y = startPoint.y;
        ArrayList<Entity> entities = ClientManager.get().getGameManager().getAliveEntities();

        Scope scope = new Scope();
        int maxRange = spell.getMaxRange();
        if(spell.isRangeVariable()) {
            maxRange += entity.getStats().get("po");
        }

        ArrayList<Point> entitiesPositions = new ArrayList<>();
        for(Entity e : entities) {
            entitiesPositions.add(e.getPosition());
        }
        // _________________ Seen Scope _______________________________ //
        // Lines //
        addLineSeenScope(map, maxRange, spell, x, y, scope, entitiesPositions);

        // Diagonals //
        addDiagonalSeenScope(map, maxRange, spell, x, y, scope, entitiesPositions);

        // Circles //
        addCircleSeenScope(map, maxRange, spell, x, y, scope, entitiesPositions);

        // Squares //
        addSquareSeenScope(map, maxRange, spell, x, y, scope, entitiesPositions);

        // Removing entities pos if Target type is empty target //
        if(spell.getTargetType() == Spell.EMPTY_TARGET) {
            for(Point pos : entitiesPositions) {
                if(scope.getSeenRange().contains(pos)) {
                    scope.getSeenRange().remove(pos);
                }
            }
        }

        // Removing pos before minimal range //
        for(int i = scope.getSeenRange().size() - 1; i >= 0; i--) {
            if(absDistance(scope.getSeenRange().get(i).x, scope.getSeenRange().get(i).y, x, y) < spell.getMinRange()) {
                scope.getSeenRange().remove(i);
            }
        }

        // ____________________ Unseen Scope __________________________ //
        // Circle //
        if (spell.getCastZone() == Spell.CIRCLE_CAST) {
            for (int i = 0; i < map.getData().length; i++) {
                for (int j = 0; j < map.getData().length; j++) {
                    if (absDistance(i, j, x, y) <= maxRange
                            && absDistance(i, j, x, y) >= spell.getMinRange()) {
                        if (!in(i, j, scope.getSeenRange())) {
                            if (map.isGround(i, j)) {
                                scope.addUnseen(i, j);
                            }
                        }
                    }
                }
            }
        }

        // Sqaure //
        if (spell.getCastZone() == Spell.SQUARE_CAST) {
            for (int i = 0; i < map.getData().length; i++) {
                for (int j = 0; j < map.getData().length; j++) {
                    if (absDistanceSquare(i, j, x, y) <= maxRange
                            && absDistanceSquare(i, j, x, y) >= spell.getMinRange()) {
                        if (!in(i, j, scope.getSeenRange())) {
                            if (map.isGround(i, j)) {
                                scope.addUnseen(i, j);
                            }
                        }
                    }
                }
            }
        }

        // Lines //
        if (spell.getCastZone() == Spell.CROSS_CAST || spell.getCastZone() == Spell.STAR_CAST || spell.getCastZone() == Spell.CIRCLE_CAST) {
            for (int i = -maxRange; i < maxRange + 1; i++) {
                if (x + i >= 0 && y >= 0 && x + i < map.getData().length && y < map.getData()[0].length) {
                    if (!in(x + i, y, scope.getSeenRange())) {
                        if (map.isGround(x + i, y) && Math.abs(i) >= spell.getMinRange()) {
                            scope.addUnseen(x + i, y);
                        }
                    }
                }
                if (x >= 0 && y + i >= 0 && x < map.getData().length && y + i < map.getData()[0].length) {
                    if (!in(x, y + i, scope.getSeenRange())) {
                        if (map.isGround(x, y + i) && Math.abs(i) >= spell.getMinRange()) {
                            scope.addUnseen(x, y + i);
                        }
                    }
                }
            }
        }

        // Diagonals //
        if (spell.getCastZone() == Spell.DIAGONAL_CAST || spell.getCastZone() == Spell.STAR_CAST) {
            for (int i = -maxRange / 2 + 1; i < maxRange / 2 + 1 + 1; i++) {
                if (x + i >= 0 && y + i >= 0 && x + i < map.getData().length && y + i < map.getData()[0].length) {
                    if (!in(x + i, y + i, scope.getSeenRange())) {
                        if (map.isGround(x + i, y + i) && Math.abs(2 * i) > spell.getMinRange() / 2 + 1) {
                            scope.addUnseen(x + i, y + i);
                        }
                    }
                }
                if (x + i >= 0 && y - i >= 0 && x + i < map.getData().length && y - i < map.getData()[0].length) {
                    if (!in(x + i, y - i, scope.getSeenRange())) {
                        if (map.isGround(x + i, y - i) && Math.abs(2 * i) > spell.getMinRange() / 2 + 1) {
                            scope.addUnseen(x + i, y - i);
                        }
                    }
                }
            }
        }

        return scope;
    }

    public static TargetArea getTargetArea(Map map, Entity entity, Spell spell, Point startPoint) {
        int x = startPoint.x;
        int y = startPoint.y;
        ArrayList<Entity> entities = ClientManager.get().getGameManager().getAliveEntities();

        TargetArea targetArea = new TargetArea();

        ArrayList<Point> entitiesPositions = new ArrayList<>();
        for(Entity e : entities) {
            entitiesPositions.add(e.getPosition());
        }

        // Circle //
        if(spell.getTargetZone() == Spell.CIRCLE_ZONE || spell.getTargetZone() == Spell.CIRCLE_ZONE_WITHOUT_CENTER) {
            for(int i = x - spell.getZoneSize(); i < x + spell.getZoneSize() + 1; i++) {
                for(int j = y - spell.getZoneSize(); j < y + spell.getZoneSize() + 1; j++) {
                    if(i >= 0 && j >= 0 && i < map.getData().length && j < map.getData()[0].length) {
                        if(map.isGround(i,j) && absDistance(i,j,x,y) <= spell.getZoneSize()) {
                            if(spell.getTargetZone() != Spell.CIRCLE_ZONE_WITHOUT_CENTER) {
                                targetArea.getAreaPos().add(new Point(i,j));
                            } else if(i != startPoint.x || j != startPoint.y) {
                                targetArea.getAreaPos().add(new Point(i,j));
                            }
                        }
                    }
                }
            }
        }



        // Invert Circle //
        if(spell.getTargetZone() == Spell.INVERT_CIRCLE_ZONE) {
            for(int i = 0; i < map.getData().length; i++) {
                for(int j = 0; j < map.getData()[0].length; j++) {
                    if(map.isGround(i,j) && absDistance(i,j,x,y) > spell.getZoneSize()) {
                        targetArea.getAreaPos().add(new Point(i,j));
                    }
                }
            }
        }

        // Square //
        if(spell.getTargetZone() == Spell.SQUARE_ZONE) {
            for(int i = x - spell.getZoneSize(); i < x + spell.getZoneSize() + 1; i++) {
                for(int j = y - spell.getZoneSize(); j < y + spell.getZoneSize() + 1; j++) {
                    if(i >= 0 && j >= 0 && i < map.getData().length && j < map.getData()[0].length) {
                        if(map.isGround(i,j)) {
                            targetArea.getAreaPos().add(new Point(i,j));
                        }
                    }
                }
            }
        }

        // Cross //
        if(spell.getTargetZone() == Spell.CROSS_ZONE) {
            for(int k : new Integer[]{-1,1}) {
                // x //
                for(int i = 0; i < spell.getZoneSize() + 1; i++) {
                    if(x +i*k >= 0 && y >=0 && x +i*k < map.getData().length && y < map.getData()[0].length) {
                        if(map.isGround(x +i*k, y)) {
                            targetArea.getAreaPos().add(new Point(x +i*k, y));
                        }
                    }
                }
                // y //
                for(int i = 0; i < spell.getZoneSize() + 1; i++) {
                    if(x >= 0 && y +i*k >=0 && x < map.getData().length && y +i*k < map.getData()[0].length) {
                        if(map.isGround(x, y+i*k)) {
                            targetArea.getAreaPos().add(new Point(x, y+i*k));
                        }
                    }
                }
            }
        }

        // Rows //
        if(spell.getTargetZone() == Spell.ROW_ZONE) {
            if(Math.abs(x-entity.getPosition().x) > Math.abs(y-entity.getPosition().y)) {
                for(int k : new Integer[]{-1,1}) {
                    // y //
                    for(int i = 0; i < spell.getZoneSize() + 1; i++) {
                        if(x >= 0 && y +i*k >=0 && x < map.getData().length && y +i*k < map.getData()[0].length) {
                            if(map.isGround(x, y+i*k)) {
                                targetArea.getAreaPos().add(new Point(x, y+i*k));
                            }
                        }
                    }
                }

            } else {
                for(int k : new Integer[]{-1,1}) {
                    // x //
                    for(int i = 0; i < spell.getZoneSize() + 1; i++) {
                        if(x +i*k >= 0 && y >=0 && x +i*k < map.getData().length && y < map.getData()[0].length) {
                            if(map.isGround(x +i*k, y)) {
                                targetArea.getAreaPos().add(new Point(x +i*k, y));
                            }
                        }
                    }
                }
            }
        }

        // Columns //
        if(spell.getTargetZone() == Spell.LINE_ZONE) {
            if(Math.abs(x-entity.getPosition().x) > Math.abs(y-entity.getPosition().y)) {
                int k = (x-entity.getPosition().x)/(Math.abs((x-entity.getPosition().x)));
                // x //
                for(int i = 0; i < spell.getZoneSize(); i++) {
                    if(x +i*k >= 0 && y >=0 && x +i*k < map.getData().length && y < map.getData()[0].length) {
                        if(map.isGround(x +i*k, y)) {
                            targetArea.getAreaPos().add(new Point(x +i*k, y));
                        }
                    }
                }

            } else {
                int k = (y-entity.getPosition().y)/(Math.abs((y-entity.getPosition().y)));
                // y //
                for(int i = 0; i < spell.getZoneSize(); i++) {
                    if(x >= 0 && y +i*k >=0 && x < map.getData().length && y +i*k < map.getData()[0].length) {
                        if(map.isGround(x, y+i*k)) {
                            targetArea.getAreaPos().add(new Point(x, y+i*k));
                        }
                    }
                }
            }
        }

        // Trail //
        if(spell.getTargetZone() == Spell.TRAIL_ZONE) {
            if(Math.abs(x-entity.getPosition().x) > Math.abs(y-entity.getPosition().y)) {
                int trail = Math.abs(x - entity.getPosition().x);
                int k = (x-entity.getPosition().x)/trail;
                for (int i = 0; i < trail; i++) {
                    if(entity.getPosition().x + k * (i + 1) >= 0 && entity.getPosition().y >=0 && entity.getPosition().x + k * (i + 1) < map.getData().length && entity.getPosition().y < map.getData()[0].length) {
                        if(map.isGround(entity.getPosition().x + k * (i + 1), entity.getPosition().y)) {
                            targetArea.getAreaPos().add(new Point(entity.getPosition().x + k * (i + 1), entity.getPosition().y));
                        }
                    }
                }
            } else {
                int trail = Math.abs(y - entity.getPosition().y);
                int k = (y-entity.getPosition().y)/trail;
                for (int i = 0; i < trail; i++) {
                    if(entity.getPosition().x >= 0 && entity.getPosition().y + k * (i + 1) >=0 && entity.getPosition().x < map.getData().length && entity.getPosition().y + k * (i + 1) < map.getData()[0].length) {
                        if(map.isGround(entity.getPosition().x, entity.getPosition().y + k * (i + 1))) {
                            targetArea.getAreaPos().add(new Point(entity.getPosition().x, entity.getPosition().y + k * (i + 1)));
                        }
                    }
                }
            }
        }

        // Set entities in the Area //
        for(Entity e : entities) {
            if(in(e.getPosition(), targetArea.getAreaPos())) {
                targetArea.getEntitiesInArea().add(e);
            }
        }

        return targetArea;
    }

    public static DualList<Point> getTravelPositions(Map map, Entity entity) {
        int x = entity.getPosition().x;
        int y = entity.getPosition().y;
        int pd = entity.getStats().get("pd");

        ArrayList<Entity> entities = ClientManager.get().getGameManager().getAliveEntities();

        DualList<Point> dualTravelList = new DualList<>();

        ArrayList<Point> entitiesPositions = new ArrayList<>();
        for(Entity e : entities) {
            entitiesPositions.add(e.getPosition());
        }

        for(int i = x - pd; i < x + pd + 1; i++) {
            for(int j = y - pd; j < y + pd + 1; j++) {
                if(i >= 0 && j >= 0 && i < map.getData().length && j < map.getData()[0].length) {
                    if(absDistance(i,j,x,y) <= pd) {
                        if (map.isGround(i, j) && !in(i, j, entitiesPositions)) {
                            Path path = pathfinding(map, entity, new Point(i, j), entity.getPosition());
                            if (path.cost > pd) {
                                dualTravelList.getList2().add(new Point(i, j));
                            } else {
                                dualTravelList.getList1().add(new Point(i, j));
                            }
                        }
                    }
                }
            }
        }

        return dualTravelList;
    }


    // Seen Scope Functions _________________________________________________________________________________________________ //

    private static void addCircleSeenScope(Map map, int maxRange,  Spell spell, int x, int y, Scope scope, ArrayList<Point> entitiesPositions) {
        if(spell.getCastZone() == Spell.CIRCLE_CAST) {
            ArrayList<Point> blockingPoints = new ArrayList<>();

            // Adding blocking positions if isSightLine spell //
            if(spell.isSightLine()) {
                for(Point p : entitiesPositions) {
                    if(p.x != x || p.y != y) {
                        blockingPoints.add(p);
                    }
                }
                for(int i = x - maxRange; i < x + maxRange + 1; i++) {
                    for(int j = y - maxRange; j < y + maxRange + 1; j++) {
                        if(i >= 0 && j >= 0 && i < map.getData().length && j < map.getData()[0].length) {
                            if(map.isWall(i,j)) {
                                blockingPoints.add(new Point(i,j));
                            }
                        }
                    }
                }
            }

            // Add angles //
            ArrayList<Double[]> angles = new ArrayList<>();
            for(Point pos : blockingPoints) {
                ArrayList<Double> thetas = new ArrayList<>();
                for(float i : new Float[]{-0.5f,0.5f}) {
                    for (float j : new Float[]{-0.5f, 0.5f}) {
                        double X = pos.x + i - x;
                        double Y = pos.y + j - y;

                        double theta = -Math.atan(Y / X) + Math.PI / 2;

                        if (X < 0) {
                            theta += Math.PI;
                        }

                        thetas.add(theta);
                    }
                }

                double thetaMax = max(thetas);
                double thetaMin = min(thetas);

                // Si on se trouve à la limite 0/2Pi, la zone cachée n'est pas celle entre thetamax et thetamin //
                if(thetaMax - thetaMin > Math.PI) {
                    while (thetas.contains(thetaMax)) {
                        thetas.remove(thetaMax);
                    }
                    while (thetas.contains(thetaMin)) {
                        thetas.remove(thetaMin);
                    }
                    angles.add(new Double[]{min(thetas), max(thetas), (double) (Math.abs(pos.x - x) + Math.abs(pos.y - y))});
                } else {
                    angles.add(new Double[]{thetaMin, thetaMax, (double) absDistance(x, y,pos.x,pos.y)});
                }
            }

            for(int i = x - maxRange; i < x + maxRange + 1; i++) {
                for (int j = y - maxRange; j < y + maxRange + 1; j++) {
                    if (i >= 0 && j >= 0 && i < map.getData().length && j < map.getData()[0].length) {

                        boolean visible = false;
                        double distance = absDistance(i,j, x, y);

                        if(maxRange >= distance && i != x && j != y && map.isGround(i,j)) {
                            double theta = - Math.atan((j - y)/(float)(i - x)) + Math.PI/2;
                            if(i - x < 0) {
                                theta += Math.PI;
                            }
                            visible = true;
                            for(Double[] angle : angles) {
                                if(distance > angle[2]) {
                                    // On gère le cas où il on doit passer de 2Pi à 0 pour la zone cachée //
                                    if(angle[1] - angle[0] >= Math.PI) {
                                        if (theta > angle[1] || theta < angle[0]) {
                                            visible = false;
                                            break;
                                        }
                                    } else if (theta < angle[1] && theta > angle[0]) {
                                        visible = false;
                                        break;
                                    }
                                }
                            }

                        }

                        if (visible) {
                            scope.addSeen(i,j);
                        }
                    }
                }
            }
            // Removing pos before minimal range //
            for(int i = scope.getSeenRange().size() - 1; i >= 0 ; i --) {
                if(absDistance(scope.getSeenRange().get(i).x, scope.getSeenRange().get(i).y, x, y) < spell.getMinRange()) {
                    scope.getSeenRange().remove(i);
                }
            }
        }
    }

    private static void addSquareSeenScope(Map map, int maxRange,  Spell spell, int x, int y, Scope scope, ArrayList<Point> entitiesPositions) {
        if(spell.getCastZone() == Spell.SQUARE_CAST) {
            ArrayList<Point> blockingPoints = new ArrayList<>();

            // Adding blocking positions if isSightLine spell //
            if(spell.isSightLine()) {
                for(Point p : entitiesPositions) {
                    if(p.x != x || p.y != y) {
                        blockingPoints.add(p);
                    }
                }
                for(int i = x - maxRange; i < x + maxRange + 1; i++) {
                    for(int j = y - maxRange; j < y + maxRange + 1; j++) {
                        if(i >= 0 && j >= 0 && i < map.getData().length && j < map.getData()[0].length) {
                            if(map.isWall(i,j)) {
                                blockingPoints.add(new Point(i,j));
                            }
                        }
                    }
                }
            }

            // Add angles //
            ArrayList<Double[]> angles = new ArrayList<>();
            for(Point pos : blockingPoints) {
                ArrayList<Double> thetas = new ArrayList<>();
                for(float i : new Float[]{-0.5f,0.5f}) {
                    for (float j : new Float[]{-0.5f, 0.5f}) {
                        double X = pos.x + i - x;
                        double Y = pos.y + j - y;

                        double theta = -Math.atan(Y / X) + Math.PI / 2;

                        if (X < 0) {
                            theta += Math.PI;
                        }

                        thetas.add(theta);
                    }
                }

                double thetaMax = max(thetas);
                double thetaMin = min(thetas);

                // Si on se trouve à la limite 0/2Pi, la zone cachée n'est pas celle entre thetamax et thetamin //
                if(thetaMax - thetaMin > Math.PI) {
                    while (thetas.contains(thetaMax)) {
                        thetas.remove(thetaMax);
                    }
                    while (thetas.contains(thetaMin)) {
                        thetas.remove(thetaMin);
                    }
                    angles.add(new Double[]{min(thetas), max(thetas), (double) (Math.max(Math.abs(pos.x - x), Math.abs(pos.y - y)))});
                } else {
                    angles.add(new Double[]{thetaMin, thetaMax, (double) absDistanceSquare(x, y,pos.x,pos.y)});
                }
            }

            for(int i = x - maxRange; i < x + maxRange + 1; i++) {
                for (int j = y - maxRange; j < y + maxRange + 1; j++) {
                    if (i >= 0 && j >= 0 && i < map.getData().length && j < map.getData()[0].length) {

                        boolean visible = false;
                        double distance = absDistanceSquare(i,j, x, y);

                        if(maxRange >= distance && i != x && j != y && map.isGround(i,j)) {
                            double theta = - Math.atan((j - y)/(float)(i - x)) + Math.PI/2;
                            if(i - x < 0) {
                                theta += Math.PI;
                            }
                            visible = true;
                            for(Double[] angle : angles) {
                                if(distance > angle[2]) {
                                    // On gère le cas où il on doit passer de 2Pi à 0 pour la zone cachée //
                                    if(angle[1] - angle[0] >= Math.PI) {
                                        if (theta > angle[1] || theta < angle[0]) {
                                            visible = false;
                                            break;
                                        }
                                    } else if (theta < angle[1] && theta > angle[0]) {
                                        visible = false;
                                        break;
                                    }
                                }
                            }

                        }

                        if (visible) {
                            scope.addSeen(i,j);
                        }
                    }
                }
            }
            // Removing pos before minimal range //
            for(int i = scope.getSeenRange().size() - 1; i >= 0 ; i --) {
                if(absDistanceSquare(scope.getSeenRange().get(i).x, scope.getSeenRange().get(i).y, x, y) < spell.getMinRange()) {
                    scope.getSeenRange().remove(i);
                }
            }
        }
    }

    private static void addDiagonalSeenScope(Map map, int maxRange,  Spell spell, int x, int y, Scope scope, ArrayList<Point> entitiesPositions) {
        if(spell.getCastZone() == Spell.DIAGONAL_CAST || spell.getCastZone() == Spell.STAR_CAST) {

            for(int k : new Integer[]{-1,1}) {
                for(int j : new Integer[]{-1,1}) {
                    for (int i = 0; i < maxRange/2+1 + 1; i++) {
                        if (x + i * k >= 0 && y + i * j >= 0 && x + i * k < map.getData().length && y + i * j < map.getData()[0].length) {
                            if (map.isGround(x + i * k, y + i * j)) {
                                scope.addSeen(x + i * k, y + i * j);
                            } else if (spell.isSightLine() && map.isWall(x + i * k, y + i * j)) {
                                break;
                            }
                            if (spell.isSightLine() && in(x + i * k, y + i * j, entitiesPositions) && i != 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void addLineSeenScope(Map map, int maxRange, Spell spell, int x, int y, Scope scope, ArrayList<Point> entitiesPositions) {
        if(spell.getCastZone() == Spell.CROSS_CAST || spell.getCastZone() == Spell.CIRCLE_CAST || spell.getCastZone() == Spell.STAR_CAST || spell.getCastZone() == Spell.SQUARE_CAST) {

            for(int k : new Integer[]{-1,1}) {
                // x //
                for(int i = 0; i < maxRange + 1; i++) {
                    if(x +i*k >= 0 && y >=0 && x +i*k < map.getData().length && y < map.getData()[0].length) {
                        if(map.isGround(x +i*k, y)) {
                            scope.addSeen(x +i*k, y);
                        } else if(spell.isSightLine() && map.isWall(x +i*k, y)) {
                            break;
                        }

                        if(spell.isSightLine() && in(x +i*k, y, entitiesPositions) && i != 0) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                // y //
                for(int i = 0; i < maxRange + 1; i++) {
                    if(x >= 0 && y +i*k >=0 && x < map.getData().length && y +i*k < map.getData()[0].length) {
                        if(map.isGround(x, y+i*k)) {
                            scope.addSeen(x, y +i*k);
                        } else if(spell.isSightLine() && map.isWall(x, y +i*k)) {
                            break;
                        }
                        if(spell.isSightLine() && in(x, y +i*k, entitiesPositions) && i != 0) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }

        }
    }

    // Utils Functions _________________________________________________________________________________________________ //
    public static boolean in(int val, int[] container) {
        for(int i : container) {
            if(i==val) {
                return true;
            }
        }
        return false;
    }
    public static boolean in(Point val, ArrayList<Point> container) {
        for(Point i : container) {
            if(i.equals(val)) {
                return true;
            }
        }
        return false;
    }

    public static boolean in(int x, int y, ArrayList<Point> container) {
        for(Point i : container) {
            if(i.x == x && i.y == y) {
                return true;
            }
        }
        return false;
    }

    public static double max(ArrayList<Double> list) {
        if(list.isEmpty()) {
            return 0;
        }
        double max = list.get(0);
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) >= max) {
                max = list.get(i);
            }
        }
        return max;
    }

    public static double min(ArrayList<Double> list) {
        if(list.isEmpty()) {
            return 0;
        }
        double min = list.get(0);
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i) <= min) {
                min = list.get(i);
            }
        }
        return min;
    }

    public static int absDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    public static int absDistanceSquare(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

}
package fr.suward.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.suward.assets.Assets;
import fr.suward.display.popups.DamageDisplay;
import fr.suward.display.popups.PreviewPopup;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.classes.dolma.MiseAuCube;
import fr.suward.game.entities.spells.classes.erit.Ciblage;
import fr.suward.game.entities.spells.pdcity.mdc.Fermentation;
import fr.suward.game.entities.spells.pdcity.pasdoeuf.DepressionPartagee;
import fr.suward.game.pathfinding.*;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class MapIso {

    private static final int ABSOLUTE_VOID = -2;
    private static final int VOID = -1;
    private static final int GROUND = 0;
    private static final int WALL = 1;
    private static final int BLUE_POS = 2;
    private static final int RED_POS = 3;


    private Map map;

    private boolean isZoomed = false;
    private Point zoomPoint = new Point(330,400);

    private int centeredX;
    private int centeredY;
    private int centeredXZoomed;
    private int centeredYZoomed;

    public MapIso() {
        map = new Map();
    }

    public MapIso(String mapName) {
        map = new Map(mapName);
    }


    public void setMap(Map map) {
        this.map = map;
    }

    public void render(SpriteBatch graphics) {
        Point mouseIsoLoc = getMouseIsoLoc();

        ClientManager clientManager = ClientManager.get();
        ArrayList<Entity> entities = clientManager.getGameManager().getAliveEntities();

        ArrayList<Point> entitiesPositions = new ArrayList<>();
        for(Entity e : entities) {
            entitiesPositions.add(e.getPosition());
            e.setPopup(false);
        }

        ArrayList<Point> damageDisplayPositions = new ArrayList<>();
        for(DamageDisplay d : ClientManager.get().getDamageToRender()) {
            damageDisplayPositions.add(d.getPoint());
        }

        // Areas Display _______________________________________________________________________________________________ //

            // Scope //
        Scope scope = new Scope();
        TargetArea targetArea = new TargetArea();
        if(clientManager.getGameManager().isBattleStarted() && clientManager.getSpellLock() != null) {
            scope = Pathfinding.scopeFinding(map, clientManager.getCurrentClientCharacterOrItsSummon(), clientManager.getSpellLock());
            if(scope.getSeenRange().contains(mouseIsoLoc)) {
                targetArea = Pathfinding.getTargetArea(map, clientManager.getCurrentClientCharacterOrItsSummon(), ClientManager.get().getSpellLock(), mouseIsoLoc);
                for(Entity target : entities) {
                    if(targetArea.getEntitiesInArea().contains(target)) {
                        target.setPopup(true);
                        if (target.getPreviewPopup() == null) {
                            target.setPreviewPopup(new PreviewPopup(clientManager.getSpellLock(), clientManager.getCurrentClientCharacterOrItsSummon(), target, mouseIsoLoc));
                        } else if (target.getPreviewPopup().isDifferent(clientManager.getSpellLock(), clientManager.getCurrentClientCharacterOrItsSummon(), mouseIsoLoc)) {
                            target.setPreviewPopup(new PreviewPopup(clientManager.getSpellLock(), clientManager.getCurrentClientCharacterOrItsSummon(), target, mouseIsoLoc));
                        }
                    } else {
                        target.setPreviewPopup(null);
                        target.setPopup(false);
                    }
                }
            }
        }

        // Pop up //
        for(Entity e : entities) {
            if(!e.isPopup()) {
                if(e.getPosition().x == mouseIsoLoc.x && e.getPosition().y == mouseIsoLoc.y) {
                    if (e.getPreviewPopup() == null) {
                        e.setPreviewPopup(new PreviewPopup(e));
                    } else if (e.getPreviewPopup().isDifferent(e)) {
                        e.setPreviewPopup(new PreviewPopup(e));
                    }
                } else {
                    e.setPreviewPopup(null);
                }
            }
        }



            // Path //
        Path path = new Path();
        DualList<Point> dualTravelList = new DualList<>();
        if(ClientManager.get().getSpellLock() == null) {
            if(ClientManager.get().getGameManager().isBattleStarted() && clientManager.isCurrentCharacterTurn()) {
                path = Pathfinding.pathfinding(map, clientManager.getCurrentClientCharacterOrItsSummon(), mouseIsoLoc, clientManager.getCurrentClientCharacterOrItsSummon().getPosition());
                dualTravelList = Pathfinding.getTravelPositions(map, clientManager.getCurrentClientCharacterOrItsSummon());
            }
        }

            // Set Position if clicked
        clickProcess(mouseIsoLoc, clientManager, entitiesPositions, path, scope, targetArea);

        // Tiles Display _______________________________________________________________________________________________ //
        for(int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                centeredX = xFromIso(i,j);
                if(i == mouseIsoLoc.x && j == mouseIsoLoc.y && entitiesPositions.contains(mouseIsoLoc)){
                    centeredY = yFromIso(i,j) - 6;
                    for(int k = 0; k < scope.getSeenRange().size(); k++) {

                    }
                } else {
                    centeredY = yFromIso(i,j);
                }
                centeredXZoomed = xFromIsoZoomed(i,j);
                centeredYZoomed = yFromIsoZoomed(i,j);



                // Skip Tiles out of the screen //
                //if(isZoomed && (centeredXZoomed < -258 || centeredXZoomed > 1280 || centeredYZoomed < -128 || centeredYZoomed > 840)) continue;

                // Tiles //
                displayTile(i, j, graphics, path, scope, targetArea, dualTravelList);

                // Players //
                displayPlayer(i, j, graphics, entitiesPositions, entities);


            }
        }
    }

    private void clickProcess(Point mouseIsoLoc, ClientManager clientManager, ArrayList<Point> entitiesPositions, Path path, Scope scope, TargetArea targetArea) {
        if(Gdx.input.isButtonJustPressed(0)) {
            if(!isOutOfMapBounds(mouseIsoLoc)) {
                if(ClientManager.get().getGameManager().isBattleStarted()) {
                    if(ClientManager.get().isCurrentCharacterTurn()) {
                        if(scope.getSeenRange().contains(mouseIsoLoc) && !ClientManager.get().getSpellLock().isDisabled()) {
                            ClientManager.get().getSpellLock().use(clientManager.getCurrentClientCharacterOrItsSummon(), targetArea.getEntitiesInArea(), entitiesPositions, targetArea.getAreaPos(), mouseIsoLoc);
                        } else if(path.contains(mouseIsoLoc)) {
                            clientManager.moveCharacter(mouseIsoLoc, path);
                        }
                    }
                    ClientManager.get().setSpellLock(null);
                } else {
                    if(!clientManager.getCurrentClientCharacterOrItsSummon().isReady()) {
                        if((map.get(mouseIsoLoc) == BLUE_POS || map.get(mouseIsoLoc) == RED_POS ) && !entitiesPositions.contains(mouseIsoLoc)) {
                            if(map.get(mouseIsoLoc) == BLUE_POS) {
                                ClientManager.get().getGameManager().setTeam(1, clientManager.getCurrentClientCharacterOrItsSummon());
                            } else {
                                ClientManager.get().getGameManager().setTeam(2, clientManager.getCurrentClientCharacterOrItsSummon());
                            }
                            clientManager.moveCharacter(mouseIsoLoc);
                        }
                    }

                }
            } else {
                ClientManager.get().setSpellLock(null);
            }
        }

    }


    private Point getMouseIsoLoc() {
        /*
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        Point panelLoc = new Point(Gdx.input.getX(), Gdx.input.getY());
        if(isZoomed) {
            return pointToIsoZoomed(new Point(mouseLoc.x - panelLoc.x, mouseLoc.y - panelLoc.y));
        } else {
            return pointToIso(new Point(mouseLoc.x - panelLoc.x, mouseLoc.y - panelLoc.y));
        }
         */
        if(isZoomed) {
            return pointToIsoZoomed(new Point(Gdx.input.getX(), Gdx.input.getY()));
        } else {
            return pointToIso(new Point(Gdx.input.getX(), Gdx.input.getY()));
        }

    }

    private void displayPlayer(int i, int j, SpriteBatch graphics, ArrayList<Point> entitiesPositions, ArrayList<Entity> entities) {
        Point p = new Point(i,j);
        if(entitiesPositions.contains(p)) {
            for(Entity e : entities) {
                if(e.getPosition().equals(p)) {
                    if(e.getTeam() == 1) {
                        if(isZoomed) {
                            graphics.draw(Assets.TILES.get("blueCircle128"),centeredXZoomed, centeredYZoomed);
                        } else {
                            graphics.draw(Assets.TILES.get("blueCircle64"),centeredX, centeredY);
                        }
                    } else {
                        if(isZoomed) {
                            graphics.draw(Assets.TILES.get("redCircle128"),centeredXZoomed, centeredYZoomed);
                        } else {
                            graphics.draw(Assets.TILES.get("redCircle64"),centeredX, centeredY);
                        }
                    }

                    if(isZoomed) {
                        graphics.draw(e.getClassData().getImage(128),centeredXZoomed, centeredYZoomed+20+32);
                    } else {
                        graphics.draw(e.getClassData().getTexture(),centeredX, centeredY+10+16,64, 64);
                    }

                    try {
                        displayStatusIcons(graphics, e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    break;
                }
            }
        }
    }

    private void displayStatusIcons(SpriteBatch graphics, Entity e) {
        ArrayList<String> effectNames = new ArrayList<>();
        for(Effect ef : e.getStats().getEffects()) {
            if(ef.getName() == Ciblage.effectName && !effectNames.contains("cible")) {
                effectNames.add("cible");
            } else if(ef.getName() == DepressionPartagee.effectName && !effectNames.contains("depressif")) {
                effectNames.add("depressif");
            } else if(ef.getName() == Fermentation.effectName && !effectNames.contains("1")) {
                effectNames.add("1");
            } else if(ef.getName() == MiseAuCube.effectName1 && !effectNames.contains("1r")) {
                effectNames.add("1r");
            } else if(ef.getName() == MiseAuCube.effectName2 && !effectNames.contains("2r")) {
                effectNames.add("2r");
            } else {
                for(Boost b : ef.getBoosts()) {
                    if(b.getStatusID() == Effect.INCURABLE && !effectNames.contains("insoignable"))
                        effectNames.add("insoignable");
                    if(b.getStatusID() == Effect.ROOTED && !effectNames.contains("enracine"))
                        effectNames.add("enracine");
                    if(b.getStatusID() == Effect.HEAVY && !effectNames.contains("lourd"))
                        effectNames.add("lourd");
                    if(b.getStatusID() == Effect.INVULNERABLE && !effectNames.contains("invu"))
                        effectNames.add("invu");
                    if(b.getStatusID() == Effect.DISTANCE_INVULNERABLE && !effectNames.contains("invuDist"))
                        effectNames.add("invuDist");
                    if(b.getStatusID() == Effect.MELEE_INVULNERABLE && !effectNames.contains("invuMel"))
                        effectNames.add("invuMel");
                    if(b.getStatusID() == Effect.GRAVITY && !effectNames.contains("pesanteur"))
                        effectNames.add("pesanteur");
                }
            }

        }
        if(effectNames.size() == 2) {
            graphics.draw(Assets.TEXTURES.get(effectNames.get(0)),centeredX-4, centeredY+10+16 + 72,32, 32);
            graphics.draw(Assets.TEXTURES.get(effectNames.get(1)),centeredX+4, centeredY+10+16 + 72,32, 32);
        } else {
            for(int k = 0; k < effectNames.size(); k ++) {
                int l = 0;
                if(k >= 3) {
                    l = 1;
                }
                if(k == 0 || k == 3) {
                    graphics.draw(Assets.TEXTURES.get(effectNames.get(k)),centeredX+16, centeredY+10+16 + 72 +40*l,32, 32);
                } else if(k == 1 || k == 4) {
                    graphics.draw(Assets.TEXTURES.get(effectNames.get(k)),centeredX + 16 - 40, centeredY+10+16 + 72 +40*l,32, 32);
                } else if(k == 2 || k == 5) {
                    graphics.draw(Assets.TEXTURES.get(effectNames.get(k)),centeredX + 16 + 40, centeredY+10+16 + 72 +40*l,32, 32);
                }
            }
        }

    }


    private void displayTile(int i, int j, SpriteBatch graphics, Path path, Scope scope, TargetArea targetArea, DualList<Point> dualTravelList) {
        Texture tile = null;
        Texture area = null;
        Texture glyph = null;

        String str;
        if(isZoomed) {
            str = "128";
        } else {
            str = "64";
        }

        if(map.get(i,j) == GROUND || map.get(i,j) == BLUE_POS || map.get(i,j) == RED_POS) {
            Point pos = new Point(i,j);
            if (targetArea.getAreaPos().contains(pos)) {
                area = Assets.TILES.get("redGround"+str);
            } else if(scope.getSeenRange().contains(pos)) {
                area = Assets.TILES.get("blueGround"+str);
            } else if(scope.getUnseenRange().contains(pos)) {
                area = Assets.TILES.get("lightBlueGround"+str);
            } else if(path.contains(i,j)) {
                area = Assets.TILES.get("path"+str);
            } else if(dualTravelList.getList1().contains(pos)) {
                area = Assets.TILES.get("pdArea"+str);
            } else if(dualTravelList.getList2().contains(pos)) {
                area = Assets.TILES.get("noPdArea"+str);
            }
            if(isEven(i+j)) {
                tile = Assets.TILES.get("evenGroundB"+str);
            } else {
                tile = Assets.TILES.get("oddGroundB"+str);
            }
            // Glyphs //
            // Walls //
        } else if(map.get(i,j) == WALL) {
            tile = Assets.TILES.get("wallB"+str);
        }

        if(tile != null && (map.get(i,j) != VOID || map.get(i,j) != ABSOLUTE_VOID)) {
            if(isZoomed) {
                graphics.draw(tile, centeredXZoomed, centeredYZoomed);
            } else {
                graphics.draw(tile, centeredX, centeredY);
            }
            if(area != null) {
                if(isZoomed) {
                    graphics.draw(area,centeredXZoomed, centeredYZoomed);
                } else {
                    graphics.draw(area,centeredX, centeredY);
                }
            }

            if(!ClientManager.get().getGameManager().isBattleStarted() && map.get(i,j) == BLUE_POS) {
                tile = Assets.TILES.get("blueGround" + str);
                if (isZoomed) {
                    graphics.draw(tile, centeredXZoomed, centeredYZoomed);
                } else {
                    graphics.draw(tile, centeredX, centeredY);
                }
            } else if(!ClientManager.get().getGameManager().isBattleStarted() && map.get(i,j) == RED_POS) {
                tile = Assets.TILES.get("redGround" + str);
                if (isZoomed) {
                    graphics.draw(tile, centeredXZoomed, centeredYZoomed);
                } else {
                    graphics.draw(tile, centeredX, centeredY);
                }
            }

            if(glyph != null) {
                if(isZoomed) {
                    graphics.draw(glyph,centeredXZoomed, centeredYZoomed);
                } else {
                    graphics.draw(glyph,centeredX, centeredY);
                }

            }
        } else {


        }
    }


    public static boolean isEven(int n) {
        return (n/2f - n/2 == 0);
    }

    private static final int xOffset = 900 + 60;
    private static final int yOffset = 1160;
    private static final int yOrigin = 990 - 60; //! (config.height - 60) : because graphic origin is bottom left, and mouse origin is top left

    // Corrections en x et y
    private static final int xT = 30;
    private static final int yT = 12;

    public static int xFromIso(int i, int j) {
        return xOffset + 32 * (i - j);
    }

    public static int yFromIso(int i, int j) {
        return yOffset - 16 * (i+j);
    }

    public static Point pointToIso(int x, int y) {
        y = yOrigin - y;
        x-=xT;
        y+=yT;
        int i = (int)(1/64f * ((x - xOffset) - 2 * (y - yOffset)));
        int j = (int)(1/64f * (-(x - xOffset) - 2 * (y - yOffset)));
        return new Point(i,j);
    }

    public static Point pointToIso(Point p) {
        return pointToIso(p.x, p.y);
    }

    private int xFromIsoZoomed(int i, int j) {
        return 2* xFromIso(i,j) - zoomPoint.x;
    }

    private int yFromIsoZoomed(int i, int j) {
        return 2* yFromIso(i,j) - zoomPoint.y;
    }

    private Point pointToIsoZoomed(int x, int y) {
        int i = (int)(1/64f*((x + zoomPoint.x - 64)/2f-640-240) + 1/32f*((y + zoomPoint.y - 32)/2f+284));
        int j = (int)((1/32f)*((y + zoomPoint.y - 32)/2f+284) - 1/64f*((x + zoomPoint.x - 64)/2f-640));

        return new Point(i,j);

    }

    private Point pointToIsoZoomed(Point p) {
        return pointToIsoZoomed(p.x, p.y);
    }

    public boolean isZoomed() {
        return isZoomed;
    }

    public void setZoomed(boolean zoomed) {
        isZoomed = zoomed;
    }

    public void setZoomPoint(Point zoomPoint) {
        this.zoomPoint = zoomPoint;
    }

    public Map getMap() {
        return map;
    }

    public String getMapName() {
        return map.getMapName();
    }

    public boolean isOutOfMapBounds(Point point) {
        return (point.x >= map.getData().length || point.x < 0 || point.y < 0 || point.y >= map.getData()[0].length);
    }

}

package fr.suward.display.states.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import fr.suward.assets.FontManager;
import fr.suward.display.popups.DamageDisplay;
import fr.suward.display.interfaces.*;
import fr.suward.display.components.STextButton;
import fr.suward.managers.GameManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.map.MapIso;
import fr.suward.managers.ClientManager;
import fr.suward.managers.StageManager;

import java.util.ArrayList;


public class BattleState extends State implements InputProcessor {

    private SpriteBatch batch;

    private Table bottomTable;

    private ChatInterface chatInterface;
    private MiddleInterface middleInterface;
    private RightInterface rightInterface;
    private TimelineInterface timelineInterface;
    private SpellInterface spellInterface;

    private Label mapName;

    private float leftTurnTime = -10;

    public BattleState() {
        super();
        create();
    }

    private void create () {
        batch = new SpriteBatch();
        mainTable.setLayoutEnabled(false);

        createBottomTable();
        chatInterface = new ChatInterface(bottomTable);
        spellInterface = new SpellInterface(bottomTable);
        rightInterface = new RightInterface(bottomTable, skin);
        middleInterface = new MiddleInterface(bottomTable);

        // Top Table //
        Table topTable = new Table();
        topTable.align(Align.topLeft);
        topTable.setHeight(100);
        topTable.setWidth(1900);
        topTable.setY(990-100);
        mainTable.add(topTable);

        mapName = new Label("[" + ClientManager.get().getMap().getMapName() + "]" , new Label.LabelStyle(FontManager.firaFont(24), Color.WHITE));
        topTable.add(mapName).padLeft(20).padTop(20);

    }


    @Override
    public void draw() {
        super.draw();
        float dt = Gdx.graphics.getDeltaTime();

        update();

        batch.begin();
        if(ClientManager.get().getMapIso() != null) {
            if(!ClientManager.get().getMap().isMapDataNull()) {
                ClientManager.get().getMapIso().render(batch);
                displayDamage(batch, dt);
                spellInterface.render(batch);

                for(Entity e : ClientManager.get().getAliveEntities()) {
                    if(e.getPreviewPopup() != null) {
                        e.getPreviewPopup().render(batch);
                    }
                }

            }
        }

        //batch.setProjectionMatrix(this.getCamera().combined);
        chatInterface.receiveSentMessage();
        middleInterface.update();

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        super.dispose();
    }

    public void changeMapName() {
        this.mapName.setText("[" + ClientManager.get().getMap().getMapName() + "]");
    }

    private void update() {
        GameManager gm = ClientManager.get().getGameManager();

        if(gm.isBattleStarted()) {
            if(gm.isCurrentCharacterTurn()) {
                rightInterface.getButton().setDisabled(false);
            } else {
                rightInterface.getButton().setDisabled(true);
            }

            if(leftTurnTime > 0) {
                leftTurnTime -= Gdx.graphics.getDeltaTime();
                rightInterface.getButton().setText("Passer (" + (int)(leftTurnTime + 1) + ")");
            } else if(leftTurnTime <= 0 && leftTurnTime != -10) {
                if(gm.isCurrentCharacterTurn()) {
                    gm.passTurn();
                    rightInterface.getButton().setText("Passer");
                }
            }
        } else if(gm.isBattleEnded()) {
            StageManager.get().nextStage(new WorldState(), true);
        }
    }

    public void newTimeLineInterface() {
        //timelineInterface = new TimelineInterface(bottomTable);
    }

    private void displayDamage(SpriteBatch batch, float dt) {
        ArrayList<DamageDisplay> damageToRender = ClientManager.get().getDamageToRender();
        boolean wontOverDisplay = true;
        for(int k = damageToRender.size() - 1; k >= 0; k--) {
            if(damageToRender.size() <= 0) break;
            DamageDisplay d = damageToRender.get(k);
            if(wontOverDisplay) {
                if(d.getLifeTime()/DamageDisplay.duration > 0.1f) {
                    wontOverDisplay = false;
                }
                d.render(batch, dt);
            }
        }
    }


    //_________________ Extracted Methods ________________________________//
    private Table createBottomTable() {
        bottomTable = new Table();

        bottomTable.setLayoutEnabled(false);
        //bottomTable.setBackground(textureRegionDrawableBg);
        mainTable.add(bottomTable);
        bottomTable.setHeight(990);
        bottomTable.setWidth(1900);
        bottomTable.setOriginY(0);
        //bgPixmap.dispose();
        return bottomTable;
    }

    private STextButton addTextButton(String text, Color color) {
        STextButton b = new STextButton(text, FontManager.robotoFont(32), color, skin);
        mainTable.add(b).padTop(30).width(420).height(80);
        mainTable.row();
        return b;
    }

    public ChatInterface getChatInterface() {
        return chatInterface;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        /*
        /map.setZoomed(amountY < 0);
        OrthographicCamera cam = ((OrthographicCamera)this.getCamera());
        if(amountY > 0) {
            if(cam.zoom < 1.8f) {
                cam.zoom += 0.4f;
                if(cam.zoom == 1.4f) {
                    cam.position.x = 1740/2;
                    cam.position.y = 890/2;
                } else {
                    cam.translate(1740/2 - Gdx.input.getX(), 890/2 - Gdx.input.getY());
                }
            }

        } else {
            if(cam.zoom > 1f) {
                cam.zoom -= 0.4f;
                if(cam.zoom == 1.4f) {
                    cam.position.x = 1740/2;
                    cam.position.y = 890/2;
                } else {
                    cam.translate(1740/2 - Gdx.input.getX(), 890/2 - Gdx.input.getY());
                }
            }
        }

         */
        return ClientManager.get().getMapIso().isZoomed();
    }

    @Override
    public boolean keyDown(int keycode) {
        chatInterface.keyDown(keycode);

        if (keycode == Input.Keys.F1) {
            rightInterface.passTurnPressed(ClientManager.get().getGameManager());
        }
        if(ClientManager.get().godMod) {
            if (keycode == Input.Keys.Q) {;
                ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().add("pc",10);
            } else if (keycode == Input.Keys.D) {
                ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().add("pm",10);
            } else if (keycode == Input.Keys.S) {
                ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().add("pd",5);
            }
        }
        for(int i = 0; i < ClientManager.get().getCurrentClientCharacterOrItsSummon().getClassData().getSpells().size(); i++) {
            if(keycode == 8+i) {
                ClientManager.get().setSpellLock(ClientManager.get().getCurrentClientCharacterOrItsSummon().getClassData().getSpells().get(i));
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    public RightInterface getRightInterface() {
        return rightInterface;
    }

    public TimelineInterface getTimelineInterface() {
        return timelineInterface;
    }

    public void setLeftTurnTime(float leftTurnTime) {
        this.leftTurnTime = leftTurnTime;
    }

    public SpellInterface getSpellInterface() {
        return spellInterface;
    }

    public Table getBottomTable() {
        return bottomTable;
    }
}

package fr.suward.display.states.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.suward.Constants;
import fr.suward.assets.FontManager;
import fr.suward.display.components.STextButton;
import fr.suward.display.interfaces.*;
import fr.suward.display.popups.DamageDisplay;
import fr.suward.game.entities.Entity;
import fr.suward.game.map.MapIso;
import fr.suward.managers.ClientManager;
import fr.suward.managers.GameManager;
import fr.suward.managers.StageManager;
import fr.suward.network.packets.ChangePosHubPacket;
import fr.suward.network.packets.ConnectionPacket;
import fr.suward.network.packets.StartBattleStatePacket;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;


public class WorldState extends State implements InputProcessor {

    private SpriteBatch batch;

    private Table bottomTable;

    private ChatInterface chatInterface;
    private MiddleInterface middleInterface;
    private RightInterface rightInterface;
    private TimelineInterface timelineInterface;
    private SpellInterface spellInterface;

    private boolean isBattleLaunched = false;


    public WorldState() {
        super();
        create();
    }

    private void create () {
        batch = new SpriteBatch();
        mainTable.setLayoutEnabled(false);
        ClientManager.get().getAccount().getCharacters().get(0).setPosition(950-32, 600);
        ClientManager.get().getClient().sendTCP(new ChangePosHubPacket(ClientManager.get().getAccount().getCharacters().get(0).getPosition().x, ClientManager.get().getAccount().getCharacters().get(0).getPosition().y));

        createBottomTable();
        chatInterface = new ChatInterface(bottomTable);
        //spellInterface = new SpellInterface(bottomTable);
        rightInterface = new RightInterface(bottomTable, skin);
        //middleInterface = new MiddleInterface(bottomTable);


        mainTable.setBackground(new TextureRegionDrawable(new Texture("img/hubBG.png")));
        rightInterface.getButton().setText("Lancement");

    }


    @Override
    public void draw() {
        super.draw();
        float dt = Gdx.graphics.getDeltaTime();

        update();

        batch.begin();
        ArrayList<Entity> toDrawSorted = new ArrayList<>();
        for(Entity e : ClientManager.get().getAliveEntities()) {
            toDrawSorted.add(e);
        }
        for (int i = 0; i < toDrawSorted.size() - 1; i++) {
            for (int j = 0; j < toDrawSorted.size() - i - 1; j++) {
                if (toDrawSorted.get(j).getPosition().getY() > toDrawSorted.get(j + 1).getPosition().getY()) {
                    Entity temp = toDrawSorted.get(j);
                    toDrawSorted.set(j, toDrawSorted.get(j + 1));
                    toDrawSorted.set(j + 1, temp);
                }
            }
        }
        for(Entity e : toDrawSorted) {
            batch.draw(e.getClassData().getTexture(), e.getPosition().x, e.getPosition().y, 64, 64);
        }

        //spellInterface.render(batch);
        chatInterface.receiveSentMessage();
        //middleInterface.update();

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        super.dispose();
    }

    private void update() {
        if(isBattleLaunched) {
            ClientManager.get().getGameManager().setBattleEnded(false);
            StageManager.get().nextStage(new BattleState(), true);
        }
    }


    private void displayDamage(SpriteBatch batch, float dt) {
        ArrayList<DamageDisplay> damageToRender = ClientManager.get().getDamageToRender();
        for(int k = damageToRender.size() - 1; k >= 0; k--) {
            boolean wontOverDisplay = true;
            DamageDisplay d = damageToRender.get(k);
            if(wontOverDisplay) {
                if(d.getLifeTime()/DamageDisplay.duration > 0.75f) {
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


        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            if(screenY < 990 - 200) {
                ClientManager.get().getClient().sendTCP(new ChangePosHubPacket(screenX-32,990 - screenY - 12));
            }
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    public RightInterface getRightInterface() {
        return rightInterface;
    }

    public TimelineInterface getTimelineInterface() {
        return timelineInterface;
    }


    public SpellInterface getSpellInterface() {
        return spellInterface;
    }

    public Table getBottomTable() {
        return bottomTable;
    }

    public void setBattleLaunched(boolean battleLaunched) {
        isBattleLaunched = battleLaunched;
    }
}

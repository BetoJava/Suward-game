package fr.suward.display.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.suward.assets.FontManager;
import fr.suward.game.map.MapIso;
import fr.suward.managers.ClientManager;

import java.awt.*;

public class DamageDisplay {

    public static final float duration = 1.3f;
    public static final int pathLength = 42;
    private BitmapFont font;

    private int value;
    private String moreOrLess = "";
    private Point pos;

    private Color color;
    private float lifeTime = duration;


    public DamageDisplay(int value, Point pos, Color color) {
        init(value, pos, color);
    }

    public DamageDisplay(int value, Point pos) {
        init(value, pos, getColor(value));
    }

    private void init(int value, Point pos, Color color) {
        this.value = value;
        font = FontManager.firaFonts.get(28);
        if(value > 0) {
            moreOrLess = "+";
        }
        this.color = color;
        this.pos = new Point(MapIso.xFromIso(pos.x, pos.y), MapIso.yFromIso(pos.x, pos.y));
        font.setColor(color);
        if(value == 0) {
            lifeTime = 0;
        }
    }

    public void render(SpriteBatch batch, float dt) {
        updateColor();
        float dy = (duration - lifeTime)/duration * pathLength;

        font.draw(batch, (moreOrLess + value), pos.x + 10,pos.y + dy + 96);

        lifeTime -= dt;
        if(lifeTime < 0) {
            ClientManager.get().getDamageToRender().remove(this);
        }
    }

    private Color getColor(int value) {
        if(value < 0) {
            return FontManager.flashyRed;
        } else {
            return FontManager.green2;
        }
    }

    private void updateColor() {
        font.setColor(getColor(value));
    }

    public Point getPoint() {
        return pos;
    }

    public float getLifeTime() {
        return lifeTime;
    }
}

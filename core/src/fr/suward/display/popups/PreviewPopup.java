package fr.suward.display.popups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import fr.suward.assets.Assets;
import fr.suward.assets.FontManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.entities.spells.classes.erit.Ciblage;
import fr.suward.game.entities.spells.classes.erit.CoupDeGrace;
import fr.suward.game.map.MapIso;

import java.awt.*;
import java.util.ArrayList;

public class PreviewPopup {

    private String entityLine;
    private ArrayList<String> damageStringLines = new ArrayList<>();

    private Spell spell;
    private Entity caster;
    private Entity target;
    private Point castPosition;

    private ArrayList<String> column1 = new ArrayList<>();
    private ArrayList<String> column2 = new ArrayList<>();
    private ArrayList<String> column3 = new ArrayList<>();
    private Sprite interfaceSprite;

    private static int xOrigin = 1045;
    private static int yOrigin = 198;
    private int x;
    private int y;
    private float width;
    private float height = 0;

    private int pad = 5;
    private int textPad = 5;

    private static BitmapFont font16 = FontManager.firaFonts.get(16);
    private float font16Height = 16;
    private Color damageColor;


    public PreviewPopup(Spell spell, Entity caster, Entity target, Point castPosition) {
        this.spell = spell;
        this.caster = caster;
        this.target = target;
        this.castPosition = castPosition;
        createPopup();
    }

    public PreviewPopup(Entity target) {
        this.target = target;
        castPosition = target.getPosition();
        createPopup();
        createEntityInfoPopup(target);
    }

    public void render(SpriteBatch batch) {

        batch.draw(Assets.TEXTURES.get("spellPopupBg"), x + width * 0.7f - width, y + 96, width, height);
        font16.setColor(Color.WHITE);
        font16.draw(batch, entityLine, x + width * 0.7f - width  + pad, y + 96 + height - 2 * pad - 1, width - 2 * pad, Align.center, false);
        for(String line : damageStringLines) {
            font16.setColor(damageColor);
            font16.draw(batch, line, x + width * 0.7f - width + textPad  + pad , y + 96 + height - 2 * font16Height - textPad + 4, width - 4 * pad, Align.center, false);
        }

        if(interfaceSprite != null) {
            interfaceSprite.draw(batch);
            font16.setColor(Color.WHITE);
            font16.draw(batch, target.getPseudo(), xOrigin + 35 + textPad + pad, yOrigin + height - 2 * font16Height - textPad - 6, width - 4 * pad, Align.left, false);
            int i = 0;
            for(String line : column1) {
                if(i == 1) {
                    font16.setColor(new Color(220/255f, 142/255f, 96/255f,1));
                } else if(i == 2) {
                    font16.setColor(new Color(88/255f, 176/255f, 155/255f,1));
                } else if(i == 3) {
                    font16.setColor(new Color(72/255f, 115/255f, 187/255f,1));
                } else {
                    font16.setColor(Color.WHITE);
                }
                i+=1;
                font16.draw(batch, line, xOrigin + 35  + textPad  + pad , yOrigin - 28 + height - (2+i) * font16Height - i * textPad + 4, width - (4+i) * pad, Align.left, false);
            }
            i = 0;
            font16.setColor(FontManager.flashyGreen);
            for(String line : column2) {
                if(i == 4) {
                    font16.setColor(Color.WHITE);
                }
                i+=1;
                font16.draw(batch, line, xOrigin + 35 + 149 + textPad  + pad , yOrigin - 28 + height - (2+i) * font16Height - i * textPad + 4, width - (4+i) * pad, Align.left, false);
            }
            i = 0;
            for(String line : column3) {
                if(i == 0) {
                    font16.setColor(new Color(72/255f, 115/255f, 187/255f,1));
                } else if(i == 1) {
                    font16.setColor(new Color(220/255f, 142/255f, 96/255f,1));
                } else if(i == 2) {
                    font16.setColor(new Color(88/255f, 176/255f, 155/255f,1));
                } else if(i == 3) {
                    font16.setColor(new Color(230/255f, 217/255f, 65/255f,1));
                } else {
                    font16.setColor(Color.WHITE);
                }
                i+=1;
                font16.draw(batch, line, xOrigin + 35 + 149*2 + textPad  + pad , yOrigin - 28 + height - (2+i) * font16Height - i * textPad + 4, width - (4+i) * pad, Align.left, false);
            }
            batch.draw(target.getClassData().getTexture(), xOrigin + 3*149 + 36, yOrigin - 36 - 67*2, 128, 128);
        }
    }

    private void createEntityInfoPopup(Entity entity) {
        interfaceSprite = new Sprite(Assets.TEXTURES.get("entityInfoPopup"));
        interfaceSprite.setX(xOrigin);
        createEntityInfoStrings(entity);
    }

    private void createEntityInfoStrings(Entity entity) {
        column1.clear();
        column2.clear();
        column3.clear();

        column1.add("PV : " + entity.getStats().get("pv") + " / " + entity.getStats().getStat("pv").getMax());
        column1.add("PD : " + entity.getStats().get("pd"));
        column1.add("PC : " + entity.getStats().get("pc"));
        column1.add("PM : " + entity.getStats().get("pm"));
        column1.add("PB : " + entity.getStats().get("pb"));
        column1.add("Erosion : " + (10 + entity.getStats().get("ero")) + "%");

        column2.add("Tacle : " + entity.getStats().get("tacle"));
        column2.add("Fuite : " + entity.getStats().get("fuite"));
        column2.add("Esquive : " + entity.getStats().get("esquive"));
        column2.add("Retrait : " + entity.getStats().get("ret"));
        column2.add("Res. Melée : " + entity.getStats().get("res mel") + "%");
        column2.add("Res. Distance : " + entity.getStats().get("res dist") + "%");

        column3.add("Res. Eau : " + entity.getStats().get("res eau") + "%");
        column3.add("Res. Feu : " + entity.getStats().get("res feu") + "%");
        column3.add("Res. Air : " + entity.getStats().get("res air") + "%");
        column3.add("Res. Fou : " + entity.getStats().get("res foudre") + "%");
        column3.add("Res. Pou : " + entity.getStats().get("res pou") + "%");
        column3.add("Res. Crit : " + entity.getStats().get("res crit") + "%");

    }

    private void createPopup() {
        x = MapIso.xFromIso(target.getPosition().x, target.getPosition().y);
        y = MapIso.yFromIso(target.getPosition().x, target.getPosition().y);

        entityLine = target.getPseudo() + " (" + target.getStats().get("pv") + ")";

        GlyphLayout layout = new GlyphLayout();

        float width2 = 0;
        if(spell != null) {
            if(!spell.getDamageLines().isEmpty()) {
                float multiplier = 1;
                if(spell.getClass() == CoupDeGrace.class) {
                    if(target.getStats().hasEffectWithName(Ciblage.effectName)) {
                        multiplier = 2;
                    }
                }
                // Min No Critical //
                spell.getMinRandom(caster);
                ArrayList<Integer> damageListMinNoCrit = new ArrayList<>();
                int minSumNoCrit = spell.getDamageSum(caster, target, spell.getDamageLines(), castPosition, false, damageListMinNoCrit, multiplier);

                // Min Critical //
                ArrayList<Integer> damageListMinCrit = new ArrayList<>();
                int minSumCrit = spell.getDamageSum(caster, target, spell.getDamageLines(), castPosition, true, damageListMinCrit, multiplier);

                // Max No Critical //
                spell.getMaxRandom(caster);
                ArrayList<Integer> damageListMaxNoCrit = new ArrayList<>();
                int maxSumNoCrit = spell.getDamageSum(caster, target, spell.getDamageLines(), castPosition, false, damageListMaxNoCrit, multiplier);

                // Max Critical //
                ArrayList<Integer> damageListMaxCrit = new ArrayList<>();
                int maxSumCrit = spell.getDamageSum(caster, target, spell.getDamageLines(), castPosition, true, damageListMaxCrit, multiplier);

                int critValue = spell.getCritic() + caster.getStats().get("crit");
                for(int i = 0; i < damageListMaxCrit.size(); i++) {
                    String text = "";
                    if(minSumNoCrit < 0) {
                        text += "+ ";
                    }
                    text += Math.abs(minSumNoCrit);
                    if(minSumNoCrit != maxSumNoCrit) text += " - " + Math.abs(maxSumNoCrit);

                    if(critValue > 0) {
                        text += " (" + Math.abs(minSumCrit);
                        if(minSumCrit != maxSumCrit) text += " - " + Math.abs(maxSumCrit);
                        text += ")";
                    }

                    damageStringLines.add(text);
                }

                if(spell.getDamageLines().size() > 1) {
                    damageColor = FontManager.multiColor;
                } else if (spell.getDamageLines().get(0).getElement() == DamageLine.WATER_ELEMENT) {
                    damageColor = FontManager.waterColor;
                } else if (spell.getDamageLines().get(0).getElement() == DamageLine.AIR_ELEMENT) {
                    damageColor = FontManager.airColor;
                } else if (spell.getDamageLines().get(0).getElement() == DamageLine.FIRE_ELEMENT) {
                    damageColor = FontManager.fireColor;
                } else if (spell.getDamageLines().get(0).getElement() == DamageLine.LIGHTNING_ELEMENT) {
                    damageColor = FontManager.lightingColor;
                } else {
                    damageColor = FontManager.healingColor;
                }

                if(!damageStringLines.isEmpty()) {
                    layout.setText(FontManager.firaFonts.get(16), damageStringLines.get(0));
                }
                width2 = layout.width;
                height += font16Height + textPad;
            }
        }



        layout.setText(FontManager.firaFonts.get(16), entityLine);
        float width1 = layout.width;

        width = Math.max(width1, width2) + 4 * pad;
        height += font16Height + 4 * pad;

    }

    public boolean isDifferent(Spell spell, Entity caster, Point castPosition) {
        return (caster != this.caster || castPosition.x != this.castPosition.x
                || castPosition.y != this.castPosition.y || spell != this.spell);
    }

    public boolean isDifferent(Entity target) {
        if(spell != null) {
            return true;
        } else {
            return this.target != target;
        }
    }

    public Spell getSpell() {
        return spell;
    }

    public Entity getCaster() {
        return caster;
    }

    public Point getCastPosition() {
        return castPosition;
    }

}

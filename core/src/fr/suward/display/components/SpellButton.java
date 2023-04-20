package fr.suward.display.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.suward.assets.Assets;
import fr.suward.assets.FontManager;
import fr.suward.display.listeners.SpellButtonListener;
import fr.suward.game.entities.spells.*;
import fr.suward.game.utils.BattleFunctions;
import fr.suward.managers.ClientManager;

import java.util.ArrayList;

public class SpellButton extends ImageButton {

    private Spell spell;

    private ArrayList<String> statLines;
    private ArrayList<String> descriptionLines;
    private ArrayList<String> effectLines;

    private int x = 1444;
    private int y = 206;
    private int width = 450;
    private float height;

    private float height1 = 94;
    private float height2 = 0;
    private float height3 = 0;
    private float height4 = 0;

    private int j;
    private String[] spellKey = new String[]{"&","é","\"", "'", "(", "-", "è", "_", "ç", "Ctrl+à", "Ctrl+&","Ctrl+é","Ctrl+\"", "Ctrl+'", "Ctrl+(", "Ctrl+-", "Ctrl+è", "Ctrl+_", "Ctrl+ç", "Ctrl+à"};

    private int pad = 15;
    private int padX = 15;
    private int textPad = 5;

    private BitmapFont font16 = FontManager.firaFonts.get(16);
    private float font16Height = 16;
    private BitmapFont font20 = FontManager.firaFonts.get(20);

    private boolean isDisable = false;


    public SpellButton(Spell spell, int i) {
        super(new TextureRegionDrawable(Assets.SPELLS.get(spell.getTextureID())));
        this.spell = spell;
        if(i >= 9) {
            i -= 9;
        }
        this.x -= 65 * (9 - i) + 32;
        this.j = i;
        addListener(new SpellButtonListener(spell, this));
        createSpellPopup();
    }

    public void displaySpellPopup(SpriteBatch batch) {

        batch.draw(Assets.TEXTURES.get("spellPopupBg"), x, y, width, height);
        batch.draw(Assets.SPELLS.get(spell.getTextureID()), x + pad, y + height - height1 + pad, 64, 64);
        font20.setColor(Color.WHITE);
        font20.draw(batch, spell.getName() + " (" + spellKey[j] + ")", x  + height1, y + height - height1/2 + 10);

        font16.setColor(Color.WHITE);
        float y = this.y + height;
        for(int i = 0; i < statLines.size(); i++) {
            font16.draw(batch, statLines.get(i), x + padX  + pad, y - height1 - (1+i) * font16Height - i * textPad);
        }

        font16.setColor(FontManager.orange2);
        font16.draw(batch, spell.getEffectDescription(), x + padX  + pad, y - height1 - height2 - pad - 1 * font16Height, width - 2 * pad, Align.left, true);


        font16.setColor(Color.WHITE);
        for(int i = 0; i < effectLines.size(); i++) {
            if(i > 0) {
                if(effectLines.get(i).charAt(3) == '-') {
                    font16.setColor(FontManager.flashyRed);
                } else {
                    font16.setColor(FontManager.green2);
                }
            }

            font16.draw(batch, effectLines.get(i), x + padX  + 2 * pad, y - height1 - height2 - height3 - 3 * pad - (1+i) * font16Height - i * textPad);
        }
    }

    public void createSpellPopup() {

        // Stats //
        height2 = createStatLines();

        // Description //
        height3 = createDescriptionLines();

        // Effect //
        height4 = createEffectLines();

        height = height1 + height2 + pad + height3 + pad + height4 + pad * 3;
    }


    public float createStatLines() {
        statLines = new ArrayList<>();
        String gainLine = "Gain : ";
        String costLine = "Coût : ";
        if(spell.getManaCost() > 0) {
            costLine += spell.getManaCost() + " PM / ";
        } else if (spell.getManaCost() < 0) {
            gainLine += (-spell.getManaCost()) + " PM / ";
        }
        if (spell.getConcentrationCost() > 0) {
            costLine += spell.getConcentrationCost() + " PC / ";
        } else if (spell.getManaCost() < 0) {
            gainLine += (-spell.getConcentrationCost()) + " PC / ";
        }
        if (spell.getTravelCost() > 0) {
            costLine += spell.getTravelCost() + " PD / ";
        } else if (spell.getManaCost() < 0) {
            gainLine += (-spell.getTravelCost()) + " PD / ";
        }
        if(costLine.charAt(costLine.length() - 2) == '/') {
            costLine = costLine.substring(0,costLine.length() - 2);
        }
        if(gainLine.charAt(gainLine.length() - 2) == '/') {
            gainLine = gainLine.substring(0,gainLine.length() - 2);
        }
        statLines.add(costLine);
        if(gainLine.length() > 7) {
            statLines.add(gainLine);
        }

        String rangeLine = "Portée : " + spell.getMinRange();
        if(spell.isRangeVariable()) {
            rangeLine += " à " + (spell.getMaxRange() + ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().get("po")) + " (modifiable)";
        } else if(spell.getMinRange() != spell.getMaxRange()){
            rangeLine +=  " à " + spell.getMaxRange();
        }
        statLines.add(rangeLine);

        if(spell.getCritic() > 0) {
            String criticLine = "Critique : " + spell.getCritic() + "%";
            statLines.add(criticLine);
        }


        if(spell.getZoneSize() > 0) {
            String zoneLine = "Zone d'effet : ";
            if(spell.getTargetZone() == Spell.CROSS_ZONE) {
                zoneLine += "croix";
            } else if(spell.getTargetZone() == Spell.ROW_ZONE) {
                zoneLine += "ligne";
            } else if(spell.getTargetZone() == Spell.LINE_ZONE) {
                zoneLine += "colonne";
            } else if(spell.getTargetZone() == Spell.SQUARE_ZONE) {
                zoneLine += "carré";
            } else if(spell.getTargetZone() == Spell.T_ZONE) {
                zoneLine += "T";
            } else if(spell.getTargetZone() == Spell.CIRCLE_ZONE) {
                zoneLine += "cercle";
            }
            zoneLine += " de " + spell.getZoneSize() + " case";
            if(spell.getZoneSize() > 1) {
                zoneLine += "s";
            }
            statLines.add(zoneLine);
        }

        if(spell.getCastZone() != Spell.CIRCLE_CAST) {
            String castLine = "Lancer en ";
            if(spell.getCastZone() == Spell.CROSS_CAST) {
                castLine += "ligne";
            } else if(spell.getCastZone() == Spell.DIAGONAL_CAST) {
                castLine += "diagonale";
            } else if(spell.getCastZone() == Spell.STAR_CAST) {
                castLine += "étoile";
            } else if(spell.getCastZone() == Spell.SQUARE_CAST) {
                castLine += "carré";
            }
            statLines.add(castLine);
        }

        if(!spell.isSightLine()) {
            statLines.add("Ne nécessite pas de ligne de vue");
        }

        if(spell.isTargetNecessary()) {
            statLines.add("Cible nécessaire");
        }

        if(spell.getMaxDelay() > 0) {
            statLines.add("Intervalle de relance : " + spell.getMaxDelay());
        } else if(spell.getMaxUses() < 100){
            String usesLine = spell.getMaxUses() + " utilisation";
            if(spell.getMaxUses() > 1) {
                usesLine += "s";
            }
            usesLine += " par tour";
            statLines.add(usesLine);
        }

        if(spell.getMaxUsesOnTarget() < 100) {
            String usesLine = spell.getMaxUsesOnTarget() + " lancer";
            if(spell.getMaxUsesOnTarget() > 1) {
                usesLine += "s";
            }
            usesLine += " par cible";
            statLines.add(usesLine);
        }

        String targetTypeLine = "N'affecte que les ";
        if(spell.getTargetType() == Spell.FOE_TARGET) {
            targetTypeLine += "ennemis";
            statLines.add(targetTypeLine);
        } else if(spell.getTargetType() == Spell.ALLY_TARGET) {
            targetTypeLine += "alliés";
            statLines.add(targetTypeLine);
        }
        return (font16Height + textPad) * statLines.size();
    }

    public float createDescriptionLines() {
        descriptionLines = new ArrayList<>();
        GlyphLayout layout = new GlyphLayout();

        float height3;
        layout.setText(FontManager.firaFonts.get(16), spell.getEffectDescription(), FontManager.orange2, width - 2 * pad, Align.left, true);
        return layout.height;
    }

    public float createEffectLines() {
        effectLines = new ArrayList<>();
        float height4;
        effectLines.add("Effets");
        int boost = 0;
        for(SpellBoost sb : spell.getSpellBoosts()) {
            boost += sb.getDamage();
        }

        if(spell.getPush() > 0) {
            String push = "    Repousse de " + spell.getPush() + " case";
            if(spell.getPush() > 1) {
                push += "s";
            }
            effectLines.add(push);
        } else if(spell.getPush() < 0) {
            String push = "   Attire de " + (-spell.getPush()) + " case";
            if(spell.getPush() < -1) {
                push += "s";
            }
            effectLines.add(push);
        }

        for(DamageLine dl : spell.getDamageLines()) {
            String dmgText;
            if(dl.isDrain()) {
                dmgText = "vol " + capitalize(BattleFunctions.stringStatFromElement(dl.getElement()));
            } else if(dl.isHeal()) {
                dmgText = "soin";
            } else {
                dmgText = "dommages " + capitalize(BattleFunctions.stringStatFromElement(dl.getElement()));
            }
            String text;
            if(dl.getMinDamage() + boost != dl.getMaxDamage() + boost) {
                text = "   " + (dl.getMinDamage() + boost) + " à " + (dl.getMaxDamage() + boost) + " (" + dmgText + ")";
            } else {
                text = "   " + (dl.getMaxDamage() + boost) + " (" + dmgText + ")";
            }
            effectLines.add(text);
        }

        for(Effect e : spell.getEffects()) {
            for(Boost b : e.getBoosts()) {
                if(b.getStatusID() >= 0) {
                    effectLines.add("   " + b.getStatusText());
                } else {
                    effectLines.add("   " + b.getBoostText());
                }
            }
        }

        if(effectLines.size() == 1) {
            effectLines.clear();
        }

        return (font16Height + textPad) * (effectLines.size());
    }

    public ArrayList<String> wordsFromString(String text) {
        ArrayList<String> words = new ArrayList<>();
        int i = 0;
        while(i < text.length()) {
            String word = "";
            while(text.charAt(i) != ' ') {
                if(i <= text.length() - 2) {
                    word += text.charAt(i);
                    i++;
                } else {
                    break;
                }
            }
            words.add(word);
            i++;
        }
        return words;
    }

    private static String capitalize(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public void setDisable(boolean disable) {
        isDisable = disable;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void renderShadow(SpriteBatch batch, float interfaceHeight) {
        int xs = (int) (this.getX() + 1045);
        int ys = (int) (this.getY() + 200 - interfaceHeight);
        if(xs >= 1663) {
            xs -= 520;
        }
        batch.draw(Assets.SPELLS.get(-1), xs, ys);
        int value = spell.getDelay();
        if(value == 0) {
            value = 1;
        }
        font20.setColor(Color.WHITE);
        font20.draw(batch, String.valueOf(value), xs + 27, ys + 39);
    }
}

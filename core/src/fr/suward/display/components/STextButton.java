package fr.suward.display.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class STextButton extends Button {

    private Label label;
    private BitmapFont font;

    public STextButton(String text, BitmapFont font, Color fontColor, Skin skin) {
        super(skin);
        label = new Label(text, new Label.LabelStyle(font, fontColor));
        this.add(label);
    }

    public void setText(String text) {
        label.setText(text);
    }


}

package fr.suward.display.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.assets.FontManager;
import fr.suward.display.states.stages.character.CharacterStatsCreation;

public class StatLineSpinner extends Table {

    private CharacterStatsCreation state;

    private String statName;
    private int minValue;
    private int maxValue;
    private int step;

    private STextButton lessButton;
    private STextButton plusButton;
    private int value;
    private Label valueLabel;
    private Label textLabel;

    public StatLineSpinner(CharacterStatsCreation superState, String statName, int min, int max, final int step, Skin skin) {
        this.state = superState;
        this.statName = statName;
        this.minValue = min;
        this.maxValue = max;
        this.step = step;

        value = minValue;

        textLabel = new Label(statName + " (" + minValue + " à " + maxValue + ") (+ " + step + ")", new Label.LabelStyle(FontManager.firaFonts.get(16), FontManager.darkGray));
        lessButton = new STextButton("-", FontManager.firaFonts.get(16), FontManager.darkGray, skin);
        plusButton = new STextButton("+", FontManager.firaFonts.get(16), FontManager.darkGray, skin);
        valueLabel = new Label(String.valueOf(value), new Label.LabelStyle(FontManager.firaFonts.get(16), FontManager.darkGray));

        lessButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(value - step >= minValue) {
                    value -= step;
                    state.addCpPoint(1);
                }
                update();
            }
        });

        plusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(state.getCpPoint() > 0) {
                    if(value + step <= maxValue) {
                        value += step;
                        state.addCpPoint(-1);
                    }
                    update();
                }

            }
        });

        this.add(textLabel);
        this.add(lessButton);
        this.add(valueLabel);
        this.add(plusButton);

    }

    public void update() {
        valueLabel.setText(String.valueOf(value));
        state.updateCpPoint();
    }


    public int getValue() {
        return value;
    }
}

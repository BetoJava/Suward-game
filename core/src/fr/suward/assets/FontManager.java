package fr.suward.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class FontManager {

    public static final Color red = new Color(0.8f, 0.125f, 0.125f, 1f);
    public static final Color flashyRed = new Color(1f, 0.125f, 0.125f, 1f);
    public static final Color blue = new Color(0.1f,0.2f,0.4f,1f);
    public static final Color lightBlue = new Color(0.33f,0.33f,1f,1f);
    public static final Color gray = new Color(0.4f, 0.4f, 0.4f, 1f);
    public static final Color darkGray = new Color(0.2f, 0.2f, 0.2f, 1f);
    public static final Color green = new Color(0.1f, 0.4f, 0.1f, 1f);
    public static final Color flashyGreen = new Color(0.125f, 1f, 0.125f, 1f);
    public static final Color veryLightGray = new Color(0.9f,0.9f,0.9f,1f);
    public static final Color orange2 = new Color(0.78f,0.47f,0.22f,1f);
    public static final Color green2 = new Color(0.31f,0.67f,0.24f,1f);

    public static final Color airColor = new Color(0f,0.88f,0.39f,1f);
    public static final Color lightingColor = new Color(0.88f,0.87f,0f,1f);
    public static final Color fireColor = new Color(1f,0.39f,0f,1f);
    public static final Color waterColor = new Color(0.17f,0.17f,1f,1f);
    public static final Color multiColor = new Color(0.84f,0f,1f,1f);
    public static final Color healingColor = new Color(1f,0.1f,0f,1f);

    public static HashMap<Integer, BitmapFont> firaFonts = new HashMap<>();

    public static void loadFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        for(int i = 12; i < 40; i+=4) {
            parameter.size = i;
            BitmapFont font = generator.generateFont(parameter);
            firaFonts.put(i,font);
        }
        generator.dispose();
    }

    public static BitmapFont firaFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/FiraSans-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;

    }

    public static BitmapFont firaFont(int size, Color borderColor, int borderWidth) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/FiraSans-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = borderWidth;
        parameter.borderColor = borderColor;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;

    }

    public static BitmapFont robotoFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;

    }

    public static BitmapFont robotoFont(int size, Color borderColor, int borderWidth) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = borderWidth;
        parameter.borderColor = borderColor;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;

    }

}

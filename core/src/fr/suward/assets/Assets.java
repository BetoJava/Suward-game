package fr.suward.assets;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Assets {

    private static AssetManager assetManager = new AssetManager();

    public static final HashMap<String, Texture> TILES = new HashMap<>();
    public static final HashMap<String, Texture> TEXTURES = new HashMap<>();
    public static final HashMap<Integer, Texture> SPELLS = new HashMap<>();
    public static final HashMap<String, Texture> CHARACTERS = new HashMap<>();
    public static final HashMap<String, Font> FONTS = new HashMap<>();

    public static final AssetDescriptor<Skin> SKIN_COMPOSER = new AssetDescriptor<>("skins/skin-composer/skin/skin-composer-ui.json", Skin.class, new SkinLoader.SkinParameter("skins/skin-composer/skin/skin-composer-ui.atlas"));



    public static void load() {
        long t1 = System.currentTimeMillis();
        assetManager.load(SKIN_COMPOSER);
        FontManager.loadFonts();
        loadResources();
        System.out.println("Temps de process : " + String.valueOf((System.currentTimeMillis()-t1)));
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }

    private static void loadResources() {
        // Buttons //
        loadTexture("quitButton", "ui/quitButton.png");
        loadTexture("quitButtonPressed", "ui/quitButtonPressed.png");
        loadTexture("UISprite1", "ui/UISprite1.png");
        loadTexture("UISprite2", "ui/UISprite2.png");
        loadTexture("UISpritePopup", "ui/UISpritePopup.png");

        // Fonts //
        loadFont("Fira", "FiraSans-Black.ttf");
        loadFont("Roboto Bold", "Roboto-Bold.ttf");
        //loadFont("Miriam", "Miriam.otf");
        //loadFont("Miriam Bold", "MiriamBold.otf");

        // Tiles //
        for(String str : new String[]{"64","128"}) {
            for(String s : new String[]{"","B","R"}) {
                loadTile("oddGround"+s+str, str+"/oddGround"+s+str+".png");
                loadTile("evenGround"+s+str, str+"/evenGround"+s+str+".png");
                loadTile("wall"+s+str, str+"/wall"+s+str+".png");
            }

            loadTile("blueGround"+str, str+"/blueGround"+str+".png");
            loadTile("lightBlueGround"+str, str+"/lightBlueGround"+str+".png");
            loadTile("redGround"+str, str+"/redGround"+str+".png");
            loadTile("path"+str, str+"/path"+str+".png");
            loadTile("pdArea"+str, str+"/pdArea"+str+".png");
            loadTile("noPdArea"+str, str+"/noPdArea"+str+".png");
        }

        loadTile("blueCircle128", "128/blueCircle128.png");
        loadTile("redCircle128", "128/redCircle128.png");
        loadTile("blueCircle64", "64/blueCircle64.png");
        loadTile("redCircle64", "64/redCircle64.png");

        // Characters //
        loadCharacter("betonneur", "betonneur.png");
        loadCharacter("betonneur64", "64/betonneur64.png");
        loadCharacter("betonneur128", "128/betonneur128.png");
        loadCharacter("Ouq", "Ouq.png");
        loadCharacter("Dolma", "Dolma.png");
        loadCharacter("Bram", "Bram.png");
        loadCharacter("Erit", "Erit.png");
        loadCharacter("Eres", "Eres.png");
        loadCharacter("Hallebarde", "Hallebarde.png");
        loadCharacter("Ouq64", "64/Ouq64.png");
        loadCharacter("Bram64", "64/Bram64.png");
        loadCharacter("Erit64", "64/Erit64.png");
        loadCharacter("Eres64", "64/Eres64.png");
        loadCharacter("Hallebarde64", "64/Hallebarde64.png");
        loadCharacter("EpeeSanglante", "EpeeSanglante.png");
        loadCharacter("MurDePierre", "MurDePierre.png");
        loadCharacter("Hypercube", "Hypercube.png");

        loadCharacter("Pasdoeuf", "Pasdoeuf.png");
        loadCharacter("Chaî", "Chaî.png");
        loadCharacter("Oeuf", "Oeuf.png");
        loadCharacter("Moustache De Gégé", "Moustache De Gégé.png");
        loadCharacter("Maître De Chaî", "Maître De Chaî.png");
        loadCharacter("Pasdstuff", "Pasdstuff.png");


        // Spells //
        for(int i = 0; i <= 136; i++) {
            SPELLS.put(i, new Texture("img/spells/sufdo/" + i + ".png"));
            SPELLS.get(i).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        SPELLS.put(-1, new Texture("img/spells/sufdo/shadow.png"));
        SPELLS.get(-1).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Status icons //
        loadStatusIcon("insoignable");
        loadStatusIcon("enracine");
        loadStatusIcon("lourd");
        loadStatusIcon("invu");
        loadStatusIcon("invuMel");
        loadStatusIcon("invuDist");
        loadStatusIcon("cible");
        loadStatusIcon("pesanteur");
        loadStatusIcon("depressif");
        for(int i = 1; i <= 10; i++) {
            loadStatusIcon(i + "");
            loadStatusIcon(i + "r");
        }

        // Music //
        //Resources.MUSICS.put("music", new File("./src/resources/musics/music.mid"));

        // Interfaces //
        TEXTURES.put("chatInterface", new Texture("img/textures/interfaces/chatInterface.png"));
        TEXTURES.get("chatInterface").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("middleInterface", new Texture("img/textures/interfaces/middleInterface.png"));
        TEXTURES.get("middleInterface").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("spellInterface", new Texture("img/textures/interfaces/spellInterface.png"));
        TEXTURES.get("spellInterface").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("rightInterface", new Texture("img/textures/interfaces/rightInterface.png"));
        TEXTURES.get("rightInterface").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("entityInfoPopup", new Texture("img/textures/interfaces/entityInfoPopup.png"));
        TEXTURES.get("entityInfoPopup").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            // Timeline //
        TEXTURES.put("timelineHead", new Texture("img/textures/interfaces/timeline/timelineHead.png"));
        TEXTURES.get("timelineHead").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("blueCard", new Texture("img/textures/interfaces/timeline/blueCard.png"));
        TEXTURES.get("blueCard").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("redCard", new Texture("img/textures/interfaces/timeline/redCard.png"));
        TEXTURES.get("redCard").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("blueTurnCard", new Texture("img/textures/interfaces/timeline/blueTurnCard.png"));
        TEXTURES.get("blueTurnCard").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TEXTURES.put("redTurnCard", new Texture("img/textures/interfaces/timeline/redTurnCard.png"));
        TEXTURES.get("redTurnCard").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            // Health Bar //
        for(int i = 0; i < 110; i += 10) {
            loadHealthBar(i);
        }
            // Primary Stats //
        TEXTURES.put("primaryStats", new Texture("img/textures/interfaces/primaryStats.png"));
        TEXTURES.get("primaryStats").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Spell Popup //
        TEXTURES.put("spellPopupBg", new Texture("img/textures/ui/spellPopupBg.png"));
        TEXTURES.get("spellPopupBg").setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }



    private static void loadTexture(String name, String relativePath) {
        TEXTURES.put(name, new Texture("img/textures/"+relativePath));
        TEXTURES.get(name).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    private static void loadTile(String name, String relativePath) {
        TILES.put(name, new Texture("img/textures/tiles/"+relativePath));
        TILES.get(name).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    private static void loadCharacter(String name, String relativePath) {
        CHARACTERS.put(name, new Texture("img/characters/"+relativePath));
        CHARACTERS.get(name).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    private static void loadHealthBar(int value) {
        TEXTURES.put("hb" + value, new Texture("img/textures/interfaces/healthBar/hb" + value + ".png"));
        TEXTURES.get("hb" + value).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    private static void loadStatusIcon(String name) {
        TEXTURES.put(name, new Texture("img/status/" + name + ".png"));
        TEXTURES.get(name).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    private static void loadFont(String name, String relativePath) {
        try {
            FONTS.put(name, Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "/fonts/" + relativePath))));
        } catch (IOException | FontFormatException e) {

            try {
                FONTS.put(name, Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(new FileInputStream("./core/assets/fonts/" + relativePath))));
            } catch (IOException | FontFormatException er) {

            }
        }
    }
}

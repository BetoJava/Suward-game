package fr.suward.display.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import fr.suward.assets.Assets;
import fr.suward.assets.FontManager;
import fr.suward.managers.ClientManager;

public class MiddleInterface extends Stack {

    private Image healthBar;
    private Table bg;
    private Image primaryStats;

    private Label maxHP;
    private Label hp;
    private Stack primaryStatsStack;
    private Label PC;
    private Label PD;
    private Label PM;

    public MiddleInterface(Table table) {

        this.setLayoutEnabled(false);
        this.setWidth(256);
        this.setHeight(200);
        this.setX(789);
        table.add(this);

        bg = new Table();
        bg.setBackground(new TextureRegionDrawable(Assets.TEXTURES.get("middleInterface")));
        bg.setWidth(256);
        bg.setHeight(200);
        this.add(bg);

        healthBar = new Image(Assets.TEXTURES.get("hb80"));
        healthBar.setWidth(256);
        healthBar.setHeight(200);
        healthBar.setX(1);
        healthBar.setY(24);
        this.add(healthBar);

        primaryStatsStack = new Stack();
        primaryStatsStack.setHeight(132);
        primaryStatsStack.setWidth(40);
        primaryStatsStack.setX(236);
        primaryStatsStack.setY(68);
        this.add(primaryStatsStack);

        primaryStats = new Image(Assets.TEXTURES.get("primaryStats"));
        primaryStats.setHeight(132);
        primaryStats.setWidth(40);
        primaryStatsStack.add(primaryStats);


        // Stats //

            // HP Table //
        Table hpTable = new Table();
        hpTable.setWidth(256);
        hpTable.setHeight(200);
        hpTable.align(Align.top);
        this.add(hpTable);

                // PV //
        hp = newLabel("pv");
        //hp.setX(124);
        hp.setY(139);
        hpTable.add(hp).padTop(30).row();

                // Separation //

        hpTable.add(new Label("---", new Label.LabelStyle(FontManager.robotoFont(20, Color.BLACK, 0), Color.WHITE))).row();

                // PV  Max//
        maxHP = new Label(String.valueOf(ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().getStat("pv").getMax()),
                new Label.LabelStyle(FontManager.robotoFont(20, Color.BLACK, 0), Color.WHITE));
        maxHP.setY(100);
        hpTable.add(maxHP);


            // Primary Stats //
        Table psTable = new Table();
                // PD //
        PD = newLabel("pd");
        psTable.add(PD).padBottom(15).padTop(6).row();
                // PC //
        PC = newLabel("pc");
        psTable.add(PC).padTop(4).row();
                // PM //
        PM = newLabel("pm");
        psTable.add(PM).padTop(20).row();

        primaryStatsStack.add(psTable);


    }

    public void update() {
        float hpPercentage = (ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().get("pv") * 100/(float)ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().getStat("pv").getMax());

        if(hpPercentage > 90) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb100")));
        } else if(hpPercentage > 80) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb90")));
        } else if(hpPercentage > 70) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb80")));
        } else if(hpPercentage > 60) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb70")));
        } else if(hpPercentage > 50) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb60")));
        } else if(hpPercentage > 40) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb50")));
        } else if(hpPercentage > 30) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb40")));
        } else if(hpPercentage > 20) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb30")));
        } else if(hpPercentage > 10) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb20")));
        } else if(hpPercentage > 3) {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb10")));
        } else {
            healthBar.setDrawable(new TextureRegionDrawable(Assets.TEXTURES.get("hb0")));
        }

        hp.setText(String.valueOf(ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().get("pv")));
        maxHP.setText(ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().getStat("pv").getMax());
        PD.setText(String.valueOf(ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().get("pd")));
        PM.setText(String.valueOf(ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().get("pm")));
        PC.setText(String.valueOf(ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().get("pc")));

    }

    private Label newLabel(String name) {
        return new Label(String.valueOf(ClientManager.get().getCurrentClientCharacterOrItsSummon().getStats().get(name)),
                new Label.LabelStyle(FontManager.robotoFont(20, Color.BLACK, 0), Color.WHITE));
    }


    public Image getHealthBar() {
        return healthBar;
    }

    public Image getPrimaryStats() {
        return primaryStats;
    }

    public void addX(int value) {
        hp.setX(hp.getX() + value);
    }

    public void addY(int value) {
        hp.setY(hp.getY() + value);
    }
}

package fr.suward.game.entities.spells.classes.dolma;

import fr.suward.assets.FontManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.*;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class Impesanteur extends Spell {

    public Impesanteur() {
        super();
        name = "Impesanteur";
        textureID = 125;
        damageLines.add(new DamageLine(38, 44, DamageLine.WATER_ELEMENT, false, false));
        minRange = 2;
        maxRange = 6;
        critic = 10;
        manaCost = 4;
        maxUses = 3;
        castZone = SQUARE_CAST;
        isRangeVariable = true;

        effectDescription = "Occasionne des dommages Eau aux ennemis.\nSi la cible est dans l'état pesanteur, l'état est consommé,\n" +
                "les dégats du sort sont augmentés de 60% et le lanceur gagne 2 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        for(Entity target : targets) {
            int amount = target.removeStatusWithID(Effect.GRAVITY);
            hit(isCritical, caster, target, pos, 1+Math.min(amount, 1)*0.60f);
            if(amount > 0) {
                caster.getStats().add("pc", 2);
                ClientManager.get().sendMessageInChat(caster.getPseudo() + " gagne 2 PC !", FontManager.flashyGreen);
                for(Spell s : caster.getClassData().getSpells()) {
                    System.out.println(s.getName());
                    if(s.getName() == "Trou Noir") s.addSpellBoost(new SpellBoost(33, 1000), caster);
                }
            }

        }
    }

    @Override
    public void startAction() {

    }

    @Override
    public void endAction() {

    }

    @Override
    protected void addEffect() {

    }


}

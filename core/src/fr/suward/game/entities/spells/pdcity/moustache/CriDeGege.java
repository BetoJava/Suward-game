package fr.suward.game.entities.spells.pdcity.moustache;

import fr.suward.assets.FontManager;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.spells.Boost;
import fr.suward.game.entities.spells.DamageLine;
import fr.suward.game.entities.spells.Effect;
import fr.suward.game.entities.spells.Spell;
import fr.suward.game.entities.spells.classes.erit.Ciblage;
import fr.suward.managers.ClientManager;

import java.awt.*;
import java.util.ArrayList;

public class CriDeGege extends Spell {

    public static String effectName = "Cri De Gégé";

    public CriDeGege() {
        super();
        name = "Cri De Gégé";
        textureID = 50;
        minRange = 1;
        maxRange = 1;
        manaCost = 3;
        maxUses = 1;
        maxDelay = 1;
        isRangeVariable = false;
        zoneSize = 43;
        targetZone = Spell.LINE_ZONE;

        effectDescription = "Retire 1 PM (1t) et applique 15% d'érosion (2t) (max 1).\nPour chaque cible, gagne 1 PC.";
    }

    @Override
    public void action(Spell spell, boolean isCritical, ArrayList<Integer> rds, Entity caster, ArrayList<Entity> targets, ArrayList<Point> zone, Point pos) {
        if(targets.size() > 0) {
            caster.getStats().add("pc", targets.size());
            ClientManager.get().sendMessageInChat(caster.getPseudo() + " gagne " + targets.size() + " PC !", FontManager.flashyGreen);
        }
        for(Entity target : targets) {
            if(!target.getStats().hasEffectWithName(effectName)) {
                for(Effect e : effects) {
                    e.addEffectToTarget(target, caster, target.getPseudo() + " est érodé de 15% pour 2 tours et perd 1 PM pour 1 tour.");
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
        Effect e = new Effect(effectName,3);
        e.add(new Boost("ero", 15, 3));
        e.add(new Boost("pm", -1, 2));
        effects.add(e);
    }
}

package fr.suward.display.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fr.suward.display.components.SpellButton;
import fr.suward.game.entities.spells.Spell;
import fr.suward.managers.ClientManager;

public class SpellButtonListener extends ClickListener {
    
    private Spell spell;
    private SpellButton spellButton;

    public SpellButtonListener(Spell spell, SpellButton spellButton) {
        this.spell = spell;
        this.spellButton = spellButton;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        ClientManager.get().setSpellLock(spell);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        ClientManager.get().setOverSpellButton(spellButton);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        ClientManager.get().setOverSpellButton(null);
    }
}

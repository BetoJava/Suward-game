package fr.suward.display.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.suward.assets.Assets;
import fr.suward.managers.Engine;
import fr.suward.managers.StageManager;


public class GameState extends Game {

    private static GameState gameState = new GameState();

    public static GameState get() {
        return gameState;
    }

    public void create () {
        loadAssets();
        Engine.start();
    }

    public void resize (int width, int height) {
        StageManager.get().getStage().getViewport().update(width, height, true);
    }

    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 1);
        StageManager.get().getStage().act(Gdx.graphics.getDeltaTime());
        StageManager.get().getStage().draw();

    }

    public void dispose () {
        StageManager.get().getStage().dispose();
    }

    //_________________ Extracted Methods ________________________________//

    private void loadAssets() {
        Assets.load();
        Assets.getAssetManager().finishLoading();
    }


}

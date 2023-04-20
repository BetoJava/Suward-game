package fr.suward.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.suward.display.states.GameState;

public class DesktopLauncher {


	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		configure(config);
		LwjglApplication l = new LwjglApplication(GameState.get(), config);

	}

	private static void configure(LwjglApplicationConfiguration config) {
		config.title = "Suward";
		config.width = 1900;
		config.height = 990;
		config.addIcon("img/icon/icon32.png", Files.FileType.Internal);
		config.resizable = false;
		config.x = 5;
		config.y = 0;
	}

}

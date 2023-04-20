package fr.suward.managers;

import fr.suward.display.states.stages.MainMenu;

public class Engine {


    public static void start() {
        StageManager.start(new MainMenu());
    }

}

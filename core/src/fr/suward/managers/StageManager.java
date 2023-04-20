package fr.suward.managers;

import com.badlogic.gdx.Gdx;
import fr.suward.display.states.stages.State;

import java.awt.*;
import java.util.Stack;

public class StageManager {

    private static StageManager stageManager;

    public static void start(State firstStage) {
        stageManager = new StageManager(firstStage);
    }

    public static StageManager get() {
        return stageManager;
    }

    private Stack<State> stageStack = new Stack<>();
    private State nextStage;
    private Point mousePos;
    private boolean removeLastStage = false;

    public StageManager(State firstStage) {
        stageStack.push(firstStage);
    }

    public void nextStage(State stage) {
        nextStage(stage, false);
    }

    public void nextStage(State stage, boolean removeLastStage) {
        if(!stageStack.empty()) {
            stageStack.peek().fadeOut(0f);
        }
        nextStage = stage;
        this.removeLastStage = removeLastStage;
    }

    public void setNextStage() {
        if(removeLastStage) {
            stageStack.pop();
        }
        stageStack.push(nextStage);
        nextStage.fadeIn(0f);
        Gdx.input.setInputProcessor(nextStage);
    }


    public State getStage() {
        if(!stageStack.empty()) {
            return stageStack.peek();
        }
        return null;
    }

    public Point getMousePos() {
        return mousePos;
    }

    public void setMousePos(Point mousePos) {
        this.mousePos = mousePos;
    }

    public Stack<State> getStageStack() {
        return stageStack;
    }

    public State getNextStage() {
        return nextStage;
    }

}

package me.giantcrack.gge.game;

/**
 * Created by markvolkov on 8/6/15.
 */
public class GameState {

    protected String state;

    protected int stateID;

    public GameState(String state, int stateID) {
        this.state = state;
        this.stateID = stateID;
    }

    public String getState() {
        return state;
    }

    public int getStateID() {
        return stateID;
    }

}

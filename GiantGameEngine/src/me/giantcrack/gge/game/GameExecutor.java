package me.giantcrack.gge.game;

/**
 * Created by markvolkov on 8/6/15.
 */
public abstract class GameExecutor {

    public abstract void start();

    public abstract void countdown();

    public abstract void end();

    public abstract void setUpGame();

    public abstract void broadcast(String msg);


}

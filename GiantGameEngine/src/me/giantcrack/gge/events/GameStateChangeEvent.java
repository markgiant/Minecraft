package me.giantcrack.gge.events;

import me.giantcrack.gge.game.Game;
import me.giantcrack.gge.game.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by markvolkov on 8/6/15.
 */
public class GameStateChangeEvent extends Event {

    private Game game;

    private GameState newState;

    public GameStateChangeEvent(Game g,GameState newState) {
        this.game = g;
        this.newState = newState;
    }

    public GameState getNewState() {
        return newState;
    }

    public GameState getCurrentState() {
        if (game.getState(game.getStates().size()+1) != null) {
            return game.getState(game.getStates().size()+1);
        }
        return null;
    }

    public Game getGame() {
        return game;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

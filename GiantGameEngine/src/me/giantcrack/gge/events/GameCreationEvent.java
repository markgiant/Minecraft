package me.giantcrack.gge.events;

import me.giantcrack.gge.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by markvolkov on 8/6/15.
 */
public class GameCreationEvent extends Event {

    private Game game;

    public GameCreationEvent(Game g) {
        this.game = g;
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

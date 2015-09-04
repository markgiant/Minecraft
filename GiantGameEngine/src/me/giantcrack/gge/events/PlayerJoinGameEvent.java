package me.giantcrack.gge.events;

import me.giantcrack.gge.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by markvolkov on 8/6/15.
 */
public class PlayerJoinGameEvent extends Event implements Cancellable {

    private Game game;
    private Player p;

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public PlayerJoinGameEvent(Player p,Game g,boolean cancelled) {
        this.p = p;
        this.game = g;
        this.cancelled = cancelled;
    }

    public Game getGame() {
        return game;
    }
    public Player getPlayer() {
        return p;
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

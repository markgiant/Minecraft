package me.giantcrack.gge.events;

import me.giantcrack.gge.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by markvolkov on 8/6/15.
 */
public class PlayerLeaveGameEvent extends Event {
    private Game game;
    private Player p;

    public PlayerLeaveGameEvent(Player p,Game g) {
        this.p = p;
        this.game = g;
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

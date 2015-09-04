package me.giantcrack.gge.events;

import me.giantcrack.gge.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public class GameStartEvent extends Event {

    private Game game;

    public GameStartEvent(Game g) {
        this.game = g;
    }

    public Game getGame() {
        return game;
    }

    public List<Player> getGamePlayers() {
        return game.getPlayers();
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

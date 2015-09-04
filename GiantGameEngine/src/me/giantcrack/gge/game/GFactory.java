package me.giantcrack.gge.game;

import com.avaje.ebean.validation.NotNull;
import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import me.giantcrack.gge.events.GameCreationEvent;
import me.giantcrack.gge.events.GameDeletionEvent;
import me.giantcrack.gge.exceptions.GameException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.xml.validation.Validator;
import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public abstract class GFactory implements GameManager {

    protected Plugin plugin;

    public GFactory(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<? extends Game> getGames() {
        return null;
    }

    @Override
    public void createGame(String name, int id) throws GameException {
        GameCreationEvent event = new GameCreationEvent(getGame(name,id));
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public Game getGame(String name, int id) {
        return null;
    }

    @Override
    public void removeGame(String name, int id) throws GameException {
        GameDeletionEvent event = new GameDeletionEvent(getGame(name,id));
        Bukkit.getPluginManager().callEvent(event);
    }
}

package me.giantcrack.gge.game;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by markvolkov on 8/6/15.
 */
public interface GameHandler {

    void addPlayer(Player p);

    void removePlayer(Player p);

    int getMinPlayers();

    int getMaxPlayers();

    boolean containsPlayer(Player p);

    List<Player> getPlayers();

    void garbageCleanUp();

    GameState getState(String name);

    GameState getState(int id);

    void addState(String name);

    void removeState(String name);

    void setState(String newState);

    Map<String,GameState> getStates();

}

package me.giantcrack.gge.game;

import com.avaje.ebean.validation.NotNull;
import me.giantcrack.gge.events.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by markvolkov on 8/6/15.
 */
public abstract class Game extends GameExecutor implements GameHandler {

    protected String name;
    protected int id;
    protected GameState currentState;

    protected List<Player> players = new ArrayList<>();

    protected Map<String,GameState> states = new HashMap<>();

    public Game(String name, int id) {
        this.name = name;
        this.id = id;
        this.currentState = null;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }


    @Override
    public void start() {
        GameStartEvent event = new GameStartEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void countdown() {
        GameCountdownEvent event = new GameCountdownEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    public GameState getCurrentState() {
        return currentState;
    }

    @Override
    public void end() {
        GameEndEvent event = new GameEndEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void addPlayer(Player p) {
        PlayerJoinGameEvent event = new PlayerJoinGameEvent(p,this,false);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
    }

    @Override
    public void removePlayer(Player p) {
        PlayerLeaveGameEvent event = new PlayerLeaveGameEvent(p,this);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public int getMinPlayers() {
        return 0;
    }

    @Override
    public int getMaxPlayers() {
        return 0;
    }

    @Override
    public boolean containsPlayer(Player p) {
        return players.contains(p);
    }

    @Override
    public GameState getState(String name) {
        GameState state = states.get(name);
        if (state == null) {
            return null;
        }
        return state;
    }

    @Override
    public void setState(String newState) {
        GameState state = getState(newState);
        if (state != null) {
            GameStateChangeEvent event = new GameStateChangeEvent(this,state);
            Bukkit.getPluginManager().callEvent(event);
            this.currentState = state;
        }
    }

    @Override
    public GameState getState(int id) {
        for(Map.Entry<String,GameState> state : states.entrySet()) {
            if (state.getValue().getStateID() == id) {
                return state.getValue();
            }
        }
        return null;
    }

    @Override
    public void addState(String name) {
        if (getState(name) != null) return;
        GameState state = new GameState(name,states.size()+1);
        states.put(name,state);
    }

    @Override
    public void removeState(String name) {
        if (getState(name) == null) return;
        states.remove(name);
    }

    @Override
    public Map<String, GameState> getStates() {
        return states;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void garbageCleanUp() {
        this.players = new ArrayList<>();
    }
}

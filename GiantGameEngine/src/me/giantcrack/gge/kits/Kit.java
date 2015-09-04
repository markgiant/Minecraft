package me.giantcrack.gge.kits;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public abstract class Kit implements KitInfo {

    protected String name;

    protected List<String> players = new ArrayList<>();

    public Kit(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void clearPlayers() {
        this.players = new ArrayList<>();
    }

    @Override
    public void apply(Player p) {

    }

    @Override
    public boolean hasKit(Player p) {
        return players.contains(p.getName());
    }

    @Override
    public List<String> getPlayersWithKit() {
        return players;
    }
}

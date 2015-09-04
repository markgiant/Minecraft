package me.giantcrack.gge.teams;

import me.giantcrack.gge.F;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public class Team implements TeamHandler {

    protected String name;

    protected List<Player> players = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void addPlayer(Player p) {
        if (containsPlayers(p)) return;
        players.add(p);
        p.sendMessage(F.color("&7You have joined the &c"+name+"&7 team!"));
    }

    @Override
    public void removePlayer(Player p) {
        if (!containsPlayers(p)) return;
        players.remove(p);
        p.sendMessage(F.color("&7You have left the &c"+name+"&7 team!"));
    }

    @Override
    public void teleport(Location l) {
        for (Player p : players) {
            p.teleport(l);
        }
    }

    @Override
    public boolean containsPlayers(Player p) {
        return players.contains(p);
    }

    @Override
    public void garbageClean() {
        this.players = new ArrayList<>();
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }
}

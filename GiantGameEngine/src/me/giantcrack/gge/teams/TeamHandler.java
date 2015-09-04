package me.giantcrack.gge.teams;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public interface TeamHandler {

    void addPlayer(Player p);

    void removePlayer(Player p);

    boolean containsPlayers(Player p);

    void garbageClean();

    List<Player> getPlayers();

    void teleport(Location l);
}

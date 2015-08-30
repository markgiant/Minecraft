package me.giantcrack.bpvp.arenas;

import me.giantcrack.bpvp.files.ArenaData;
import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class Arena {

    private String name;
    private Location spawn1, spawn2;
    private Set<Player> arenaPlayers;

    public Arena(String name, Location spawn1, Location spawn2) {
        this.name = name;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.arenaPlayers = new HashSet<>();
    }

    public Set<Player> getArenaPlayers() {
        return arenaPlayers;
    }

    public void cleanupPlayers() {
        this.arenaPlayers = new HashSet<>();
    }

    public void addPlayer(Player... p) {
        for (Player pl : p) {
            this.arenaPlayers.add(pl);
        }
    }

    public void removePlayer(Player... p) {
        for (Player pl : p) {
            this.arenaPlayers.remove(pl);
        }
    }

    public Arena(String name) {
        this.name = name;
        this.spawn1 = null;
        this.spawn2 = null;
    }

    public void setup() {
        ArenaData.getInstance().createConfigurationSection("Arenas." + name);
        LocationUtility.saveLocation("Arenas." + name + ".spawn1", spawn1);
        LocationUtility.saveLocation("Arenas." + name + ".spawn2", spawn2);
        ArenaData.getInstance().save();
    }


    public void delete() {
        ArenaData.getInstance().set("Arenas." + name + ".spawn1", null);
        ArenaData.getInstance().set("Arenas." + name + ".spawn2", null);
        ArenaData.getInstance().set("Arenas." + name, null);
        ArenaData.getInstance().save();
        ArenaManager.getInstance().arenas.remove(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpawn1() {
        return spawn1;
    }

    public void setSpawn1(Location spawn1) {
        this.spawn1 = spawn1;
    }

    public Location getSpawn2() {
        return spawn2;
    }

    public void setSpawn2(Location spawn2) {
        this.spawn2 = spawn2;
    }
}

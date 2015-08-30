package me.giantcrack.bpvp.arenas;

import me.giantcrack.bpvp.files.ArenaData;
import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class ArenaManager {


    private static ArenaManager instance = new ArenaManager();

    private ArenaManager() {}

    public static ArenaManager getInstance() {
        return instance;
    }

    public List<Arena> arenas = new ArrayList<>();

    public Arena getArena(String name) {
        for (Arena a : arenas) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public void createArena(Player p, String name) {
        if (getArena(name) != null) {
            p.sendMessage(ChatColor.RED + "That arena already exists!");
            return;
        }
        Arena a = new Arena(name);
        arenas.add(a);
        p.sendMessage(ChatColor.GREEN + "Created arena " + name + "!");
        return;
    }

    public void deleteArena(Player p, String name) {
        if (getArena(name) == null) {
            p.sendMessage(ChatColor.RED + "That arena doesn't exist!");
            return;
        }
        getArena(name).delete();
        ArenaData.getInstance().save();
        arenas.remove(name);
        p.sendMessage(ChatColor.GREEN + "Deleted arena " + name + "!");
        return;
    }

    public void setUpArena(Player p, String name) {
        if (getArena(name) == null) {
            p.sendMessage(ChatColor.RED + "That arena doesnt exist!");
            return;
        }
        getArena(name).setup();
        p.sendMessage(ChatColor.GREEN + "You have finished setting up arena " + name + "!");
        return;
    }


    public void setSpawn(Player p, String name, int num) {
        if (getArena(name) == null) {
            p.sendMessage(ChatColor.RED + "That arena doesn't exist!");
            return;
        }
        if (num == 1) {
            getArena(name).setSpawn1(p.getLocation());
            p.sendMessage(ChatColor.GREEN + "You have set spawn 1 for arena " + name + "!");
        } else if (num == 2) {
            getArena(name).setSpawn2(p.getLocation());
            p.sendMessage(ChatColor.GREEN + "You have set spawn 2 for arena " + name + "!");
        } else {
            p.sendMessage(ChatColor.RED + "/arena setspawn <arena> <1:2>");
        }
    }

    public void setUP() {
        if (ArenaData.getInstance().<ConfigurationSection>get("Arenas") == null) {
            ArenaData.getInstance().createConfigurationSection("Arenas");
        }
        for (String name : ArenaData.getInstance().<ConfigurationSection>get("Arenas").getKeys(false)) {
            Location spawn1 = LocationUtility.getLocation("Arenas." + name + ".spawn1");
            Location spawn2 = LocationUtility.getLocation("Arenas." + name + ".spawn2");
            Arena a = new Arena(name,spawn1,spawn2);
            arenas.add(a);
        }
    }

    public Arena getRandomArena() {
        Random rand = new Random();
        int index = rand.nextInt(arenas.size());
        return arenas.get(index);
    }

}

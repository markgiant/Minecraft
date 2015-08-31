package me.giantcrack.bpvp.que;

import me.giantcrack.bpvp.arenas.ArenaManager;
import me.giantcrack.bpvp.listeners.InventoryHandler;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoot_000 on 6/25/2015.
 */
public abstract class Que extends BukkitRunnable {

    private List<Player> players = new ArrayList<>();
    private Kit k;
    private QueType type;

    public List<Player> getPlayers() {
        return players;
    }

    public Que(Kit k, QueType type) {
        this.k = k;
        this.type = type;
    }

    public QueType getQueType() {
        return type;
    }

    public Kit getKit() {
        return k;
    }

    public String getType() {
        if (type == QueType.UNRANKED) {
            return "UNRANKED";
        } else {
            return "RANKED";
        }

    }

    public void removePlayer(Player p) {
        if (!players.contains(p)) return;
        players.remove(p);
        if (InventoryHandler.search.containsKey(p)) {
            InventoryHandler.search.remove(p);
        }
        if (InventoryHandler.search.containsKey(p)) {
            InventoryHandler.search.remove(p);
        }
        if (InventoryHandler.task.containsKey(p)) {
            InventoryHandler.task.get(p).cancel();
            InventoryHandler.task.remove(p);
        }
        if (InventoryHandler.task.containsKey(p)) {
            InventoryHandler.task.get(p).cancel();
            InventoryHandler.task.remove(p);
        }
        p.sendMessage(ChatColor.GREEN + "You have left the " + ChatColor.YELLOW + getType() + ChatColor.GREEN + " queue for kit " + k.getName() + "!");
        return;
    }


    public void addPlayer(Player p) {
        if (players.contains(p)) {
            removePlayer(p);
            return;
        }
        if (ArenaManager.getInstance().getRandomArena() == null || ArenaManager.getInstance().arenas.isEmpty()) {
            p.sendMessage(ChatColor.RED + "No arenas have been setup!");
            return;
        }
        if (TeamManager.getInstance().getTeam(p).getMembers().size() > 1) {
            p.sendMessage(ChatColor.RED + "You cannot be on a team and join a queue!");
            return;
        }
        if (QueManager.getInstance().getQue(p) != null) {
            QueManager.getInstance().getQue(p).removePlayer(p);
        }
        players.add(p);
        p.sendMessage(ChatColor.RED + "Leave the queue with '/leave' or click on the icon again!");
        p.sendMessage(ChatColor.GREEN + "You have been added to the " + ChatColor.YELLOW + getType() + ChatColor.GREEN + " queue for kit " + k.getName() + "!");
        return;
    }


}

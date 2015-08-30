package me.giantcrack.bpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoot_000 on 4/19/2015.
 */
public class PvPCmd implements CommandExecutor, Listener {

    @EventHandler
    public void onBlockBreakPvP(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (pvp.contains(p.getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("blocks.break")) {
            e.setCancelled(true);
        }
    }

    public static List<String> pvp = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
                             String commandLabel, String[] args) throws NullPointerException {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to execute this command!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("pvp")) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "No permission!");
                return true;
            }
            if (pvp.contains(p.getName())) {
                pvp.remove(p.getName());
                p.sendMessage(ChatColor.RED
                        + "Removed from pvp mode!");
                return true;
            }
            pvp.add(p.getName());
            p.sendMessage(ChatColor.GREEN + "You have been put into pvp mode!");
            return true;
        }
        return false;
    }
}

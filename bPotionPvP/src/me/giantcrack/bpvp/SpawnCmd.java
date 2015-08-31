package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.DuelManager;
import me.giantcrack.bpvp.duels.DuelState;
import me.giantcrack.bpvp.listeners.InventoryHandler;
import me.giantcrack.bpvp.teams.TeamManager;
import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 * Created by shoot_000 on 7/15/2015.
 */
public class SpawnCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (DuelManager.getInstance().getDuel(p) != null && DuelManager.getInstance().getDuel(p).getState() == DuelState.ENDING) {
                p.sendMessage(ChatColor.RED + "You can't teleport to spawn while the match is ending!");
                return true;
            } else if (DuelManager.getInstance().getDuel(p) != null) {
                DuelManager.getInstance().getDuel(p).getOtherTeam(p).sendMessage(ChatColor.GREEN + p.getName() + " teleported to spawn!");
                DuelManager.getInstance().getDuel(p).getTracker().addDeath(p);
                p.teleport(LocationUtility.getLocation("Spawn"));
                p.sendMessage(ChatColor.GREEN + "Welcome to spawn!");
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setFireTicks(0);
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                for (PotionEffect pe : p.getActivePotionEffects()) {
                    p.removePotionEffect(pe.getType());
                }
                if (TeamManager.getInstance().getTeam(p).isJoinable()) {
                    InventoryHandler.giveTeamItems(p);
                } else {
                    InventoryHandler.giveDefaultItems(p);
                }
                p.updateInventory();
                return true;
            } else {
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setFireTicks(0);
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                for (PotionEffect pe : p.getActivePotionEffects()) {
                    p.removePotionEffect(pe.getType());
                }
                p.teleport(LocationUtility.getLocation("Spawn"));
                p.sendMessage(ChatColor.GREEN + "Welcome to spawn!");
                if (TeamManager.getInstance().getTeam(p).isJoinable()) {
                    InventoryHandler.giveTeamItems(p);
                } else {
                    InventoryHandler.giveDefaultItems(p);
                }
                p.updateInventory();
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("setspawn")) {
            LocationUtility.saveLocation("Spawn", p.getLocation());
            p.sendMessage(ChatColor.GREEN + "Spawn set at your location!");
            return true;
        }
        return false;
    }
}

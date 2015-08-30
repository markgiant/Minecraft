package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.InventoryHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by shoot_000 on 7/17/2015.
 */
public class StatsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");
            return true;
        }
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("stats")) {
            InventoryHandler.showStats(p);
            return true;
        }
        return false;
    }
}

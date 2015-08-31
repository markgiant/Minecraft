
package me.giantcrack.mb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LookupCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("lookup")) {
            if (!sender.hasPermission("mattban.staff")) {
                sender.sendMessage(Main.prefix + ChatColor.DARK_RED + "No Permission!");
                return true;
            }
            if ((args.length == 0) || (args.length > 1)) {
                sender.sendMessage(Main.prefix + ChatColor.GOLD + "/lookup <username>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (args[0].contains(".")) {
                if (PlayerManager.getInstance().getData(args[0]) == null) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "That IP was not found!");
                    return true;
                }
                sender.sendMessage(PlayerManager.getInstance().getData(args[0]).toString());
                return true;
            }
            if (target != null) {
                if (PlayerManager.getInstance().getData(target) == null) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "That player has never joined!");
                    return true;
                }
                sender.sendMessage(PlayerManager.getInstance().getData(target).toString());
                return true;
            } else {
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                if (PlayerManager.getInstance().getDataFromName(op.getName()) == null) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "That player has never joined!");
                    return true;
                }
                sender.sendMessage(PlayerManager.getInstance().getDataFromName(op.getName()).toString());
                return true;
            }
        }
        return false;

    }

}


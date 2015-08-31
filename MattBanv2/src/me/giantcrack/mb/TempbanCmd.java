package me.giantcrack.mb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempbanCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tempban")) {
            if (!sender.hasPermission("mattban.staff")) {
                sender.sendMessage(Main.prefix + ChatColor.DARK_RED + "No permission!");
                return true;
            }
            if ((args.length == 0) || (args.length < 2)) {
                sender.sendMessage(Main.prefix + ChatColor.AQUA + "/tempban <username> <amount>");
                sender.sendMessage(Main.prefix + ChatColor.AQUA + "Ip Bans Automatically!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            String amount = args[1];
            int seconds = 0;
            if (amount.indexOf("s") > -1) {
                seconds = Integer.valueOf(amount.replace("s", "")).intValue();
            } else if (amount.indexOf("mm") > -1) {
                seconds = Integer.valueOf(amount.replace("mm", "")).intValue() * 2678400;
            } else if (amount.indexOf("h") > -1) {
                seconds = Integer.valueOf(amount.replace("h", "")).intValue() * 3600;
            } else if (amount.indexOf("d") > -1) {
                seconds = Integer.valueOf(amount.replace("d", "")).intValue() * 86400;
            } else if (amount.indexOf("w") > -1) {
                seconds = Integer.valueOf(amount.replace("w", "")).intValue() * 604800;
            } else if (amount.indexOf("m") > -1) {
                seconds = Integer.valueOf(amount.replace("m", "")).intValue() * 60;
            } else {
                sender.sendMessage(Main.prefix + ChatColor.RED + "Invalid Time Format!");
                return true;
            }
            if (target == null) {
                OfflinePlayer otarget = Bukkit.getOfflinePlayer(args[0]);
                if (PlayerManager.getInstance().getData(otarget) == null) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "That player has never joined!");
                    return true;
                }
                if ((sender instanceof Player)) {
                    TempbanManager.getInstance().banPlayer((Player) sender, otarget, seconds);
                    return true;
                }
                TempbanManager.getInstance().banPlayer(otarget, seconds);
                return true;
            }
            if ((sender instanceof Player)) {
                TempbanManager.getInstance().banPlayer((Player) sender, target, seconds);
                return true;
            }
            TempbanManager.getInstance().banPlayer(target, seconds);
            return true;
        }
        return false;
    }
}


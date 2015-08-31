package me.giantcrack.mb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ban")) {
            if (!sender.hasPermission("mattban.staff")) {
                sender.sendMessage(Main.prefix + ChatColor.DARK_RED + "No permission!");
                return true;
            }
            if ((args.length == 0) || (args.length < 2)) {
                sender.sendMessage(Main.prefix + ChatColor.AQUA + "/ban <username> <reason>");
                sender.sendMessage(Main.prefix + ChatColor.AQUA + "Ip Bans Automatically!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            String reason = args[1];
            for (int i = 2; i < args.length; i++) {
                reason = reason + " " + args[i];
            }
            if (target == null) {
                OfflinePlayer otarget = Bukkit.getOfflinePlayer(args[0]);
                if (PlayerManager.getInstance().getData(otarget) == null) {
                    sender.sendMessage(Main.prefix + ChatColor.RED + "That player has never joined!");
                    return true;
                }
                if ((sender instanceof Player)) {
                    BanManager.getInstance().banPlayer((Player) sender, otarget, reason);
                    return true;
                }
                BanManager.getInstance().banPlayer(otarget, reason);
                return true;
            }
            if ((sender instanceof Player)) {
                BanManager.getInstance().banPlayer((Player) sender, target, reason);
                return true;
            }
            BanManager.getInstance().banPlayer(target, reason);
            return true;
        }
        return false;
    }

}

package me.giantcrack.mb;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by markvolkov on 7/29/15.
 */
public class ResetCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("proxyreset")) {
            if (!commandSender.isOp()) {
                commandSender.sendMessage(Main.prefix + "No perms!");
                return true;
            }
            commandSender.sendMessage(Main.prefix + ChatColor.GREEN + "Resetting ips!");
            new BukkitRunnable() {
                @Override
                public void run() {
                    PlayerManager.getInstance().resetAltsAndIps();
                }
            }.runTaskAsynchronously(Main.getInstance());
            commandSender.sendMessage(Main.prefix + ChatColor.GREEN + "Ips have been reset!");
            return true;
        }
        return false;
    }
}

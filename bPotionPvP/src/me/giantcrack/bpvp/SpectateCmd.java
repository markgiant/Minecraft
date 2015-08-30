package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by shoot_000 on 7/16/2015.
 */
public class SpectateCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");
            return true;
        }
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("spectate")) {
            if (args.length == 0 || args.length > 1) {
                p.sendMessage(ChatColor.RED + "/spectate <name>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatColor.RED + "That player was not found!");
                return true;
            }
            if (DuelManager.getInstance().getDuel(target) == null) {
                p.sendMessage(ChatColor.RED + "That player isn't in a duel!");
                return true;
            }
            DuelManager.getInstance().getDuel(target).spectate(p,target);
            return true;
        }
        return false;
    }
}

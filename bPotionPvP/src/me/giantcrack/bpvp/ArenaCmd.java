package me.giantcrack.bpvp;

import me.giantcrack.bpvp.arenas.ArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by shoot_000 on 6/27/2015.
 */
public class ArenaCmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender,Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("arena")) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "No permission!");
                return true;
            }
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Arena Commands");
                p.sendMessage(ChatColor.AQUA + "/arena create <arena>");
                p.sendMessage(ChatColor.AQUA + "/arena setspawn <arena> <1:2>");
                p.sendMessage(ChatColor.AQUA + "/arena delete <arena>");
                p.sendMessage(ChatColor.AQUA + "/arena setup <arena>");
                return true;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
                ArenaManager.getInstance().createArena(p,args[1]);
                return true;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                ArenaManager.getInstance().deleteArena(p,args[1]);
                return true;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("setup")) {
                ArenaManager.getInstance().setUpArena(p,args[1]);
                return true;
            }
            if (args.length == 3 && args[0].equalsIgnoreCase("setspawn")) {
                ArenaManager.getInstance().setSpawn(p,args[1],Integer.valueOf(args[2]));
                return true;
            }
        } else {
            p.sendMessage(ChatColor.RED + "Arena Commands");
            p.sendMessage(ChatColor.AQUA + "/arena create <arena>");
            p.sendMessage(ChatColor.AQUA + "/arena setspawn <arena> <1:2>");
            p.sendMessage(ChatColor.AQUA + "/arena delete <arena>");
            p.sendMessage(ChatColor.AQUA + "/arena setup <arena>");
            return true;
        }
        return false;
    }
}

package me.giantcrack.bpvp.duels;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by markvolkov on 8/29/15.
 */
public class DICmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player)commandSender;
        if (command.getName().equalsIgnoreCase("di")) {
            if (strings.length == 1) {
                Player target = Bukkit.getPlayer(strings[0]);
                if (target == null) {
                    p.sendMessage(ChatColor.RED + "That inventory either doesnt exist or has expired!");
                    return true;
                }
                DuelInventory di = InventoryManager.get().getInv(target);
                if (di == null) {
                    p.sendMessage(ChatColor.RED + "That inventory either doesnt exist or has expired!");
                    return true;
                }
                di.display(p);
                return true;
            }
        }
        return false;
    }
}

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
                DuelInventory di = InventoryManager.get().getInv(Bukkit.getPlayer(strings[0]));
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

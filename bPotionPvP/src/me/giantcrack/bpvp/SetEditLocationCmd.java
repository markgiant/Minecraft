package me.giantcrack.bpvp;

import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.kits.KitManager;
import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by shoot_000 on 7/15/2015.
 */
public class SetEditLocationCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player!");
            return true;
        }
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("setedit")) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "No perms");
                return true;
            }
            if (args.length == 0 || args.length > 1) {
                p.sendMessage(ChatColor.RED + "/setedit <kit>");
                p.sendMessage(ChatColor.RED + "Options: NoDebuff, Debuff,Archer,Gapple,MCSG,Soup");
                return true;
            }
            Kit k = KitManager.getInstance().getKit(args[0]);
            if (k == null) {
                p.sendMessage(ChatColor.RED + "/setedit <kit>");
                p.sendMessage(ChatColor.RED + "Options: NoDebuff, Debuff,Archer,Gapple,MCSG,Soup");
                return true;
            }
            LocationUtility.saveLocation(k.getName(),p.getLocation());
            p.sendMessage(ChatColor.RED + "Edit location changed!");
            return true;
         }
        return false;
    }
}

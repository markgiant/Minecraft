package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.InventoryHandler;
import me.giantcrack.bpvp.que.QueManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by markvolkov on 7/25/15.
 */
public class LeaveCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
                             String commandLabel, String[] args) throws NullPointerException {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to execute this command!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("leave")) {
           if (QueManager.getInstance().getQue(p) != null) {
               QueManager.getInstance().getQue(p).removePlayer(p);
               if (InventoryHandler.search.containsKey(p)) {
                   InventoryHandler.search.remove(p);
               }
               if (InventoryHandler.task.containsKey(p)) {
                   InventoryHandler.task.get(p).cancel();
                   InventoryHandler.task.remove(p);
               }
               return true;
           } else {
               p.sendMessage(ChatColor.RED + "You are not in a queue!");
           }
        }
        return false;
    }
}

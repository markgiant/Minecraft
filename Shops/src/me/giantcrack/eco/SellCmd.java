package me.giantcrack.eco;

import me.giantcrack.gs.ItemManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SellCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		boolean isPlayer = sender instanceof Player;
		if (!isPlayer) {
			sender.sendMessage(ChatColor.RED + "You must be in game to do this command!");
			return true;
		}
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("sell")) {
			ItemManager.getInstance().sellItem(p);
			return true;
		}
		return false;
	}

}

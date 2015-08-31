package me.giantcrack.gs;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditItemCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		boolean isPlayer = sender instanceof Player;
		if (!isPlayer) {
			sender.sendMessage(ChatColor.RED + "You must be in game to do this command!");
			return true;
		}
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("edit") && args.length != 2) {
			p.sendMessage(ChatColor.GREEN + "/edit <item> <price> - Edits the item specified to the specified price!");
			return true;
		}
		double price;
		if (args.length == 2) {
			if (ItemManager.getInstance().getItem(args[0]) == null) {
				p.sendMessage(ChatColor.RED + "That item is not in the shop!");
				return true;
			}
			try {
				price = Double.parseDouble(args[1]);
				ItemManager.getInstance().getItem(args[0]).setPrice(price);
				ItemManager.getInstance().getItem(args[0]).save();
				p.sendMessage(ChatColor.GREEN + "You have successfuly changed the price of " + args[0] + " to " + price + "!");
				return true;
			} catch (NumberFormatException ex) {
				p.sendMessage(ChatColor.RED + args[1] + " is not a valid price!");
				return true;
			}
		}
		return false;
	}

}

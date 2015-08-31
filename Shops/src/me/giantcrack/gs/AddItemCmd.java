package me.giantcrack.gs;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddItemCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		boolean isPlayer = sender instanceof Player;
		if (!isPlayer) {
			sender.sendMessage(ChatColor.RED + "You must be in game to do this command!");
			return true;
		}
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("add") && args.length == 0) {
			p.sendMessage(ChatColor.GREEN + "/add <price> - Adds the item in your hand to the shop!");
			return true;
		}
		double price;
		if (args.length == 1) {
			try {
				price = Double.parseDouble(args[0]);
				ItemManager.getInstance().createItem(p, price);
				//p.sendMessage(ChatColor.GREEN + "You have added " + ItemManager.getInstance().getItem(p.getItemInHand()).toString() + " to the shop for " + price + " dollars!");
				return true;
			} catch (NumberFormatException ex) {
				p.sendMessage(ChatColor.RED + args[0] + " is not a valid price!");
				return true;
			}
		}
		return false;
	}
	
}

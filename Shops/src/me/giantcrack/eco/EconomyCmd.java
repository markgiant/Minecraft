package me.giantcrack.eco;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EconomyCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("economy")) {
			sender.sendMessage("§e===== §a§lEconomy §e=====");
			sender.sendMessage("§a/Buy §7- §aPurchase an item off of the market.");
			sender.sendMessage("§a/Sell §7- §aSell an item onto the market.");
			sender.sendMessage("§a/Price §7- §aFind out the price of an item.");
			sender.sendMessage("§a/Value §7- §aFind out the value of an item.");
			sender.sendMessage("§a/Balance §7- §aView your current balance.");
			sender.sendMessage("§e================================");
			return true;
		}
		return false;
	}

}

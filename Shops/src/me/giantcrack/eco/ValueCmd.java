package me.giantcrack.eco;

import me.giantcrack.gs.ItemManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ValueCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		boolean isPlayer = sender instanceof Player;
		if (!isPlayer) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command!");
			return true;
		}
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("value")) {
			if (args.length == 0) {
				if (ItemManager.getInstance().getItem(p.getItemInHand()) == null) {
					p.sendMessage(ChatColor.RED + "The item in your hand can't be bought or sold!");
					return true;
				}
				if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
					p.sendMessage(ChatColor.RED + "The Air Is Free!");
					return true;
				}
				p.sendMessage(ChatColor.GREEN + "Price of " + p.getItemInHand().toString() + ": " + ChatColor.LIGHT_PURPLE + ItemManager.getInstance().getItem(p.getItemInHand()).getPrice());
				return true;
			}
			if (args.length == 1) {
				if (ItemManager.getInstance().getItem(args[0]) == null) {
					p.sendMessage(ChatColor.RED + "The item in your hand can't be bought or sold!");
					return true;
				}
				if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
					p.sendMessage(ChatColor.RED + "The Air Is Free!");
					return true;
				}
				p.sendMessage(ChatColor.GREEN + "Price of " + ItemManager.getInstance().getItem(args[0]).toString() + ": " + ChatColor.LIGHT_PURPLE + ItemManager.getInstance().getItem(args[0]).getPrice());
				return true;
			}
		}
		return false;
	}

}

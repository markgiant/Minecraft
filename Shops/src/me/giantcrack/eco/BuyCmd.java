package me.giantcrack.eco;

import me.giantcrack.gs.ItemManager;
import me.giantcrack.gs.ShopItem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class BuyCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		boolean isPlayer = sender instanceof Player;
		if (!isPlayer) {
			sender.sendMessage(ChatColor.RED + "You must be in game to do this command!");
			return true;
		}
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("buy") && args.length == 0) {
			p.sendMessage(ChatColor.GREEN + "/buy <itemname> <amount>");
			return true;
		}
		if (args.length == 1) {
			ItemManager.getInstance().buyItem(p, args[0], 1);
			return true;
		}
		if (args.length == 2) {
			int amount;
			try {
				amount = Integer.parseInt(args[1]);
				ItemManager.getInstance().buyItem(p, args[0], amount);
			}catch (NumberFormatException ex) {
				p.sendMessage(ChatColor.RED + args[1] + " is not a number!");
				return true;
			}
		}
		return false;
	}
}

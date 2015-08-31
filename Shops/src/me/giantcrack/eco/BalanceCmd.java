package me.giantcrack.eco;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		boolean isPlayer = sender instanceof Player;
		if (!isPlayer) {
			sender.sendMessage(ChatColor.RED + "You must be in game to do that command");
			return true;
		}
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("bal") || cmd.getName().equalsIgnoreCase("balance")) {
			p.sendMessage(ChatColor.GREEN + "Balance: " + ChatColor.LIGHT_PURPLE + EcoFile.getInstance().getBalance(p));
			return true;
		}
		return false;
	}

}

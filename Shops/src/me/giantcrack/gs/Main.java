package me.giantcrack.gs;

import me.giantcrack.eco.BalanceCmd;
import me.giantcrack.eco.BuyCmd;
import me.giantcrack.eco.EcoFile;
import me.giantcrack.eco.EconomyCmd;
import me.giantcrack.eco.SellCmd;
import me.giantcrack.eco.ValueCmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		getCommand("buy").setExecutor(new BuyCmd());
		getCommand("sell").setExecutor(new SellCmd());
		getCommand("economy").setExecutor(new EconomyCmd());
		getCommand("value").setExecutor(new ValueCmd());
		getCommand("balance").setExecutor(new BalanceCmd());
		getCommand("add").setExecutor(new AddItemCmd());
		getCommand("edit").setExecutor(new EditItemCmd());
		if (Config.getInstance().get("buysell") == null) {
			Config.getInstance().set("buysell", 0.5);
		}
		ItemManager.getInstance().setUp();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onLoad() {
		EcoFile.getInstance().setup(this);
		ConverterFile.getInstance().setup(this);
		Config.getInstance().setup(this);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (EcoFile.getInstance().get("Economy." + e.getPlayer().getUniqueId() + ".balance") == null) {
			EcoFile.getInstance().setBalance(e.getPlayer(), 0.0);
		}
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("convertitems")) {
			if (sender.isOp()) {
				ConverterFile.getInstance().convertOldConfig();
				sender.sendMessage(ChatColor.GREEN + "File Converted!");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
		}
		return false;
	}
	
	
	/***
	 * TODO LISt
	 *1.Finish Commands
	 *2.Test
	 *3.Do lava plugin
	 */
	
	
	
	

}

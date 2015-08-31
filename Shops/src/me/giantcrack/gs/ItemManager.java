package me.giantcrack.gs;

import java.util.ArrayList;
import java.util.List;

import me.giantcrack.eco.EcoFile;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
	
	private static ItemManager instance = new ItemManager();
	
	public static ItemManager getInstance() {
		return instance;
	}
	
	private ItemManager() {}
	
	public List<ShopItem> items = new ArrayList<ShopItem>();
	
	public List<ShopItem> getItems() {
		return items;
	}
	
	
	public void createItem(Player p, double price) {
		if (p.getItemInHand().getType() == Material.AIR)  {
			p.sendMessage(ChatColor.RED + "You cannot sell air...");
			return;
		}
		if (getItem(p.getItemInHand()) != null) {
			p.sendMessage(ChatColor.RED + "That is already an item in the shop, edit it with /edit <item> <price> or in the config!");
			return;
		}
		ShopItem i = new ShopItem(p.getItemInHand(), price);
		items.add(i);
		i.save();
		Config.getInstance().save();
		p.sendMessage(ChatColor.GREEN + "You have added the item " + i.toString() + " to the global shop with a price of " + i.getPrice());
		return;
	}
	
	public void buyItem(Player p, String name, int amount) {
		ShopItem si = getItem(name);
		if (si == null) {
			p.sendMessage(ChatColor.RED + "That item is not for sale!");
			return;
		}
		if (EcoFile.getInstance().getBalance(p) < si.getPrice() * amount) {
			p.sendMessage(ChatColor.RED + "Not enough money! You need " + ((si.getPrice() * amount) - EcoFile.getInstance().getBalance(p)) + " more dollars!");
			return;
		}
		if (isInventoryFull(p, si.getI())) {
			p.sendMessage(ChatColor.YELLOW + "There is not enough room in your inventory!");
			return;
		}
		EcoFile.getInstance().removeFromBal(p, si.getPrice() * amount);
		EcoFile.getInstance().save();
		p.sendMessage(ChatColor.GREEN + "Transaction Successful - You have bought " + si.getIdentifier().toUpperCase() + " x" + amount + " for " + si.getPrice()*amount);
		p.getInventory().addItem(new ItemStack(si.getI().getType(), amount, si.getSubID()));
		return;
	}
	

	public boolean isInventoryFull(Player p, ItemStack item) {
		for (int i = 0; i<p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) == null || p.getInventory().getItem(i).getType() == item.getType() && p.getInventory().getItem(i).getAmount() < item.getMaxStackSize()) {
				return false;
			}
		}
		return true;
	}
	
	public ShopItem getItem(String identifier) {
		for (ShopItem si : items) {
			if (si.getIdentifier().equalsIgnoreCase(identifier)) {
				return si;
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void sellItem(Player p) {
		if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
			p.sendMessage(ChatColor.RED + "You must be holding something in your hand!");
			return;
		}
		ShopItem si = getItem(p.getItemInHand());
		if (si == null) {
			p.sendMessage(ChatColor.RED + "That item is not for sale!");
			return;
		}
		int amount = p.getItemInHand().getAmount();
		EcoFile.getInstance().addToBal(p, getSellPrice(si, amount));
		EcoFile.getInstance().save();
		p.sendMessage(ChatColor.GREEN + "Transaction Successful - You have sold " + si.getIdentifier().toUpperCase() + " x" + amount + " for " + getSellPrice(si, amount));
		p.setItemInHand(new ItemStack(Material.AIR));
		p.updateInventory();
		return;
	}
	
	public double buysell = Config.getInstance().getDouble("buysell");
	
	public double getSellPrice(ShopItem i, int amount) {
		return (i.getPrice()*buysell) * amount;
	}
	
	public ShopItem getItem(ItemStack i) {
		for (ShopItem si : items) {
			if (si.getI().getType() == i.getType() && si.getI().getDurability() == i.getDurability()) {
				return si;
			}
		}
		return null;
	}

	
	public void setUp() {
		items.clear();
		for (String itemName : Config.getInstance().<ConfigurationSection>get("ShopItems").getKeys(false)) {
			double price = Config.getInstance().getDouble("ShopItems." + itemName + ".price");
			short subID = (short) Config.getInstance().getInt("ShopItems." + itemName + ".subID");
			String identifier = Config.getInstance().getString("ShopItems." + itemName + ".identifier");
			ShopItem si = new ShopItem(convertToItem(itemName), price);
			si.setSubID(subID);
			si.setIdentifier(identifier);
			items.add(si);
		}
	}
	
	public ItemStack convertToItem(String s) {
		ItemStack i = null;
		try{
		i = new ItemStack(Material.getMaterial(s), 1);
		}catch (Exception ex) {
			i = new ItemStack(Material.AIR);
		}
		return i;
	}
	
	public ItemStack convertToItem(Player p,String s) {
		ItemStack i = null;
		try{
		i = new ItemStack(Material.getMaterial(s), 1);
		}catch (Exception ex) {
			p.sendMessage(ChatColor.RED + s + " is not a valid material!");
		}
		return i;
	}

}

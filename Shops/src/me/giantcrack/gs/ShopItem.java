package me.giantcrack.gs;

import org.bukkit.inventory.ItemStack;

public class ShopItem {
	
	private ItemStack i;
	private short subID;
	private double price;
	private String identifier;
	
	public ShopItem(ItemStack i,double price) {
		this.i = i;
		this.price = price;
		this.subID = i.getDurability();
		this.identifier = i.getType().toString().replace("_", "");
	}
	
	public String toString() {
		return i.getType().name();
	}
	
	public void setIdentifier(String s) {
		this.identifier = s;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setSubID(short s) {
		this.subID = s;
	}
	public short getSubID() {
		return subID;
	}

	public void save() {
		Config.getInstance().set("ShopItems." + toString() + ".price", price);
		Config.getInstance().set("ShopItems." + toString() + ".subID", subID);
		Config.getInstance().set("ShopItems." + toString() + ".identifier", identifier);
		Config.getInstance().save();
	}
	
	public ItemStack getI() {
		return i;
	}
	public void setI(ItemStack i) {
		this.i = i;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	

}

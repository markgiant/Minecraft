package me.giantcrack.fm;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shoot_000 on 6/15/2015.
 */
public class InventoryItem {

    private int id;
    private ItemStack item;
    private String description;
    public static List<InventoryItem> items = new ArrayList<>();

    public static InventoryItem getItem(int id) {
        for (InventoryItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public InventoryItem(Material mat, int amount, short data, String name, List<String> lore, String description) {
        this.id = this.items.size();
        this.items.add(this);
        this.item = new ItemStack(mat,amount,data);
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        this.item.setItemMeta(meta);
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<InventoryItem> getItems() {
        return items;
    }

    public static void setItems(List<InventoryItem> items) {
        InventoryItem.items = items;
    }
}

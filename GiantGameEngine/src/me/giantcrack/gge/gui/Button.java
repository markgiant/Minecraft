package me.giantcrack.gge.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by markvolkov on 8/7/15.
 */
public abstract class Button {

    private String name;
    private ItemStack item;

    public Button(String name, ItemStack item) {
        this.name = name;
        ItemStack newItem = item.clone();
        ItemMeta meta = newItem.getItemMeta();
        meta.setDisplayName(name);
        newItem.setItemMeta(meta);
        this.item = newItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        return item;
    }
    public abstract void onClick(Player whoClicked);
}

package me.giantcrack.bpvp.kits;

import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by shoot_000 on 7/15/2015.
 */
public class Soup implements Kit {

    @Override
    public String getName() {
        return "Soup";
    }

    @Override
    public void giveDefaultKit(Player p) {
        ItemStack helm = new ItemStack(Material.IRON_HELMET);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack legg = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boot = new ItemStack(Material.IRON_BOOTS);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL,1);
        ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
        p.getInventory().setHelmet(helm);
        p.getInventory().setChestplate(chest);
        p.getInventory().setLeggings(legg);
        p.getInventory().setBoots(boot);
        p.getInventory().clear();
        p.getInventory().setItem(0,sword);
        for (int i = 0; i < p.getInventory().getSize();i++) {
            if (p.getInventory().getItem(i) == null) {
                p.getInventory().setItem(i,soup);
            }
        }
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.sendMessage(ChatColor.GREEN + "Default kit loaded!");
    }

    @Override
    public ItemStack getIcon() {
        ItemStack chest = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta meta = chest.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        chest.setItemMeta(meta);
        return chest;
    }


    @Override
    public Location getEditLocation() {
        return LocationUtility.getLocation(getName());
    }
}

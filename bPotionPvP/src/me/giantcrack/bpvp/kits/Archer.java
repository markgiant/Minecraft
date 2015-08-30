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
public class Archer implements Kit {

    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public void giveDefaultKit(Player p) {
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack legg = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1);
        legg.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1);
        boot.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1);
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE,2);
        bow.addEnchantment(Enchantment.ARROW_INFINITE,1);
        ItemStack arrow = new ItemStack(Material.ARROW,1);
        ItemStack food = new ItemStack(Material.COOKED_BEEF,64);
        p.getInventory().setHelmet(helm);
        p.getInventory().setChestplate(chest);
        p.getInventory().setLeggings(legg);
        p.getInventory().setBoots(boot);
        p.getInventory().clear();
        p.getInventory().setItem(0,bow);
        p.getInventory().setItem(1,food);
        p.getInventory().setItem(9,arrow);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.sendMessage(ChatColor.GREEN + "Default kit loaded!");
    }

    @Override
    public ItemStack getIcon() {
        ItemStack chest = new ItemStack(Material.BOW);
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

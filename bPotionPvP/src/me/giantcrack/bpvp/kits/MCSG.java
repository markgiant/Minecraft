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
public class MCSG implements Kit {

    @Override
    public String getName() {
        return "MCSG";
    }

    @Override
    public void giveDefaultKit(Player p) {
        ItemStack helm = new ItemStack(Material.GOLD_HELMET);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack legg = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemStack boot = new ItemStack(Material.IRON_BOOTS);
        ItemStack flint = new ItemStack(Material.FLINT_AND_STEEL);
        flint.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        ItemStack bread = new ItemStack(Material.BREAD,1);
        ItemStack melon = new ItemStack(Material.MELON,2);
        ItemStack pie = new ItemStack(Material.PUMPKIN_PIE,2);
        ItemStack carrot = new ItemStack(Material.GOLDEN_CARROT);
        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack arrow = new ItemStack(Material.ARROW,8);
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        p.getInventory().setHelmet(helm);
        p.getInventory().setChestplate(chest);
        p.getInventory().setLeggings(legg);
        p.getInventory().setBoots(boot);
        p.getInventory().clear();
        p.getInventory().setItem(0,sword);
        p.getInventory().setItem(1,bow);
        p.getInventory().setItem(2,rod);
        p.getInventory().setItem(3,bread);
        p.getInventory().setItem(4,melon);
        p.getInventory().setItem(5,apple);
        p.getInventory().setItem(6,carrot);
        p.getInventory().setItem(7,pie);
        p.getInventory().setItem(8,arrow);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.sendMessage(ChatColor.GREEN + "Default kit loaded!");
        return;
    }

    @Override
    public ItemStack getIcon() {
        ItemStack chest = new ItemStack(Material.FLINT_AND_STEEL);
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

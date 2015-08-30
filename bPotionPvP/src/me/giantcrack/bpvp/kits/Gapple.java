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
public class Gapple implements Kit {

    @Override
    public String getName() {
        return "Gapple";
    }

    @Override
    public void giveDefaultKit(Player p) {
        ItemStack helm = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemStack legg = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemStack speed = new ItemStack(Material.POTION, 1, (short) 8290);
        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE,64,(short)1);
        ItemStack strength = new ItemStack(Material.POTION, 1, (short) 8297);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        chest.addEnchantment(Enchantment.DURABILITY, 3);
        legg.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        legg.addEnchantment(Enchantment.DURABILITY, 3);
        boot.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        boot.addEnchantment(Enchantment.DURABILITY, 3);
        boot.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        p.getInventory().setHelmet(helm);
        p.getInventory().setChestplate(chest);
        p.getInventory().setLeggings(legg);
        p.getInventory().setBoots(boot);
        p.getInventory().clear();
        p.getInventory().setItem(0,sword);
        p.getInventory().setItem(1,apple);
        p.getInventory().setItem(2,speed);
        p.getInventory().setItem(3,strength);
        p.getInventory().setItem(4,speed);
        p.getInventory().setItem(5,strength);
        p.getInventory().setItem(9,helm);
        p.getInventory().setItem(10,chest);
        p.getInventory().setItem(11,legg);
        p.getInventory().setItem(12,boot);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.sendMessage(ChatColor.GREEN + "Default kit loaded!");
        return;
    }

    @Override
    public Location getEditLocation() {
        return LocationUtility.getLocation(getName());
    }

    @Override
    public ItemStack getIcon() {
        ItemStack chest = new ItemStack(Material.GOLDEN_APPLE,1,(short)1);
        ItemMeta meta = chest.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        chest.setItemMeta(meta);
        return chest;
    }
}

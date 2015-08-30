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
 * Created by shoot_000 on 7/14/2015.
 */
public class Debuff implements Kit {

    @Override
    public String getName() {
        return "Debuff";
    }

    @Override
    public void giveDefaultKit(Player p) {
        ItemStack helm = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemStack legg = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemStack boot = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemStack food = new ItemStack(Material.GOLDEN_CARROT, 64);
        ItemStack pearls = new ItemStack(Material.ENDER_PEARL, 16);
        ItemStack fireRes = new ItemStack(Material.POTION, 1, (short) 8259);
        ItemStack spiid = new ItemStack(Material.POTION, 1, (short) 8226);
        ItemStack heiith = new ItemStack(Material.POTION, 1, (short) 16421);
        ItemStack poison = new ItemStack(Material.POTION, 1, (short) 16388);
        ItemStack slow = new ItemStack(Material.POTION, 1, (short) 16394);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        chest.addEnchantment(Enchantment.DURABILITY, 3);
        legg.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        legg.addEnchantment(Enchantment.DURABILITY, 3);
        boot.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        boot.addEnchantment(Enchantment.DURABILITY, 3);
        boot.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        p.getInventory().setHelmet(helm);
        p.getInventory().setChestplate(chest);
        p.getInventory().setLeggings(legg);
        p.getInventory().setBoots(boot);
        p.getInventory().clear();
        p.getInventory().setItem(0,sword);
        p.getInventory().setItem(6,fireRes);
        p.getInventory().setItem(5,spiid);
        p.getInventory().setItem(7,food);
        p.getInventory().setItem(8,pearls);
        p.getInventory().setItem(35,spiid);
        p.getInventory().setItem(26,spiid);
        p.getInventory().setItem(17,spiid);
        p.getInventory().setItem(1,poison);
        p.getInventory().setItem(28,poison);
        p.getInventory().setItem(2,slow);
        p.getInventory().setItem(29,slow);
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (p.getInventory().getItem(i) == null) {
                p.getInventory().setItem(i, heiith);
            }
        }
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.sendMessage(ChatColor.GREEN + "Default kit loaded!");
        return;
    }

    @Override
    public ItemStack getIcon() {
        ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        chest.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
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

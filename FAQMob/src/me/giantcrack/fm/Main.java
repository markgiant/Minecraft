package me.giantcrack.fm;

import net.minecraft.server.v1_8_R2.EntityVillager;

import net.minecraft.server.v1_8_R2.EntityVillager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * Created by shoot_000 on 6/15/2015.
 */
public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        new NMSUtils().registerEntity("Villager", 120, EntityVillager.class, ClassVillager.class);
        Items.getInstance().setup(this);
        if (Items.getInstance().<ConfigurationSection>get("Items") == null) {
            Items.getInstance().createConfigurationSection("Items." + 0);
            Items.getInstance().set("Items." + 0 + ".itemid", 276);
            Items.getInstance().set("Items." + 0 + ".itemname", "&bDefault name");
            Items.getInstance().set("Items." + 0 + ".amount", 1);
            Items.getInstance().set("Items." + 0 + ".durability", 0);
            Items.getInstance().set("Items." + 0 + ".lore", Arrays.asList("&6Default lore"));
            Items.getInstance().set("Items." + 0 + ".description", "&8Default message!");
            Items.getInstance().save();
            return;
        }
        loadItems();
        return;
    }

    public void loadItems() {
        for (String id : Items.getInstance().<ConfigurationSection>get("Items").getKeys(false)) {
            int i = Integer.valueOf(id);
            ItemStack item = new ItemStack(Material.getMaterial(Items.getInstance().getInt("Items." + id + ".itemid")), Items.getInstance().getInt("Items." + id + ".amount"), (short) Items.getInstance().getInt("Items." + id + ".durability"));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Items.getInstance().getString("Items." + id + ".itemname").replace("&", "§"));
            for (String s : Items.getInstance().getList("Items." + id + ".lore")) {
                meta.setLore(Arrays.asList(s.replace("&", "§")));
            }
            String description = Items.getInstance().getString("Items." + id + ".description").replace("&", "§");
            item.setItemMeta(meta);
            InventoryItem ii = new InventoryItem(item.getType(), item.getAmount(), item.getDurability(), item.getItemMeta().getDisplayName(), item.getItemMeta().getLore(), description);
        }
    }

    @EventHandler
    public void onRightClickVillager(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Villager) {
            Player p = e.getPlayer();
            Villager v = (Villager) e.getRightClicked();
            if (v.getCustomName() == null) return;
            if (v.getCustomName().contains(ChatColor.stripColor("Right Click For Info!"))) {
                if (p.isOp() && p.isSneaking()) {
                    e.setCancelled(true);
                    v.remove();
                    return;
                }
                e.setCancelled(true);
                openFaqMenu(p);
            }
        }
    }

    public void openFaqMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 18, ChatColor.GREEN + "FAQ and Info!");
        for (InventoryItem item : InventoryItem.getItems()) {
            inv.setItem(item.getId(), item.getItem());
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (!e.getInventory().getName().contains(ChatColor.stripColor("FAQ and Info!"))) return;
        e.setCancelled(true);
        if (e.getCurrentItem() != null) {
            InventoryItem item = InventoryItem.getItem(e.getRawSlot());
            if (item != null) {
                p.closeInventory();
                p.sendMessage(item.getDescription());
                return;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player!");
            return true;
        }
        Player p = (Player) sender;
        if (command.getName().equalsIgnoreCase("spawnfaq")) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "No perms!");
                return true;
            }
            Villager v = ClassVillager.spawn(p.getLocation(), ChatColor.RED + "" + ChatColor.BOLD + "Right Click For Info!");
            v.setCanPickupItems(false);
            v.setRemoveWhenFarAway(false);
            p.sendMessage(ChatColor.RED + "Spawned A FAQ Villager!");
            return true;
        }
        return false;
    }
}

package me.giantcrack.mad;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by markvolkov on 7/25/15.
 */
public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < p.getInventory().getSize(); i++) {
                                if (p.getInventory().getItem(i).getAmount() == 0) {
                                    p.getInventory().setItem(i, new ItemStack(Material.AIR));
                                    continue;
                                }
                                p.getInventory().setItem(i, p.getInventory().getItem(i));
                            }
                            p.updateInventory();
                        }catch (NullPointerException ex) {

                        }
                    }
                }, 1);
            }
        }
    }


    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
       final Player p = (Player)e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < p.getInventory().getSize(); i++) {
                        if (p.getInventory().getItem(i).getAmount() == 0) {
                            p.getInventory().setItem(i, new ItemStack(Material.AIR));
                            continue;
                        }
                        p.getInventory().setItem(i, p.getInventory().getItem(i));
                    }
                    p.updateInventory();
                }catch (NullPointerException ex) {

                }
            }
        }, 1);
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        final Player p = (Player) e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < p.getInventory().getSize(); i++) {
                        if (p.getInventory().getItem(i).getAmount() == 0) {
                            p.getInventory().setItem(i, new ItemStack(Material.AIR));
                            continue;
                        }
                        p.getInventory().setItem(i, p.getInventory().getItem(i));
                    }
                    p.updateInventory();
                }catch (NullPointerException ex) {

                }
            }
        }, 1);
    }

}

package me.giantcrack.bs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoot_000 on 6/22/2015.
 */
public class Events implements Listener {


    public static List<Player> staff = new ArrayList<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (!Main.getInstance().isStaff(p)) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (Main.getInstance().isStaff(online)) continue;
                    online.hidePlayer(p);
                }
            }
        },2);
        p.setAllowFlight(true);
        p.setFlying(true);
        staff.add(p);
        return;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (staff.contains(e.getPlayer())) {
            staff.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        if (staff.contains(e.getPlayer())) {
            staff.remove(e.getPlayer());
        }
    }



    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (Main.getInstance().isStaff(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (Main.getInstance().isStaff(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (Main.getInstance().isStaff(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        if (Main.getInstance().isStaff(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (Main.getInstance().isStaff(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        if (!Main.getInstance().isStaff(e.getPlayer())) return;
        if (e.getRightClicked() instanceof Player) {
            Player right = (Player)e.getRightClicked();
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.YELLOW + "That is " + ChatColor.RED + right.getName());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (Main.getInstance().isStaff(e.getPlayer())) {
            e.setCancelled(true);
        }
    }



    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player || e.getDamager() instanceof Player) {
            Player p = (Player)e.getEntity();
            Player d = (Player)e.getDamager();
            if (Main.getInstance().isStaff(p) || Main.getInstance().isStaff(d)) {
                e.setCancelled(true);
            }
        }
    }

}

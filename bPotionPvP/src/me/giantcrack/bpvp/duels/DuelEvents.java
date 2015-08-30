package me.giantcrack.bpvp.duels;

import me.giantcrack.bpvp.GlassTimer;
import me.giantcrack.bpvp.Main;
import me.giantcrack.bpvp.PvPCmd;
import me.giantcrack.bpvp.kits.KitManager;
import me.giantcrack.bpvp.que.QueManager;
import me.giantcrack.bpvp.teams.TeamManager;
import me.giantcrack.bpvp.utilities.LocationUtility;
import net.minecraft.server.v1_7_R4.MobEffectList;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shoot_000 on 7/15/2015.
 */
public class DuelEvents implements Listener {

    //Hiding potions and shit
    //Handling deaths
    //Handling leaving and being kicked
    //Right clicking books


    @EventHandler
    public void onTp(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && DuelManager.getInstance().getDuel(p) == null || e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && DuelManager.getInstance().getDuel(p).getState() != DuelState.INGAME) {
            e.setCancelled(true);
            return;
        }
    }


    @EventHandler
    public void onGlitchMove(PlayerMoveEvent e) {
        if ((DuelManager.getInstance().getDuel(e.getPlayer()) != null && e.getTo().getBlock().getType() == Material.GLASS) || (DuelManager.getInstance().getDuel(e.getPlayer()) != null && e.getPlayer().getLocation().getBlock().getType() == Material.GLASS)) {
            e.setTo(e.getFrom());
            if (GlassTimer.getTimer(e.getPlayer()) != null) return;
            GlassTimer.timers.add(new GlassTimer(e.getPlayer()));
            //Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(),GlassTimer.getTimer(e.getPlayer()),0,5);
            return;
        } else if (e.getTo().getBlock().getType() == Material.AIR && GlassTimer.getTimer(e.getPlayer()) != null) {
            GlassTimer.getTimer(e.getPlayer()).end();
            return;
        }
    }

    @EventHandler
    public void onGlitch(final PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && DuelManager.getInstance().getDuel(p) != null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (p.getLocation().getBlock().getType() == Material.GLASS) {
                        if (GlassTimer.getTimer(e.getPlayer()) != null) return;
                        GlassTimer.timers.add(new GlassTimer(e.getPlayer()));
                    }
                }
            }, 2);
        }
    }

    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    /**
     * Converts a rotation to a cardinal direction name.
     *
     * @param
     * @return
     */

    private static String getDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return "North";
        } else if (22.5 <= rot && rot < 67.5) {
            return "Northeast";
        } else if (67.5 <= rot && rot < 112.5) {
            return "East";
        } else if (112.5 <= rot && rot < 157.5) {
            return "Southeast";
        } else if (157.5 <= rot && rot < 202.5) {
            return "South";
        } else if (202.5 <= rot && rot < 247.5) {
            return "Southwest";
        } else if (247.5 <= rot && rot < 292.5) {
            return "West";
        } else if (292.5 <= rot && rot < 337.5) {
            return "Northwest";
        } else if (337.5 <= rot && rot < 360.0) {
            return "North";
        } else {
            return null;
        }
    }

    public boolean shouldAddX(Player p) {
        Location newLoc = new Location(p.getWorld(), p.getLocation().getX() - 1, p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
        int xminus = 0;
        for (int i = 0; i < 200; i++) {
            //Location newLoc = new Location(Bukkit.getWorld(p.getWorld().getName()),p.getLocation().getX()-1,p.getLocation().getY(),p.getLocation().getZ(),p.getLocation().getYaw(),p.getLocation().getPitch());
            newLoc.setY(newLoc.getY() - i);
            if (newLoc.getBlock().getType() == Material.AIR) {
                xminus++;
            }
        }
        return xminus == 200;
    }


    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (((event.getEntity() instanceof ThrownPotion)) && ((event.getEntity().getShooter() instanceof Player))) {
            Player shooter = (Player) event.getEntity().getShooter();
            Duel duel = DuelManager.getInstance().getDuel(shooter);
            if (duel != null) {
                Iterator<LivingEntity> i = event.getAffectedEntities().iterator();
                while (i.hasNext()) {
                    LivingEntity affected = (LivingEntity) i.next();
                    if ((!duel.getTeam1().getMembers().contains(Bukkit.getPlayer(affected.getUniqueId()))) || (!duel.getTeam2().getMembers().contains(Bukkit.getPlayer(affected.getUniqueId())))) {
                        event.getAffectedEntities().remove(affected);
                    }
                    for (int a = 0; a < duel.getTeam1().getMembers().size(); a++) {
                        Player entity = duel.getTeam1().getMembers().get(a);
                        for (PotionEffect effect : event.getPotion().getEffects()) {
                            if (effect.getType().isInstant())
                                MobEffectList.byId[effect.getType().getId()].applyInstantEffect(((CraftLivingEntity) shooter).getHandle(), ((CraftLivingEntity) entity).getHandle(), effect.getAmplifier(), event.getIntensity(entity));
                            else {
                                int j = (int) (event.getIntensity(entity) * effect.getDuration() + 0.5D);
                                if (j > 20) {
                                    entity.addPotionEffect(new PotionEffect(effect.getType(), j, effect.getAmplifier()));
                                }
                            }

                        }
                    }
                    for (int a = 0; a < duel.getTeam2().getMembers().size(); a++) {
                        Player entity = duel.getTeam2().getMembers().get(a);
                        for (PotionEffect effect : event.getPotion().getEffects()) {
                            if (effect.getType().isInstant())
                                MobEffectList.byId[effect.getType().getId()].applyInstantEffect(((CraftLivingEntity) shooter).getHandle(), ((CraftLivingEntity) entity).getHandle(), effect.getAmplifier(), event.getIntensity(entity));
                            else {
                                int j = (int) (event.getIntensity(entity) * effect.getDuration() + 0.5D);
                                if (j > 20) {
                                    entity.addPotionEffect(new PotionEffect(effect.getType(), j, effect.getAmplifier()));
                                }
                            }

                        }
                    }

                    event.setCancelled(true);
                }

            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                p.teleport(LocationUtility.getLocation("Spawn"));
                if (TeamManager.getInstance().getTeam(p).getOwner().getName().equals(p.getName()) && TeamManager.getInstance().getTeam(p).isJoinable()) {
                    InventoryHandler.giveTeamItems(p);
                } else {
                    InventoryHandler.giveDefaultItems(p);
                }
            }
        }, 2);
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.CHEST) {
                if (!PvPCmd.pvp.contains(p.getName()) && p.isOp()) return;
                if (e.getClickedBlock().getState() instanceof Chest) {
                    e.setCancelled(true);
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    Inventory inv = Bukkit.createInventory(e.getPlayer(), 54, ChatColor.GOLD + "Kit Items");
                    inv.setContents(chest.getInventory().getContents());
                    p.openInventory(inv);
                }
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                p.teleport(LocationUtility.getLocation("Spawn"));
                if (p.isOp() && !PvPCmd.pvp.contains(p.getName())) {
                    p.performCommand("pvp");
                }
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                TeamManager.getInstance().createTeam(p);
                if (TeamManager.getInstance().getTeam(p).isJoinable()) {
                    InventoryHandler.giveTeamItems(p);
                } else {
                    InventoryHandler.giveDefaultItems(p);
                }
                for (Duel d : DuelManager.getInstance().duels) {
                    for (Player team1 : d.getTeam1().getMembers()) {
                        if (d.getHider().canSee(team1,p)) {
                            d.getHider().hideEntity(team1,p);
                        }
                    }
                    for (Player team2 : d.getTeam2().getMembers()) {
                        if (d.getHider().canSee(team2,p)) {
                            d.getHider().hideEntity(team2,p);
                        }
                    }
                }
            }
        }, 1);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
//        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),new Runnable() {
//            @Override
//            public void run() {
        if (QueManager.getInstance().getQue(p) != null) {
            QueManager.getInstance().getQue(p).removePlayer(p);
        }
        if (TeamManager.getInstance().getTeam(p).getOwner().getName().equals(p.getName()) && TeamManager.getInstance().getTeam(p).isJoinable()) {
            TeamManager.getInstance().getTeam(p).setJoinable(false);
//                    InventoryHandler.giveDefaultItems(p);
//                    p.sendMessage(ChatColor.RED + "You have disbanded your team!");
            try {
                Iterator<Player> i = TeamManager.getInstance().getTeam(p).getMembers().iterator();
                while (i.hasNext()) {
                    Player pl = i.next();
                    if (pl.isOnline() && pl != null && !pl.getName().equals(p.getName())) {
                        i.remove();
                        TeamManager.getInstance().createTeam(pl);
                        InventoryHandler.giveDefaultItems(pl);
                        pl.sendMessage(ChatColor.RED + "Your team has been disbanded!");
                    }
                }
            } catch (NullPointerException ex) {

            }
        }
        if (DuelManager.getInstance().getDuel(p) != null) {
            DuelManager.getInstance().getDuel(p).getTracker().addDeath(p);
        }
    }
    // },1);
    // }


    @EventHandler
    public void onPlayerSoup(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() == null) return;
            if (!(player.getHealth() == player.getMaxHealth()) && e.getItem().getType() == Material.MUSHROOM_SOUP) {
                player.setHealth(player.getHealth() + 7 > player.getMaxHealth() ? player.getMaxHealth() : player.getHealth() + 7);
                e.getItem().setType(Material.BOWL);
            }
        }
    }


    @EventHandler
    public void onLeave(PlayerKickEvent e) {
        final Player p = e.getPlayer();
//        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),new Runnable() {
//            @Override
//            public void run() {
        if (QueManager.getInstance().getQue(p) != null) {
            QueManager.getInstance().getQue(p).removePlayer(p);
        }
        if (TeamManager.getInstance().getTeam(p).getOwner().getName().equals(p.getName()) && TeamManager.getInstance().getTeam(p).isJoinable()) {
            TeamManager.getInstance().getTeam(p).setJoinable(false);
//                    InventoryHandler.giveDefaultItems(p);
//                    p.sendMessage(ChatColor.RED + "You have disbanded your team!");
            try {
                Iterator<Player> i = TeamManager.getInstance().getTeam(p).getMembers().iterator();
                while (i.hasNext()) {
                    Player pl = i.next();
                    if (pl.isOnline() && pl != null && !pl.getName().equals(p.getName())) {
                        i.remove();
                        TeamManager.getInstance().createTeam(pl);
                        InventoryHandler.giveDefaultItems(pl);
                        pl.sendMessage(ChatColor.RED + "Your team has been disbanded!");
                    }
                }
            } catch (NullPointerException ex) {

            }
        }
        if (DuelManager.getInstance().getDuel(p) != null) {
            DuelManager.getInstance().getDuel(p).getTracker().addDeath(p);
        }
    }

    @EventHandler
    public void onEntitySpawn(ProjectileLaunchEvent e) {
        final Projectile entity = e.getEntity();
        if (entity instanceof ThrownPotion) {
            Player p = (Player) e.getEntity().getShooter();
            if (DuelManager.getInstance().getDuel(p) == null) return;
            try {
                for (Player online : DuelManager.getInstance().getDuel(p).getA().getArenaPlayers()) {
                    // if (!DuelManager.getInstance().getDuel(p).getOtherPlayer(p).getName().equals(online.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(online)).getOtherTeam(TeamManager.getInstance().getTeam(online)).getMembers().contains(online.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(p)).getOtherTeam(TeamManager.getInstance().getTeam(p)).getMembers().contains(online.getName())) {
                    if (online.getName().equals(p.getName())) continue;
                    if (DuelManager.getInstance().getDuel(p).getTeam(p).getMembers().contains(online) || DuelManager.getInstance().getDuel(p).getOtherTeam(p).getMembers().contains(online))
                        continue;
                    DuelManager.getInstance().getDuel(p).getHider().hideEntity(online, entity);
                    Main.getInstance().getHiddenEntities().put(online.getName(), entity.getEntityId());
                }
            } catch (NullPointerException ex) {

            }
        } else if (entity instanceof Arrow) {
            Player p = (Player) e.getEntity().getShooter();
            try {
                for (Player online : DuelManager.getInstance().getDuel(p).getA().getArenaPlayers()) {
                    //if (!DuelManager.getInstance().getDuel(p).getOtherPlayer(p).getName().equals(online.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(online)).getOtherTeam(TeamManager.getInstance().getTeam(online)).getMembers().contains(online.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(p)).getOtherTeam(TeamManager.getInstance().getTeam(p)).getMembers().contains(online.getName())) {
                    if (online.getName().equals(p.getName())) continue;
                    if (DuelManager.getInstance().getDuel(p).getTeam(p).getMembers().contains(online) || DuelManager.getInstance().getDuel(p).getOtherTeam(p).getMembers().contains(online))
                        continue;
                    DuelManager.getInstance().getDuel(p).getHider().hideEntity(online, entity);
                    Main.getInstance().getHiddenEntities().put(online.getName(), entity.getEntityId());
                }
            } catch (NullPointerException ex) {

            }
        } else if (entity instanceof EnderPearl) {
            Player p = (Player) e.getEntity().getShooter();
            try {
                for (Player online : DuelManager.getInstance().getDuel(p).getA().getArenaPlayers()) {
                    //if (!DuelManager.getInstance().getDuel(p).getOtherPlayer(p).getName().equals(online.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(online)).getOtherTeam(TeamManager.getInstance().getTeam(online)).getMembers().contains(online.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(p)).getOtherTeam(TeamManager.getInstance().getTeam(p)).getMembers().contains(online.getName())) {
                    if (online.getName().equals(p.getName())) continue;
                    if (DuelManager.getInstance().getDuel(p).getTeam(p).getMembers().contains(online) || DuelManager.getInstance().getDuel(p).getOtherTeam(p).getMembers().contains(online))
                        continue;
                    DuelManager.getInstance().getDuel(p).getHider().hideEntity(online, entity);
                    Main.getInstance().getHiddenEntities().put(online.getName(), entity.getEntityId());
                }
            } catch (NullPointerException ex) {

            }
        }
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if (DuelManager.getInstance().getDuel(damaged) == null || DuelManager.getInstance().getDuel(damager) == null)
                return;
            //if (!DuelManager.getInstance().getDuel(damager).getOtherPlayer(damager).getName().equals(damaged.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(damager)).getOtherTeam(TeamManager.getInstance().getTeam(damager)).getMembers().contains(damaged.getName())) {
            if (DuelManager.getInstance().getDuel(damager).getTeam(damager).getMembers().contains(damaged) || !DuelManager.getInstance().getDuel(damager).getOtherTeam(damager).getMembers().contains(damaged) || DuelManager.getInstance().getDuel(damaged).getState() != DuelState.INGAME) {
                e.setCancelled(true);
                return;
            }
        }
        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
            Arrow a = (Arrow) e.getDamager();
            Player damaged = (Player) e.getEntity();
            Player shooter = (Player) a.getShooter();
            if (DuelManager.getInstance().getDuel(damaged) == null || DuelManager.getInstance().getDuel(shooter) == null)
                return;
            // if (!DuelManager.getInstance().getDuel(shooter).getOtherPlayer(shooter).getName().equals(damaged.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(shooter)).getOtherTeam(TeamManager.getInstance().getTeam(shooter)).getMembers().contains(damaged.getName())) {
            if (DuelManager.getInstance().getDuel(shooter).getTeam(shooter).getMembers().contains(damaged) || !DuelManager.getInstance().getDuel(shooter).getOtherTeam(shooter).getMembers().contains(damaged) || DuelManager.getInstance().getDuel(damaged).getState() != DuelState.INGAME) {
                e.setCancelled(true);
                return;
            }
        }
        if (e.getDamager() instanceof ThrownPotion && e.getEntity() instanceof Player) {
            ThrownPotion tp = (ThrownPotion) e.getDamager();
            Player damaged = (Player) e.getEntity();
            Player shooter = (Player) tp.getShooter();
            if (DuelManager.getInstance().getDuel(damaged) == null || DuelManager.getInstance().getDuel(shooter) == null)
                return;
            //if (!DuelManager.getInstance().getDuel(shooter).getOtherPlayer(shooter).getName().equals(damaged.getName()) || !DuelManager.getInstance().getDuel(TeamManager.getInstance().getTeam(shooter)).getOtherTeam(TeamManager.getInstance().getTeam(shooter)).getMembers().contains(damaged.getName())) {
            if (DuelManager.getInstance().getDuel(shooter).getTeam(shooter).getMembers().contains(damaged) || !DuelManager.getInstance().getDuel(shooter).getOtherTeam(shooter).getMembers().contains(damaged) || DuelManager.getInstance().getDuel(damaged).getState() != DuelState.INGAME) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onRightClickBook(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().getType() == Material.BOOK) {
                if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.stripColor("Default"))) {
                    if (DuelManager.getInstance().getDuel(p) != null) {
                        KitManager.getInstance().getKit(DuelManager.getInstance().getDuel(p).getK().getName()).giveDefaultKit(p);
                        return;
                    }
                }
                if (DuelManager.getInstance().getDuel(p) != null) {
                    if (e.getItem() != null && e.getItem().getType() == Material.BOOK) {
                        try {
                            int id = Integer.valueOf(ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()));
                            KitManager.getInstance().getKitEdit(p, DuelManager.getInstance().getDuel(p).getK(), id).applyKit(p);
                            p.sendMessage(ChatColor.LIGHT_PURPLE + "Loaded kit " + id);
                        } catch (NullPointerException ex) {
                            KitManager.getInstance().getKit(DuelManager.getInstance().getDuel(p).getK().getName()).giveDefaultKit(p);
                        }
                    }
                }
            }
        }
    }

    public void respawn(Player p) {
        if (TeamManager.getInstance().getTeam(p).getOwner().getName().equals(p.getName()) && TeamManager.getInstance().getTeam(p).isJoinable()) {
            InventoryHandler.giveTeamItems(p);
        } else {
            InventoryHandler.giveDefaultItems(p);
        }
    }


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (DuelManager.getInstance().getDuel(p) == null) return;
        for (Player online : DuelManager.getInstance().getDuel(p).getA().getArenaPlayers()) {
            if (DuelManager.getInstance().getDuel(p).getTeam(p).getMembers().contains(online) || DuelManager.getInstance().getDuel(p).getOtherTeam(p).getMembers().contains(online)) continue;
            DuelManager.getInstance().getDuel(p).getHider().hideEntity(online,e.getItemDrop());
            Main.getInstance().getHiddenEntities().put(online.getName(), e.getItemDrop().getEntityId());
        }
    }

    //TODO: Fix other bugs then work on enderpearl anti glitch

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        final Player p = (Player) e.getEntity();
        InventoryManager.get().createDeathInv(p,e.getDrops());
        e.getDrops().clear();
        if (DuelManager.getInstance().getDuel(p) == null) return;
        if (DuelManager.getInstance().getDuel(p).getTeam1().isJoinable() && DuelManager.getInstance().getDuel(p).getTeam2().isJoinable()) {
            DuelManager.getInstance().getDuel(p).getTeam1().sendMessage(ChatColor.RED + "Death: " + ChatColor.GREEN + p.getName());
            DuelManager.getInstance().getDuel(p).getTeam2().sendMessage(ChatColor.RED + "Death: " + ChatColor.GREEN + p.getName());
        }
        DuelManager.getInstance().getDuel(p).getTracker().addDeath(p);
        e.setDeathMessage(null);
        p.setHealth(20);
        p.teleport(LocationUtility.getLocation("Spawn"));
        new BukkitRunnable() {
            @Override
            public void run() {
                respawn(p);
            }
        }.runTaskLater(Main.getInstance(), 2);
    }

}

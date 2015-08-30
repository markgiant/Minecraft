package me.giantcrack.bpvp.listeners;

import me.giantcrack.bpvp.Main;
import me.giantcrack.bpvp.elo.EloSign;
import me.giantcrack.bpvp.elo.SignManager;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by shoot_000 on 6/27/2015.
 */
public class SignListener implements Listener {

    private List<String> spamClick = new ArrayList<>();
    private Map<Player, Kit> edit = new HashMap<>();

    @EventHandler
    public void onSignChangeSave(SignChangeEvent e) {
        Player p = e.getPlayer();
        if (e.getLine(0).equalsIgnoreCase(ChatColor.stripColor("Save"))
                && KitManager.getInstance().getKit(e.getLine(1)) == null) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            p.getWorld().dropItemNaturally(e.getBlock().getLocation(),
                    new ItemStack(Material.SIGN, 1));
            p.sendMessage(ChatColor.RED + e.getLine(1)
                    + " is not a valid kit!");
            return;
        }
        if (e.getLine(0).equalsIgnoreCase(ChatColor.stripColor("Save"))
                && KitManager.getInstance().getKit(e.getLine(1)) != null) {
            e.setLine(2, ChatColor.AQUA + e.getLine(2));
            return;
        }
    }

    @EventHandler
    public void onEloSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        if (KitManager.getInstance().getKit(e.getLine(0)) != null) {
            SignManager.get().createSign(KitManager.getInstance().getKit(e.getLine(0)),e.getBlock().getLocation());
            SignManager.get().getSign(e.getBlock().getLocation()).save();
            SignManager.get().getSign(e.getBlock().getLocation()).update();
            p.sendMessage(ChatColor.GREEN + "Sign Created!");
        }
    }

    @EventHandler
    public void onSignBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (SignManager.get().getSign(e.getBlock().getLocation()) == null) return;
        SignManager.get().removeSign(e.getBlock().getLocation());
        p.sendMessage(ChatColor.RED + "Elo Sign Removed.");
    }



    public void openSaveMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.YELLOW + "Save Kits");
        for (int i = 2; i <= 6; i++) {
            ItemStack book = new ItemStack(Material.BOOK);
            ItemMeta meta = book.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + String.valueOf(i - 1));
            meta.setLore(Arrays.asList(ChatColor.RED + "Click to save!"));
            book.setItemMeta(meta);
            inv.setItem(i, book);
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onSaveClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName().contains(ChatColor.YELLOW + "Save Kits")) {
            e.setCancelled(true);
            switch (e.getRawSlot()) {
                case 2:
                    p.closeInventory();
                    KitManager.getInstance().saveKit(p, edit.get(p), e.getRawSlot() - 1);
                    edit.remove(p);
                    break;
                case 3:
                    p.closeInventory();
                    KitManager.getInstance().saveKit(p, edit.get(p), e.getRawSlot() - 1);
                    edit.remove(p);
                    break;
                case 4:
                    p.closeInventory();
                    KitManager.getInstance().saveKit(p, edit.get(p), e.getRawSlot() - 1);
                    edit.remove(p);
                    break;
                case 5:
                    p.closeInventory();
                    KitManager.getInstance().saveKit(p, edit.get(p), e.getRawSlot() - 1);
                    edit.remove(p);
                    break;
                case 6:
                    p.closeInventory();
                    KitManager.getInstance().saveKit(p, edit.get(p), e.getRawSlot() - 1);
                    edit.remove(p);
                    break;
            }
        }
    }

    @EventHandler
    public void onInteractSave(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            e.setCancelled(true);
            Sign s = (Sign) e.getClickedBlock().getState();
            if (s.getLine(0).equalsIgnoreCase(ChatColor.stripColor("Save"))) {
                if (spamClick.contains(p.getName())) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "Don't spam click signs!");
                    return;
                }
                if (edit.containsKey(p)) {
                    edit.remove(p);
                }
                e.setCancelled(true);
                openSaveMenu(p);
                Kit k = KitManager.getInstance().getKit(s.getLine(1));
                if (k != null) {
                    edit.put(p, KitManager.getInstance().getKit(s.getLine(1)));
                    spamClick.add(p.getName());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
                            new Runnable() {

                                @Override
                                public void run() {
                                    spamClick.remove(p.getName());

                                }

                            }, 20 * 3);
                }
            }
        }
    }

    @EventHandler
    public void onInteractSpawn(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            e.setCancelled(true);
            Sign s = (Sign) e.getClickedBlock().getState();
            if (s.getLine(1).equalsIgnoreCase(ChatColor.stripColor("spawn"))) {
                if (spamClick.contains(p.getName())) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "Don't spam click signs!");
                    return;
                }
                e.setCancelled(true);
                p.performCommand("spawn");
                spamClick.add(p.getName());
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
                        new Runnable() {

                            @Override
                            public void run() {
                                spamClick.remove(p.getName());

                            }

                        }, 20 * 3);
            }
        }
    }


}

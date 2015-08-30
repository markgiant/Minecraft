package me.giantcrack.bpvp.duels;

import me.giantcrack.bpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

/**
 * Created by markvolkov on 8/29/15.
 */
public class DuelInventory extends BukkitRunnable {

    private Player p;

    private Inventory inv;

    public DuelInventory(Player p, Collection<ItemStack> stacks) {
        this.p = p;
        this.inv = Bukkit.createInventory(null,54, ChatColor.RED + "> " + p.getName());
        fill(stacks);
        runTaskLaterAsynchronously(Main.getInstance(),20*60);
    }

    public void fill(Collection<ItemStack> itemStacks) {
        for (ItemStack i : itemStacks) {
            if (i == null || i.getType() == Material.AIR) continue;
            inv.addItem(i);
        }
    }

    @Override
    public void run() {
        InventoryManager.get().invs.remove(p.getName());
    }

    public void display(Player p) {
        p.openInventory(inv);
    }

    public Player getP() {
        return p;
    }
}

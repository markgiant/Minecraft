package me.giantcrack.bpvp.duels;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by markvolkov on 8/29/15.
 */
public class InventoryManager {

    private static final InventoryManager instance = new InventoryManager();

    private InventoryManager() {}

    public static InventoryManager get() {
        return instance;
    }

    public Map<String, DuelInventory> invs = new HashMap<>();

    public DuelInventory getInv(Player p) {
        DuelInventory inv = invs.get(p.getName());
        if (inv != null) return inv;
        return null;
    }

    public void createDeathInv(Player p,Collection<ItemStack> items) {
        if (getInv(p) != null) return;
        invs.put(p.getName(),new DuelInventory(p,items));
        return;
    }
}

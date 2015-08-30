package me.giantcrack.ii;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by shoot_000 on 6/19/2015.
 */
public class InsurancePolicy {


    private UUID p;
    private List<ItemStack> items;

    public InsurancePolicy(UUID p) {
        this.p = p;
        this.items = new ArrayList<>();
        InsuranceManager.getInstance().policies.add(this);
    }

    public void save() {
        if (items.isEmpty()) return;
        Main.getInstance().getConfig().createSection("InsurancePolicy." + p.toString());
        for (int i = 0; i < items.size(); i++) {
            Main.getInstance().getConfig().set("InsurancePolicy." + p.toString() + "." + i,items.get(i));
        }
        Main.getInstance().saveConfig();
    }

    public void restore(Player p,int index) {
        items.remove(items.get(index));
        Main.getInstance().getConfig().set("InsurancePolicy." + p.toString() + "." + index,null);
        save();
    }

    public void delete() {
        InsuranceManager.getInstance().policies.remove(this);
    }

    public void add(ItemStack i) {
        items.add(i);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Insured");
        i.setItemMeta(meta);
        save();
    }

    public UUID getP() {
        return p;
    }

    public void setP(UUID p) {
        this.p = p;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }
}

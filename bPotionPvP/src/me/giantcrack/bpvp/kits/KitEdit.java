package me.giantcrack.bpvp.kits;

import me.giantcrack.bpvp.files.*;
import me.giantcrack.bpvp.files.Archer;
import me.giantcrack.bpvp.files.Gapple;
import me.giantcrack.bpvp.files.MCSG;
import me.giantcrack.bpvp.files.NoDebuff;
import me.giantcrack.bpvp.files.Debuff;
import me.giantcrack.bpvp.files.Soup;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by shoot_000 on 7/18/2015.
 */
public class KitEdit {

    private UUID holder;
    private Kit kit;
    private int id;
    private List<ItemStack> inv,armor;

    public KitEdit(Player p, Kit k, int id) {
        this.holder = p.getUniqueId();
        this.kit = k;
        this.id = id;
        this.inv = new ArrayList<>();
        this.armor = new ArrayList<>();
        for (ItemStack i : p.getInventory().getContents()) {
            inv.add(i);
        }
        for (ItemStack i : p.getInventory().getArmorContents()) {
            armor.add(i);
        }
    }

    public KitEdit(UUID uuid, Kit k, int id, List<ItemStack> inv, List<ItemStack> armor) {
        this.holder = uuid;
        this.kit = k;
        this.id = id;
        this.inv = inv;
        this.armor = armor;
    }


    public void applyKit(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        ItemStack[] inv = new ItemStack[this.inv.size()];
        this.inv.toArray(inv);
        p.getInventory().setContents(inv);
        ItemStack[] armor = new ItemStack[this.armor.size()];
        this.armor.toArray(armor);
        p.getInventory().setArmorContents(armor);
    }

    public void save() {
       // KitData.getInstance().createConfigurationSection("KitEdit." + holder.toString() + "." + kit.getName() + "." + id);
//        KitData.getInstance().set("KitEdit." + holder.toString() + ".kit", kit.getName());
//        KitData.getInstance().set("KitEdit." + holder.toString()+ "." + kit.getName() + ".id",id);
        //KitData.getInstance().set("KitEdit." + holder.toString() + "." + kit.getName() + "." + id + ".inv",inv);
       // KitData.getInstance().set("KitEdit." + holder.toString() + "." + kit.getName() + "." + id + ".armor",armor);
       // KitData.getInstance().save();
        if (kit.getName().equalsIgnoreCase("nodebuff")) {
            NoDebuff.getInstance().createConfigurationSection("KitEdit." + holder.toString() + "." + String.valueOf(id));
//            NoDebuff.getInstance().set("KitEdit." + holder.toString() + ".id",id);
            NoDebuff.getInstance().set("KitEdit." + holder.toString() + "." + id + ".inv",this.inv);
            NoDebuff.getInstance().set("KitEdit." + holder.toString() + "." + id + ".armor",this.armor);
            NoDebuff.getInstance().save();
        } else if (kit.getName().equalsIgnoreCase("debuff")) {
            Debuff.getInstance().createConfigurationSection("KitEdit." + holder.toString() + "." + String.valueOf(id));
           // Debuff.getInstance().set("KitEdit." + holder.toString() + ".id",id);
            Debuff.getInstance().set("KitEdit." + holder.toString() + "." + id + ".inv",this.inv);
            Debuff.getInstance().set("KitEdit." + holder.toString() + "." + id + ".armor",this.armor);
            Debuff.getInstance().save();
        } else if (kit.getName().equalsIgnoreCase("archer")) {
            Archer.getInstance().createConfigurationSection("KitEdit." + holder.toString() + "." + String.valueOf(id));
           // Archer.getInstance().set("KitEdit." + holder.toString() + ".id",id);
            Archer.getInstance().set("KitEdit." + holder.toString() + "." + id + ".inv",this.inv);
            Archer.getInstance().set("KitEdit." + holder.toString() + "." + id + ".armor",this.armor);
            Archer.getInstance().save();
        } else if (kit.getName().equalsIgnoreCase("mcsg")) {
            MCSG.getInstance().createConfigurationSection("KitEdit." + holder.toString() + "." + String.valueOf(id));
          //  MCSG.getInstance().set("KitEdit." + holder.toString() + ".id",id);
            MCSG.getInstance().set("KitEdit." + holder.toString() + "." + id + ".inv",this.inv);
            MCSG.getInstance().set("KitEdit." + holder.toString() + "." + id + ".armor",this.armor);
            MCSG.getInstance().save();
        } else if (kit.getName().equalsIgnoreCase("soup")) {
            Soup.getInstance().createConfigurationSection("KitEdit." + holder.toString() + "." + String.valueOf(id));
           // Soup.getInstance().set("KitEdit." + holder.toString() + ".id",id);
            Soup.getInstance().set("KitEdit." + holder.toString() + "." + id + ".inv",this.inv);
            Soup.getInstance().set("KitEdit." + holder.toString() + "." + id + ".armor",this.armor);
            Soup.getInstance().save();
        } else {
            Gapple.getInstance().createConfigurationSection("KitEdit." + holder.toString() + "." + String.valueOf(id));
           // Gapple.getInstance().set("KitEdit." + holder.toString() + ".id",id);
            Gapple.getInstance().set("KitEdit." + holder.toString() + "." + id + ".inv",this.inv);
            Gapple.getInstance().set("KitEdit." + holder.toString() + "." + id + ".armor",this.armor);
            Gapple.getInstance().save();
        }
    }

    public UUID getHolder() {
        return holder;
    }

    public void setHolder(UUID holder) {
        this.holder = holder;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemStack> getInv() {
        return inv;
    }

    public void setInv(List<ItemStack> inv) {
        this.inv = inv;
    }

    public List<ItemStack> getArmor() {
        return armor;
    }

    public void setArmor(List<ItemStack> armor) {
        this.armor = armor;
    }
}

package me.giantcrack.bpvp.kits;
import me.giantcrack.bpvp.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class KitManager {


    private static KitManager instance = new KitManager();

    private KitManager() {}

    public static KitManager getInstance() {
        return instance;
    }

    public List<Kit> kits = new ArrayList<>();

    public List<KitEdit> edits = new ArrayList<>();

    public Kit getKit(String name) {
        for (Kit k : kits) {
            if (k.getName().equalsIgnoreCase(name)) {
                return k;
            }
        }
        return null;
    }


    public void saveKit(Player p, Kit k, int id) {
        if (getKitEdit(p,k,id) != null) {
            edits.remove(getKitEdit(p,k,id));
        }
        final KitEdit ke = new KitEdit(p,k,id);
        new BukkitRunnable(){
            @Override
            public void run() {
                ke.save();
            }
        }.runTaskAsynchronously(Main.getInstance());
        edits.add(ke);
        p.sendMessage(ChatColor.GREEN + "Saved kit " + id);
        return;
    }


    public KitEdit getKitEdit(Player p, Kit k, int id) {
        for (KitEdit ke : edits) {
            if (ke.getHolder().equals(p.getUniqueId()) && ke.getKit().getName().equals(k.getName()) && ke.getId() == id) {
                return ke;
            }
        }
        return null;
    }

    public List<KitEdit> getKitEdits(Player p, Kit k) {
        List<KitEdit> e = new ArrayList<>();
        for (KitEdit ke : edits) {
            if (ke.getHolder().equals(p.getUniqueId()) && ke.getKit().getName().equals(k.getName())) {
                e.add(ke);
            }
        }
        return e;
    }

    public void loadKitsFromFile() {
        for (String uuid : me.giantcrack.bpvp.files.NoDebuff.getInstance().<ConfigurationSection>get("KitEdit").getKeys(false)) {
            for (String id :me.giantcrack.bpvp.files.NoDebuff.getInstance().<ConfigurationSection>get("KitEdit." + uuid).getKeys(false)) {
                Kit k = getKit("NoDebuff");
                List<ItemStack> inv = (List<ItemStack>) me.giantcrack.bpvp.files.NoDebuff.getInstance().get("KitEdit." + uuid + "." + id + ".inv");
                List<ItemStack> armor = (List<ItemStack>) me.giantcrack.bpvp.files.NoDebuff.getInstance().get("KitEdit." + uuid + "." + id + ".armor");
                KitEdit ke = new KitEdit(UUID.fromString(uuid), k, Integer.valueOf(id), inv, armor);
                edits.add(ke);
            }
        }
        for (String uuid : me.giantcrack.bpvp.files.Debuff.getInstance().<ConfigurationSection>get("KitEdit").getKeys(false)) {
            for (String id : me.giantcrack.bpvp.files.Debuff.getInstance().<ConfigurationSection>get("KitEdit." + uuid).getKeys(false)) {
                Kit k = getKit("Debuff");
                List<ItemStack> inv = (List<ItemStack>) me.giantcrack.bpvp.files.Debuff.getInstance().get("KitEdit." + uuid + "." + id + ".inv");
                List<ItemStack> armor = (List<ItemStack>) me.giantcrack.bpvp.files.Debuff.getInstance().get("KitEdit." + uuid + "." + id + ".armor");
                KitEdit ke = new KitEdit(UUID.fromString(uuid), k, Integer.valueOf(id), inv, armor);
                edits.add(ke);
            }
        }
        for (String uuid : me.giantcrack.bpvp.files.Archer.getInstance().<ConfigurationSection>get("KitEdit").getKeys(false)) {
            for (String id : me.giantcrack.bpvp.files.Archer.getInstance().<ConfigurationSection>get("KitEdit." + uuid).getKeys(false)) {
                Kit k = getKit("Archer");
                List<ItemStack> inv = (List<ItemStack>) me.giantcrack.bpvp.files.Archer.getInstance().get("KitEdit." + uuid + "." + id + ".inv");
                List<ItemStack> armor = (List<ItemStack>) me.giantcrack.bpvp.files.Archer.getInstance().get("KitEdit." + uuid + "." + id + ".armor");
                KitEdit ke = new KitEdit(UUID.fromString(uuid), k, Integer.valueOf(id), inv, armor);
                edits.add(ke);
            }
        }
        for (String uuid : me.giantcrack.bpvp.files.Soup.getInstance().<ConfigurationSection>get("KitEdit").getKeys(false)) {
            for (String id : me.giantcrack.bpvp.files.Soup.getInstance().<ConfigurationSection>get("KitEdit." + uuid).getKeys(false)) {
                Kit k = getKit("Soup");
                List<ItemStack> inv = (List<ItemStack>) me.giantcrack.bpvp.files.Soup.getInstance().get("KitEdit." + uuid + "." + id + ".inv");
                List<ItemStack> armor = (List<ItemStack>) me.giantcrack.bpvp.files.Soup.getInstance().get("KitEdit." + uuid + "." + id + ".armor");
                KitEdit ke = new KitEdit(UUID.fromString(uuid), k, Integer.valueOf(id), inv, armor);
                edits.add(ke);
            }
        }
        for (String uuid : me.giantcrack.bpvp.files.MCSG.getInstance().<ConfigurationSection>get("KitEdit").getKeys(false)) {
            for (String id : me.giantcrack.bpvp.files.MCSG.getInstance().<ConfigurationSection>get("KitEdit." + uuid).getKeys(false)) {
                Kit k = getKit("MCSG");
                List<ItemStack> inv = (List<ItemStack>) me.giantcrack.bpvp.files.MCSG.getInstance().get("KitEdit." + uuid + "." + id + ".inv");
                List<ItemStack> armor = (List<ItemStack>) me.giantcrack.bpvp.files.MCSG.getInstance().get("KitEdit." + uuid + "." + id + ".armor");
                KitEdit ke = new KitEdit(UUID.fromString(uuid), k, Integer.valueOf(id), inv, armor);
                edits.add(ke);
            }
        }
        for (String uuid : me.giantcrack.bpvp.files.Gapple.getInstance().<ConfigurationSection>get("KitEdit").getKeys(false)) {
            for (String id : me.giantcrack.bpvp.files.Gapple.getInstance().<ConfigurationSection>get("KitEdit." + uuid).getKeys(false)) {
                Kit k = getKit("Gapple");
                List<ItemStack> inv = (List<ItemStack>) me.giantcrack.bpvp.files.Gapple.getInstance().get("KitEdit." + uuid + "." + id + ".inv");
                List<ItemStack> armor = (List<ItemStack>) me.giantcrack.bpvp.files.Gapple.getInstance().get("KitEdit." + uuid + "." + id + ".armor");
                KitEdit ke = new KitEdit(UUID.fromString(uuid), k, Integer.valueOf(id), inv, armor);
                edits.add(ke);
            }
        }
    }

    public void loadKits() {
        //Create kit objects and load them to array
        if (me.giantcrack.bpvp.files.NoDebuff.getInstance().<ConfigurationSection>get("KitEdit") == null) {
            me.giantcrack.bpvp.files.NoDebuff.getInstance().createConfigurationSection("KitEdit");
        }
        if (me.giantcrack.bpvp.files.Debuff.getInstance().<ConfigurationSection>get("KitEdit") == null) {
            me.giantcrack.bpvp.files.Debuff.getInstance().createConfigurationSection("KitEdit");
        }
        if (me.giantcrack.bpvp.files.Archer.getInstance().<ConfigurationSection>get("KitEdit") == null) {
            me.giantcrack.bpvp.files.Archer.getInstance().createConfigurationSection("KitEdit");
        }
        if (me.giantcrack.bpvp.files.Soup.getInstance().<ConfigurationSection>get("KitEdit") == null) {
            me.giantcrack.bpvp.files.Soup.getInstance().createConfigurationSection("KitEdit");
        }
        if (me.giantcrack.bpvp.files.MCSG.getInstance().<ConfigurationSection>get("KitEdit") == null) {
            me.giantcrack.bpvp.files.MCSG.getInstance().createConfigurationSection("KitEdit");
        }
        if (me.giantcrack.bpvp.files.Gapple.getInstance().<ConfigurationSection>get("KitEdit") == null) {
            me.giantcrack.bpvp.files.Gapple.getInstance().createConfigurationSection("KitEdit");
        }
        NoDebuff nd = new NoDebuff();
        kits.add(nd);
        Debuff d = new Debuff();
        kits.add(d);
        Archer a = new Archer();
        kits.add(a);
        MCSG m = new MCSG();
        kits.add(m);
        Gapple g = new Gapple();
        kits.add(g);
        Soup s = new Soup();
        kits.add(s);
    }


}

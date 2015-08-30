package me.giantcrack.ii;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by shoot_000 on 6/19/2015.
 */
public class InsuranceManager  {


    private static InsuranceManager instance = new InsuranceManager();

    public static InsuranceManager getInstance() {
        return instance;
    }

    private InsuranceManager() { }

    public List<InsurancePolicy> policies = new ArrayList<>();

    public InsurancePolicy getPolicy(Player p) {
        for (InsurancePolicy ip : policies) {
            if (ip.getP().equals(p.getUniqueId())) {
                return ip;
            }
        }
        return null;
    }

    public void createPolicy(Player p) {
        if (getPolicy(p) == null) {
            InsurancePolicy ip = new InsurancePolicy(p.getUniqueId());
            policies.add(ip);
            return;
        }
    }

    public void deletePolicy(Player p) {
        if (getPolicy(p) != null) {
            getPolicy(p).delete();
        }
    }


    public void setUP() {
        for (String uuid : Main.getInstance().getConfig().getConfigurationSection("InsurancePolicy").getKeys(false)) {
            List<ItemStack> i = new ArrayList<>();
            int count = 0;
            do {
                i.add((ItemStack)Main.getInstance().getConfig().get("InsurancePolicy." + uuid + "." + count));
                count++;
            }while(Main.getInstance().getConfig().get("InsurancePolicy." + uuid + "." + count) != null);
            InsurancePolicy ip = new InsurancePolicy(UUID.fromString(uuid));
            ip.getItems().addAll(i);
            ip.save();
        }
    }
}

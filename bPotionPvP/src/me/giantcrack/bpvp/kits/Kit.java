package me.giantcrack.bpvp.kits;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public interface Kit {

    String getName();

    void giveDefaultKit(Player p);

    ItemStack getIcon();

    Location getEditLocation();

}

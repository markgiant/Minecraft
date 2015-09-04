package me.giantcrack.gge.kits;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public interface KitInfo {

    String getName();

    void apply(Player p);

    boolean hasKit(Player p);

    List<String> getPlayersWithKit();
}

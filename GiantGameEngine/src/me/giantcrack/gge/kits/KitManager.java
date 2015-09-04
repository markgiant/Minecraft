package me.giantcrack.gge.kits;

import me.giantcrack.gge.exceptions.KitException;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public interface KitManager {

    List<? extends Kit> getKits();

    void createKit(String name) throws KitException;

    Kit getKit(String name);

    void deleteKit(String name) throws KitException;

    void applyKit(Player p, String kit) throws KitException;

}

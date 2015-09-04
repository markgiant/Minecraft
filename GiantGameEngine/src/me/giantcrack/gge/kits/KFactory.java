package me.giantcrack.gge.kits;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public abstract class KFactory implements KitManager {

    protected Plugin plugin;

    public KFactory(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<? extends Kit> getKits() {
        return null;
    }

    @Override
    public void createKit(String name) {

    }

    @Override
    public Kit getKit(String name) {
        return null;
    }

    @Override
    public void deleteKit(String name) {

    }

    @Override
    public void applyKit(Player p, String kit) {

    }
}

package me.giantcrack.gge.arena;

import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Created by markvolkov on 8/6/15.
 */
public abstract class AFactory implements ArenaManager {

    protected Plugin plugin;

    public AFactory(Plugin plugin) {
        this.plugin = plugin;
    }

}

package me.giantcrack.bs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by shoot_000 on 6/22/2015.
 */
public class Main extends JavaPlugin {


    private static Main i;
    @Override
    public void onEnable() {
        i = this;
        Events.staff.clear();
        Bukkit.getPluginManager().registerEvents(new Events(),this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,new StaffTask(),20,20);
    }

    @Override
    public void onDisable() {
        Events.staff.clear();
        i = null;
    }

    public static Main getInstance() {
        return i;
    }

    public boolean isStaff(Player p) {
        return p.hasPermission("b.staff");
    }
}

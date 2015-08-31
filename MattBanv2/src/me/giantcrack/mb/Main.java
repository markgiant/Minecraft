package me.giantcrack.mb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    public static final String prefix = ChatColor.RED + "[" + ChatColor.DARK_RED + "Bans" + ChatColor.RED + "] ";
    private static Main i;

    public static boolean isExempt(Player p) {
        return PlayerManager.getInstance().getData(p).exempt();
    }

    public static boolean isExempt(OfflinePlayer p) {
        return PlayerManager.getInstance().getData(p).exempt();
    }

    public void onEnable() {
        i = this;
        Players.getInstance().setup(this);
        Bans.getInstance().setup(this);
        registerListeners();
        registerCmds();
        PlayerManager.getInstance().loadPlayersFromFile();
        BanManager.getInstance().loadBansFromFile();
        TempbanManager.getInstance().loadBansFromFile();
        getCommand("ban").setExecutor(new BanCmd());
        getCommand("tempban").setExecutor(new TempbanCmd());
        getCommand("unban").setExecutor(new UnbanCmd());
        getCommand("pardon").setExecutor(new UnbanCmd());
        getCommand("lookup").setExecutor(new LookupCmd());
        getCommand("proxyreset").setExecutor(new ResetCmd());
    }

    public void onDisable() {
        i = null;
    }

    public String formatTime(int seconds) {
        int hours = seconds % 2678400 % 604800 % 86400 / 3600;
        int days = seconds % 2678400 % 604800 / 86400;
        int weeks = seconds % 2678400 / 604800;
        int months = seconds / 2678400;
        int mins = seconds % 2678400 % 604800 % 86400 % 3600 / 60;
        int secs = seconds % 2678400 % 604800 % 86400 % 3600 % 60;
        if (months > 0) {
            return months + " Months " + weeks + " Weeks " + days + " Days\n " + hours + " Hours " + mins + " Minutes " + secs + " Seconds.";
        }
        if (weeks > 0) {
            return weeks + " Weeks " + days + " Days " + hours + " Hours\n " + mins + " Minutes " + secs + " Seconds.";
        }
        if (days > 0) {
            return days + " Days " + hours + " Hours " + mins + " Minutes " + secs + " Seconds.";
        }
        if (hours > 0) {
            return hours + " Hours " + mins + " Minutes " + secs + " Seconds.";
        }
        if (mins > 0) {
            return mins + " Minutes " + secs + " Seconds.";
        }
        return secs + " Seconds.";
    }

    public static Main getInstance() {
        return i;
    }

    public void registerCmds() {
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }
}




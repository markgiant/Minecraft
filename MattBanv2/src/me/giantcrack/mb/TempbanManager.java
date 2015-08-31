package me.giantcrack.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class TempbanManager {
    private static TempbanManager instance = new TempbanManager();
    private final String perm = "mattban.staff";

    public static TempbanManager getInstance() {
        return instance;
    }

    public List<TempbanData> bans = new ArrayList();

    public TempbanData getBan(Player p) {
        for (TempbanData data : this.bans) {
            if (data.getUserData().getUuid().equals(p.getUniqueId())) {
                return data;
            }
        }
        return null;
    }

    public TempbanData getBan(OfflinePlayer p) {
        for (TempbanData data : this.bans) {
            if (data.getUserData().getUuid().equals(p.getUniqueId())) {
                return data;
            }
        }
        return null;
    }

    public TempbanData getBan(UUID uuid) {
        for (TempbanData data : this.bans) {
            if (data.getUserData().getUuid().equals(uuid)) {
                return data;
            }
        }
        return null;
    }

    public TempbanData getBan(String ip) {
        for (TempbanData data : this.bans) {
            if (data.getBannedIp().equals(ip)) {
                return data;
            }
        }
        return null;
    }

    public boolean isSuspicious(String ip) {
        for (TempbanData data : this.bans) {
            if (data.getUserData().getIps().contains(ip)) {
                return true;
            }
        }
        return false;
    }

    public void banPlayer(Player banner, Player banned, int seconds) {
        if (Main.isExempt(banned)) {
            return;
        }
        if (getBan(banned) != null) {
            getBan(banned).delete();
            this.bans.remove(getBan(banned));
            TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), banner.getName(), banned.getName(), seconds);
            this.bans.add(data);
            getBan(banned).save();
            Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + banner.getName() + "!", "mattban.staff");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");
            banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been temporarily banned!");
            return;
        }
        TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), banner.getName(), banned.getName(), seconds);
        this.bans.add(data);
        getBan(banned).save();
        Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + banner.getName() + "!", "mattban.staff");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");
        banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been temporarily banned!");
    }

    public void banPlayer(Player banned, int seconds) {
        if (Main.isExempt(banned)) {
            return;
        }
        if (getBan(banned) != null) {
            getBan(banned).delete();
            this.bans.remove(getBan(banned));
            TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), "Console", banned.getName(), seconds);
            this.bans.add(data);
            getBan(banned).save();
            Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + "Console" + "!", "mattban.staff");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");
            banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been temporarily banned!");
            return;
        }
        TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), "Console", banned.getName(), seconds);
        this.bans.add(data);
        getBan(banned).save();
        Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + "Console" + "!", "mattban.staff");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");
        banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been temporarily banned!");
    }

    public void banPlayer(Player banner, OfflinePlayer banned, int seconds) {
        if (getBan(banned) != null) {
            getBan(banned).delete();
            this.bans.remove(getBan(banned));
            TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), banner.getName(), banned.getName(), seconds);
            this.bans.add(data);
            getBan(banned).save();
            Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + banner.getName() + "!", "mattban.staff");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");

            return;
        }
        TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), banner.getName(), banned.getName(), seconds);
        this.bans.add(data);
        getBan(banned).save();
        Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + banner.getName() + "!", "mattban.staff");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");
    }

    public void banPlayer(OfflinePlayer banned, int seconds) {
        if (getBan(banned) != null) {
            getBan(banned).delete();
            this.bans.remove(getBan(banned));
            TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), "Console", banned.getName(), seconds);
            this.bans.add(data);
            getBan(banned).save();
            Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + "Console" + "!", "mattban.staff");
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");

            return;
        }
        TempbanData data = new TempbanData(PlayerManager.getInstance().getData(banned), "Console", banned.getName(), seconds);
        this.bans.add(data);
        getBan(banned).save();
        Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned!");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been temporarily banned by " + "Console" + "!", "mattban.staff");
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been temporarily banned!", "mattban.staff");
    }

    public void unbanPlayer(Player p, Player banned) {
        if (getBan(banned) == null) {
            p.sendMessage(Main.prefix + ChatColor.RED + "That player doesn't have a current ban!");
            return;
        }
        getBan(banned).delete();
        this.bans.remove(getBan(banned));
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been unbanned by " + p.getName() + "!", "mattban.staff");
    }

    public void unbanPlayer(Player p, OfflinePlayer banned) {
        if (getBan(banned) == null) {
            p.sendMessage(Main.prefix + ChatColor.RED + "That player doesn't have a current ban!");
            return;
        }
        getBan(banned).delete();
        this.bans.remove(getBan(banned));
        Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been unbanned by " + p.getName() + "!", "mattban.staff");
    }

    public void unbanPlayer(OfflinePlayer banned) {
        if (getBan(banned) == null) {
            return;
        }
        getBan(banned).delete();
        this.bans.remove(getBan(banned));
    }

    public void unbanPlayer(Player banned) {
        if (getBan(banned) == null) {
            return;
        }
        getBan(banned).delete();
        this.bans.remove(getBan(banned));
    }

    public void loadBansFromFile() {
        if (Bans.getInstance().get("TempBans") == null) {
            Bans.getInstance().createConfigurationSection("TempBans");
        }
        for (String uuid : ((ConfigurationSection) Bans.getInstance().get("TempBans")).getKeys(false)) {
            String banner = (String) Bans.getInstance().get("TempBans." + uuid + ".banner");
            String ipbanned = (String) Bans.getInstance().get("TempBans." + uuid + ".ipbanned");
            String nwb = (String) Bans.getInstance().get("TempBans." + uuid + ".nwb");
            int end = Bans.getInstance().getInt("TempBans." + uuid + ".end");
            TempbanData data = new TempbanData(PlayerManager.getInstance().getData(UUID.fromString(uuid)), banner, nwb, end, false);
            data.setIpBanned(ipbanned);
            this.bans.add(data);
        }
    }
}

 package me.giantcrack.mb;
 
 import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
 
 public class BanManager
 {
   private static BanManager instance = new BanManager();
   private final String perm = "mattban.staff";
   
   public static BanManager getInstance()
   {
     return instance;
   }
   
   public List<BanData> bans = new ArrayList();
   
   public BanData getBan(Player p)
   {
     for (BanData data : this.bans) {
       if (data.getUserData().getUuid().equals(p.getUniqueId())) {
         return data;
       }
     }
     return null;
   }
   
   public BanData getBan(OfflinePlayer p)
   {
     for (BanData data : this.bans) {
       if (data.getUserData().getUuid().equals(p.getUniqueId())) {
         return data;
       }
     }
     return null;
   }
   
   public BanData getBan(String ip)
   {
     for (BanData data : this.bans) {
       if (data.getBannedIp().equals(ip)) {
         return data;
       }
     }
     return null;
   }
   
   public boolean isSuspicious(String ip)
   {
     for (BanData data : this.bans) {
       if (data.getUserData().getIps().contains(ip)) {
         return true;
       }
     }
     return false;
   }
   
   public BanData getBan(UUID uuid)
   {
     for (BanData data : this.bans) {
       if (data.getUserData().getUuid().equals(uuid)) {
         return data;
       }
     }
     return null;
   }
   
   public void banPlayer(Player banner, Player banned, String reason)
   {
     if (Main.isExempt(banned)) {
       return;
     }
     if (getBan(banned) != null)
     {
       getBan(banned).delete();
       this.bans.remove(getBan(banned));
       BanData data = new BanData(PlayerManager.getInstance().getData(banned), banner.getName(), reason, banned.getName());
       this.bans.add(data);
       getBan(banned).save();
       Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + banner.getName() + " for " + reason + "!", "mattban.staff");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
       banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been banned for " + reason + "!");
       return;
     }
     BanData data = new BanData(PlayerManager.getInstance().getData(banned), banner.getName(), reason, banned.getName());
     this.bans.add(data);
     getBan(banned).save();
     Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + banner.getName() + " for " + reason + "!", "mattban.staff");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
     banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been banned for " + reason + "!");
   }
   
   public void banPlayer(Player banned, String reason)
   {
     if (Main.isExempt(banned)) {
       return;
     }
     if (getBan(banned) != null)
     {
       getBan(banned).delete();
       this.bans.remove(getBan(banned));
       BanData data = new BanData(PlayerManager.getInstance().getData(banned), "Console", reason, banned.getName());
       this.bans.add(data);
       getBan(banned).save();
       Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + "Console" + " for " + reason + "!", "mattban.staff");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
       banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been banned for " + reason + "!");
       return;
     }
     BanData data = new BanData(PlayerManager.getInstance().getData(banned), "Console", reason, banned.getName());
     this.bans.add(data);
     getBan(banned).save();
     Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + "Console" + " for " + reason + "!", "mattban.staff");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
     banned.kickPlayer(Main.prefix + ChatColor.WHITE + "You have been banned for " + reason + "!");
   }
   
   public void banPlayer(Player banner, OfflinePlayer banned, String reason)
   {
     if (getBan(banned) != null)
     {
       getBan(banned).delete();
       this.bans.remove(getBan(banned));
       BanData data = new BanData(PlayerManager.getInstance().getData(banned), banner.getName(), reason, banned.getName());
       this.bans.add(data);
       getBan(banned).save();
       Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + banner.getName() + " for " + reason + "!", "mattban.staff");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
       
       return;
     }
     BanData data = new BanData(PlayerManager.getInstance().getData(banned), banner.getName(), reason, banned.getName());
     this.bans.add(data);
     getBan(banned).save();
     Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + banner.getName() + " for " + reason + "!", "mattban.staff");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
   }
   
   public void banPlayer(OfflinePlayer banned, String reason)
   {
     if (getBan(banned) != null)
     {
       getBan(banned).delete();
       this.bans.remove(getBan(banned));
       BanData data = new BanData(PlayerManager.getInstance().getData(banned), "Console", reason, banned.getName());
       this.bans.add(data);
       getBan(banned).save();
       Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + "Console" + " for " + reason + "!", "mattban.staff");
       Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
       
       return;
     }
     BanData data = new BanData(PlayerManager.getInstance().getData(banned), "Console", reason, banned.getName());
     this.bans.add(data);
     getBan(banned).save();
     Bukkit.broadcastMessage(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned!");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been banned by " + "Console" + " for " + reason + "!", "mattban.staff");
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + "The ip " + getBan(banned).getUserData().getCurrentIP() + " has been banned!", "mattban.staff");
   }
   
   public void unbanPlayer(Player p, Player banned)
   {
     if (getBan(banned) == null) {
       return;
     }
     getBan(banned).delete();
     this.bans.remove(getBan(banned));
     Bukkit.broadcast(Main.prefix + ChatColor.WHITE + banned.getName() + " has been unbanned by " + p.getName() + "!", "mattban.staff");
   }
   
   public void unbanPlayer(OfflinePlayer banned)
   {
     if (getBan(banned) == null) {
       return;
     }
     getBan(banned).delete();
     this.bans.remove(getBan(banned));
   }
   
   public void loadBansFromFile()
   {
     if (Bans.getInstance().get("Bans") == null) {
       Bans.getInstance().createConfigurationSection("Bans");
     }
     for (String uuid : ((ConfigurationSection)Bans.getInstance().get("Bans")).getKeys(false))
     {
       String banner = (String)Bans.getInstance().get("Bans." + uuid + ".banner");
       String reason = (String)Bans.getInstance().get("Bans." + uuid + ".reason");
       String nwb = (String)Bans.getInstance().get("Bans." + uuid + ".nwb");
       String ip = (String)Bans.getInstance().get("Bans." + uuid + ".ipbanned");
       BanData data = new BanData(PlayerManager.getInstance().getData(UUID.fromString(uuid)), banner, reason, nwb);
       data.setIpBanned(ip);
       this.bans.add(data);
     }
   }


    public void unBanAll() {
        for (BanData bd : bans) {
            if (bd.getUserData().isBlacklisted()) continue;
            bd.delete();
            bd.save();
            bans.remove(bd);
        }

        for (TempbanData td : TempbanManager.getInstance().bans)  {
            if (td.getUserData().isBlacklisted()) continue;
            td.delete();
            td.save();
            bans.remove(td);

        }
    }
 }


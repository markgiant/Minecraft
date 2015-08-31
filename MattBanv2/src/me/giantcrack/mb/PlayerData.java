 package me.giantcrack.mb;
 
 import java.util.HashSet;
 import java.util.Set;
 import java.util.UUID;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.OfflinePlayer;
 import org.bukkit.entity.Player;
 
 public class PlayerData
 {
   private UUID uuid;
   private String playerName;
   private Set<String> ips;
   private String currentIP;
   private boolean blacklisted;
   private boolean exempt;
   private Set<String> possibleAlts;
   
   public PlayerData(OfflinePlayer p, String currentIP, boolean exempt)
   {
     this.uuid = p.getUniqueId();
     this.playerName = p.getName();
     this.ips = new HashSet();
     this.currentIP = currentIP;
     this.exempt = exempt;
     this.blacklisted = false;
     this.possibleAlts = new HashSet();
   }
   
   public PlayerData(Player p, String currentIP, boolean exempt)
   {
     this.uuid = p.getUniqueId();
     this.playerName = p.getName();
     this.ips = new HashSet();
     this.currentIP = currentIP;
     this.exempt = exempt;
     this.blacklisted = false;
     this.possibleAlts = new HashSet();
   }
   
   public String getCurrentIP()
   {
     return this.currentIP;
   }
   
   public void save()
   {
     Players.getInstance().createConfigurationSection("Players." + this.uuid);
     Players.getInstance().set("Players." + this.uuid.toString() + ".name", this.playerName);
     Players.getInstance().set("Players." + this.uuid.toString() + ".currentip", this.currentIP);
     Players.getInstance().set("Players." + this.uuid.toString() + ".ips", this.ips);
     Players.getInstance().set("Players." + this.uuid.toString() + ".blacklisted",this.blacklisted);
     Players.getInstance().set("Players." + this.uuid.toString() + ".exempt", Boolean.valueOf(this.exempt));
     Players.getInstance().set("Players." + this.uuid.toString() + ".possible", this.possibleAlts);
     Players.getInstance().save();
   }
   
   public boolean isBanned()
   {
     return (TempbanManager.getInstance().getBan(this.uuid) != null) || (BanManager.getInstance().getBan(this.uuid) != null);
   }

   public boolean isBlacklisted() {
     return this.blacklisted;
   }

   public void blackList() {
     this.blacklisted = !this.blacklisted;
     save();
   }

   private int currentTime = (int)System.currentTimeMillis() / 1000;
   
   public String toString()
   {
     if (!isBanned()) {
       return "&c&m-&4&m---------------------------------------------------&c&m-".replace("&", "§") + ChatColor.GRAY + " " + this.playerName + "\n" + ChatColor.GRAY + " IPs" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + this.ips.toString().replace("[", "").replace("]", "") + "\n" + ChatColor.GRAY + " Possible Alts" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + PlayerManager.getInstance().getAllPlayersOnIp(this).toString().replace("[", "").replace("]", "") + "\n" + ChatColor.GRAY + " Current IP" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + this.currentIP + "\n" + "&c&m-&4&m---------------------------------------------------&c&m-".replace("&", "§");
     } else if (TempbanManager.getInstance().getBan(this.uuid) != null) {
       return "&c&m-&4&m---------------------------------------------------&c&m-".replace("&", "§") + ChatColor.GRAY + " " + this.playerName + "\n" + ChatColor.GRAY + " IPs" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + this.ips.toString().replace("[", "").replace("]", "") + "\n" + ChatColor.GRAY + " Possible Alts" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + PlayerManager.getInstance().getAllPlayersOnIp(this).toString().replace("[", "").replace("]", "") + "\n" + ChatColor.GRAY + " Current IP" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + this.currentIP + "\n" + ChatColor.GRAY + " Banner" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + TempbanManager.getInstance().getBan(this.uuid).getBanner() + "\n" + ChatColor.GRAY + " TimeLeft" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + Main.getInstance().formatTime(TempbanManager.getInstance().getBan(this.uuid).getSecondsLeft()) + "\n" + "&c&m-&4&m---------------------------------------------------&c&m-".replace("&", "§");
     }else {
       return "&c&m-&4&m---------------------------------------------------&c&m-".replace("&", "§") + ChatColor.GRAY + " " + this.playerName + "\n" + ChatColor.GRAY + " IPs" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + this.ips.toString().replace("[", "").replace("]", "") + "\n" + ChatColor.GRAY + " Possible Alts" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + PlayerManager.getInstance().getAllPlayersOnIp(this).toString().replace("[", "").replace("]", "") + "\n" + ChatColor.GRAY + " Current IP" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + this.currentIP + "\n" + ChatColor.GRAY + " Banner" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + TempbanManager.getInstance().getBan(this.uuid).getBanner() + "\n" + ChatColor.GRAY + " Reason" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + BanManager.getInstance().getBan(this.uuid).getReason() + "\n" + "&c&m-&4&m---------------------------------------------------&c&m-".replace("&", "§");
     }
   }
   
   public boolean exempt()
   {
     return this.exempt;
   }
   
   public void setPossibleAlts(Set<String> alts)
   {
     this.possibleAlts = alts;
   }
   
   public void setCurrentIP(String ip)
   {
     this.currentIP = ip;
   }
   
   public OfflinePlayer getPlayer()
   {
     if (Bukkit.getPlayer(this.uuid) != null) {
       return Bukkit.getPlayer(this.uuid);
     }
     return Bukkit.getOfflinePlayer(this.uuid);
   }
   
   public UUID getUuid()
   {
     return this.uuid;
   }
   
   public void setUuid(UUID uuid)
   {
     this.uuid = uuid;
   }
   
   public String getPlayerName()
   {
     return this.playerName;
   }
   
   public void setPlayerName(String playerName)
   {
     this.playerName = playerName;
   }
   
   public Set<String> getIps()
   {
     return this.ips;
   }
   
   public void setIps(Set<String> ips)
   {
     this.ips = ips;
   }
 }

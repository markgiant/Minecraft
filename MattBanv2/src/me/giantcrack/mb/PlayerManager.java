package me.giantcrack.mb;
import java.util.*;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
 
 public class PlayerManager
 {
   private static PlayerManager instance = new PlayerManager();
   
   public static PlayerManager getInstance()
   {
     return instance;
   }
   
   public List<PlayerData> players = new ArrayList();
   
   public PlayerData getData(Player p)
   {
     for (PlayerData data : this.players) {
       if (data.getUuid().equals(p.getUniqueId())) {
         return data;
       }
     }
     return null;
   }


   public void resetAltsAndIps() {
     try {
       Iterator<PlayerData> data = players.iterator();
       while(data.hasNext()) {
         PlayerData pd = data.next();
         pd.setIps(new HashSet<String>());
         pd.setPossibleAlts(new HashSet<String>());
         pd.save();
       }
     }catch (NullPointerException ex) {

     }
   }

   public PlayerData getData(OfflinePlayer p)
   {
     for (PlayerData data : this.players) {
       if (data.getUuid().equals(p.getUniqueId())) {
         return data;
       }
     }
     return null;
   }
   
   public PlayerData getData(UUID uuid)
   {
     for (PlayerData data : this.players) {
       if (data.getUuid().equals(uuid)) {
         return data;
       }
     }
     return null;
   }


   public PlayerData getDataFromName(String name) {
     for (PlayerData data : this.players) {
       if (data.getPlayerName().equalsIgnoreCase(name)) {
         return data;
       }
     }
     return null;
   }


   public PlayerData getData(String ip)
   {
     for (PlayerData data : this.players) {
       if (data.getIps().contains(ip)) {
         return data;
       }
     }
     return null;
   }
   
   public void handle(Player p, String address)
   {
     if (getData(p) == null)
     {
       PlayerData pd = new PlayerData(p, address, false);
       this.players.add(pd);
       getData(p).getIps().add(address);
       getData(p).setCurrentIP(address);
       pd.save();
       return;
     }
     getData(p).getIps().add(address);
     getData(p).setCurrentIP(address);
     getData(p).save();
   }
   
   public List<String> getAllPlayersOnIp(PlayerData data)
   {
     List<String> names = new ArrayList();
     for (PlayerData pd : players) {
       for (String ips : data.getIps()) {
         if (!pd.getUuid().equals(data.getUuid())) {
           if (pd.getIps().contains(ips)) {
             names.add(pd.getPlayerName());
           }
         }
       }
     }
     return names;
   }
   
   public void loadPlayersFromFile()
   {
     if (Players.getInstance().get("Players") == null) {
       Players.getInstance().createConfigurationSection("Players");
     }
     for (String uuid : ((ConfigurationSection)Players.getInstance().get("Players")).getKeys(false)) {
         String name = (String) Players.getInstance().get("Players." + uuid.toString() + ".name");
         String currentIP = (String) Players.getInstance().get("Players." + uuid.toString() + ".currentip");
         Set<String> ips = (HashSet) Players.getInstance().get("Players." + uuid.toString() + ".ips");
         Set<String> pa = (HashSet) Players.getInstance().get("Players." + uuid.toString() + ".possible");
         boolean exempt = ((Boolean) Players.getInstance().get("Players." + uuid.toString() + ".exempt")).booleanValue();
         PlayerData data = new PlayerData(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), currentIP, exempt);
         data.setPossibleAlts(pa);
         data.setIps(ips);
         this.players.add(data);
     }
   }
 }


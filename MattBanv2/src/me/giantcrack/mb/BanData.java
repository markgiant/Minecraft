 package me.giantcrack.mb;
 
 import java.util.UUID;
 
 public class BanData
 {
   private PlayerData userData;
   private String banner;
   private String reason;
   private String nameWhenBanned;
   private String ipBanned;
   
   public BanData(PlayerData userData, String banner, String reason, String nameWhenBanned)
   {
     this.userData = userData;
     this.banner = banner;
     this.reason = reason;
     this.ipBanned = this.userData.getCurrentIP();
     this.nameWhenBanned = nameWhenBanned;
   }
   
   public void save()
   {
     Bans.getInstance().createConfigurationSection("Bans." + this.userData.getUuid());
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".banner", this.banner);
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".reason", this.reason);
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".nwb", this.nameWhenBanned);
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".ipbanned", this.ipBanned);
     Bans.getInstance().save();
   }
   
   public String getBannedIp()
   {
     return this.ipBanned;
   }
   
   public void setIpBanned(String ip)
   {
     this.ipBanned = ip;
   }
   
   public void delete()
   {
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".banner", null);
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".reason", null);
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".nwb", null);
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString() + ".ipbanned", null);
     Bans.getInstance().set("Bans." + this.userData.getUuid().toString(), null);
     Bans.getInstance().save();
   }
   
   public PlayerData getUserData()
   {
     return this.userData;
   }
   
   public void setUserData(PlayerData userData)
   {
     this.userData = userData;
   }
   
   public String getBanner()
   {
     return this.banner;
   }
   
   public void setBanner(String banner)
   {
     this.banner = banner;
   }
   
   public String getReason()
   {
     return this.reason;
   }
   
   public void setReason(String reason)
   {
     this.reason = reason;
   }
   
   public String getNameWhenBanned()
   {
     return this.nameWhenBanned;
   }
   
   public void setNameWhenBanned(String nameWhenBanned)
   {
     this.nameWhenBanned = nameWhenBanned;
   }
 }


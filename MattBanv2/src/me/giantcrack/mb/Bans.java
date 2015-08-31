 package me.giantcrack.mb;
 
 import java.io.File;
 import java.io.IOException;
 import org.bukkit.configuration.ConfigurationSection;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.bukkit.plugin.Plugin;
 
 public class Bans
 {
   private static Bans instance = new Bans();
   private File bans;
   private FileConfiguration cfg;
   
   public static Bans getInstance()
   {
     return instance;
   }
   
   public void setup(Plugin p)
   {
     if (!p.getDataFolder().exists()) {
       p.getDataFolder().mkdir();
     }
     this.bans = new File(p.getDataFolder(), "bans.yml");
     if (!this.bans.exists()) {
       try
       {
         this.bans.createNewFile();
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
     }
     this.cfg = YamlConfiguration.loadConfiguration(this.bans);
   }
   
   public void set(String path, Object value)
   {
     this.cfg.set(path, value);
     save();
   }
   
   public <T> T get(String path)
   {
     return (T) cfg.get(path);
   }
   
   public double getDouble(String path)
   {
     return this.cfg.getDouble(path);
   }
   
   public boolean contains(String path)
   {
     return this.cfg.contains(path);
   }
   
   public ConfigurationSection createConfigurationSection(String path)
   {
     ConfigurationSection cs = this.cfg.createSection(path);
     save();
     return cs;
   }
   
   public FileConfiguration getConfig()
   {
     return this.cfg;
   }
   
   public int getInt(String path)
   {
     return this.cfg.getInt(path);
   }
   
   public void save()
   {
     try
     {
       this.cfg.save(this.bans);
     }
     catch (IOException ex)
     {
       ex.printStackTrace();
     }
   }
 }


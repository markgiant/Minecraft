 package me.giantcrack.mb;
 
 import java.io.File;
 import java.io.IOException;
 import org.bukkit.configuration.ConfigurationSection;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.bukkit.plugin.Plugin;
 
 public class Players
 {
   private static Players instance = new Players();
   private File players;
   private FileConfiguration cfg;
   
   public static Players getInstance()
   {
     return instance;
   }
   
   public void setup(Plugin p)
   {
     if (!p.getDataFolder().exists()) {
       p.getDataFolder().mkdir();
     }
     this.players = new File(p.getDataFolder(), "players.yml");
     if (!this.players.exists()) {
       try
       {
         this.players.createNewFile();
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
     }
     this.cfg = YamlConfiguration.loadConfiguration(this.players);
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
       this.cfg.save(this.players);
     }
     catch (IOException ex)
     {
       ex.printStackTrace();
     }
   }
 }


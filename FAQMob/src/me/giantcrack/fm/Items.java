package me.giantcrack.fm;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by shoot_000 on 6/14/2015.
 */
public class Items {

    private static Items instance = new Items();

    private Items() {}

    public static Items getInstance() {
        return instance;
    }

    private File items;
    private FileConfiguration cfg;

    public void setup(Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        items = new File(p.getDataFolder(), "items.yml");
        if (!items.exists()) {
            try {
                items.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cfg = YamlConfiguration.loadConfiguration(items);
    }

    public void set(String path, Object value) {
        cfg.set(path, value);
        save();
    }

    public String getString(String path) {
        return cfg.getString(path);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        return (T) cfg.get(path);
    }

    public double getDouble(String path) {
        return cfg.getDouble(path);
    }

    public boolean contains(String path) {
        return cfg.contains(path);
    }

    public ConfigurationSection createConfigurationSection(String path) {
        ConfigurationSection cs = cfg.createSection(path);
        save();
        return cs;
    }

    public FileConfiguration getConfig() {
        return cfg;
    }

    public int getInt(String path) {return cfg.getInt(path);}

    public List<String> getList(String path) {
        return cfg.getStringList(path);
    };

    public void save() {
        try {
            cfg.save(items);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}

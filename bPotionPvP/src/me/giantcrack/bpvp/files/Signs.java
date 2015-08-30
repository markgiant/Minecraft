package me.giantcrack.bpvp.files;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by markvolkov on 8/27/15.
 */
public class Signs {

    private static Signs instance = new Signs();

    public static Signs getInstance() {
        return instance;
    }

    private File arenas;

    private FileConfiguration cfg;

    private Signs() {
    }

    public void setup(Plugin p) {
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        arenas = new File(p.getDataFolder(), "signs.yml");
        if (!arenas.exists()) {
            try {
                arenas.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cfg = YamlConfiguration.loadConfiguration(arenas);
    }

    public void set(String path, Object value) {
        cfg.set(path, value);
        save();
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

    public String getString(String path) {
        return cfg.getString(path);
    }

    public FileConfiguration getConfig() {
        return cfg;
    }

    public int getInt(String path) {return cfg.getInt(path);}

    public void save() {
        try {
            cfg.save(arenas);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

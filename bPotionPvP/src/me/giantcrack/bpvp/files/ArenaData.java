package me.giantcrack.bpvp.files;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ArenaData {
	private static ArenaData instance = new ArenaData();

	public static ArenaData getInstance() {
		return instance;
	}

	private File arenas;

	private FileConfiguration cfg;

	private ArenaData() {
	}

	public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		arenas = new File(p.getDataFolder(), "arenadata.yml");
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

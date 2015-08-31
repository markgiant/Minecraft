package me.giantcrack.gs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by shoot_000 on 3/29/2015.
 */
public class Config {

	private static Config instance = new Config();

	public static Config getInstance() {
		return instance;
	}

	private File f;

	private FileConfiguration cfg;

	private Config() {
	}

	public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		f = new File(p.getDataFolder(), "config.yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		cfg = YamlConfiguration.loadConfiguration(f);
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
	
	public String getString(String path) {
		return cfg.getString(path);
	}

	public boolean contains(String path) {
		return cfg.contains(path);
	}

	public ConfigurationSection createConfigurationSection(String path) {
		ConfigurationSection cs = cfg.createSection(path);
		save();
		return cs;
	}

	public void convertOldConfig() {
		
	}

	public FileConfiguration getConfig() {
		return cfg;
	}

	public int getInt(String path) {
		return cfg.getInt(path);
	}

	public void save() {
		try {
			cfg.save(f);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}

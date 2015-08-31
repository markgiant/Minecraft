package me.giantcrack.gs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Created by shoot_000 on 3/29/2015.
 */
public class ConverterFile {

	private static ConverterFile instance = new ConverterFile();

	public static ConverterFile getInstance() {
		return instance;
	}

	private File f;

	private FileConfiguration cfg;

	private ConverterFile() {
	}

	public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		f = new File(p.getDataFolder(), "converter.yml");
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

	public boolean contains(String path) {
		return cfg.contains(path);
	}

	public ConfigurationSection createConfigurationSection(String path) {
		ConfigurationSection cs = cfg.createSection(path);
		save();
		return cs;
	}

	public void convertOldConfig() {
		List<String> line = getConfig().getStringList("ShopItems");
		String[] newArray = new String[line.size()];
		for (int i = 0; i < line.size(); i++) {
			newArray[i] = line.get(i);
			String id = "";
			for (int j = 0; j < newArray[i].length(); j++) {
				if (String.valueOf(newArray[i].charAt(j)).equals(":"))
					break;
				id += String.valueOf(newArray[i].charAt(j));
			}
			int index = 0;
			int subIndex = 0;
			int count = 0;
			for (int b = 0; b < newArray[i].length(); b++) {
				if (count == 3) {
					index = b;
					count = 0;
					break;
				}
				if (String.valueOf(newArray[i].charAt(b)).equals(":")) {
					count++;
				}
			}
			for (int a = 0; a<newArray[i].length(); a++) {
				if (count == 4) {
					subIndex = a;
					break;
				}
				if (String.valueOf(newArray[i].charAt(a)).equals(":")) {
					count++;
				}
			}
			@SuppressWarnings("deprecation")
			ItemStack item = new ItemStack(Material.getMaterial(Integer
					.valueOf(id)), 1);
				ShopItem si = new ShopItem(item, Double.valueOf((subIndex > 0 ? newArray[i].substring(index, subIndex-1) : newArray[i].substring(index))));
				si.setSubID((subIndex > 0 ? Short.valueOf(newArray[i].substring(subIndex)) : (short)0));
				si.save();
				ItemManager.getInstance().items.add(si);
				ItemManager.getInstance().setUp();
		}
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

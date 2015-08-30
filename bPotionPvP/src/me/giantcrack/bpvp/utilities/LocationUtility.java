package me.giantcrack.bpvp.utilities;

import me.giantcrack.bpvp.files.ArenaData;
import me.giantcrack.bpvp.files.Signs;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtility {
	
	public static void saveLocation(String path, Location l) {
		ArenaData.getInstance().set(path + ".world" , l.getWorld().getName());
		ArenaData.getInstance().set(path + ".x", l.getX());
		ArenaData.getInstance().set(path + ".y", l.getY());
		ArenaData.getInstance().set(path + ".z", l.getZ());
		ArenaData.getInstance().set(path + ".pitch", l.getPitch());
		ArenaData.getInstance().set(path + ".yaw", l.getYaw());
        ArenaData.getInstance().save();
	}
	
	public static Location getLocation(String path) {
		String world = ArenaData.getInstance().get(path + ".world");
		double x = ArenaData.getInstance().getDouble(path + ".x");
		double y = ArenaData.getInstance().getDouble(path + ".y");
		double z = ArenaData.getInstance().getDouble(path + ".z");
		float pitch = (float) ArenaData.getInstance().getDouble(path + ".pitch");
		float yaw = (float) ArenaData.getInstance().getDouble(path + ".yaw");
		Location loc = new Location(Bukkit.getWorld(world), x,y,z);
		loc.setPitch(Float.valueOf(pitch).floatValue());
		loc.setYaw(Float.valueOf(yaw).floatValue());
		return loc;
	}

	public static void saveLocationSign(String path, Location l) {
		Signs.getInstance().set(path + ".world" , l.getWorld().getName());
		Signs.getInstance().set(path + ".x", l.getX());
		Signs.getInstance().set(path + ".y", l.getY());
		Signs.getInstance().set(path + ".z", l.getZ());
		Signs.getInstance().set(path + ".pitch", l.getPitch());
		Signs.getInstance().set(path + ".yaw", l.getYaw());
		Signs.getInstance().save();
	}

	public static Location getLocationSign(String path) {
		String world = Signs.getInstance().get(path + ".world");
		double x = Signs.getInstance().getDouble(path + ".x");
		double y = Signs.getInstance().getDouble(path + ".y");
		double z = Signs.getInstance().getDouble(path + ".z");
		float pitch = (float) Signs.getInstance().getDouble(path + ".pitch");
		float yaw = (float) Signs.getInstance().getDouble(path + ".yaw");
		Location loc = new Location(Bukkit.getWorld(world), x,y,z);
		loc.setPitch(Float.valueOf(pitch).floatValue());
		loc.setYaw(Float.valueOf(yaw).floatValue());
		return loc;
	}

}

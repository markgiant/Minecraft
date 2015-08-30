package me.giantcrack.mk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import sun.org.mozilla.javascript.internal.Kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shoot_000 on 6/14/2015.
 */
public class Main extends JavaPlugin implements Listener {


    public static List<String> uuids;

    List<String> u = new ArrayList<>();

    @Override
    public void onEnable() {
        task.clear();
        time.clear();
        Bukkit.getPluginManager().registerEvents(this, this);
        KitFile.getInstance().setup(this);
        if (KitFile.getInstance().<ConfigurationSection>get("Kits") == null) {
            KitFile.getInstance().createConfigurationSection("Kits.default");
            KitFile.getInstance().set("Kits.default.permission", "kits.default");
            KitFile.getInstance().set("Kits.default.kitName", "default");
            List<String> defaults = new ArrayList<>();
            defaults.add("1337");
            KitFile.getInstance().getConfig().addDefault("UsedKit",defaults);
            KitFile.getInstance().getConfig().options().copyDefaults(true);
            KitFile.getInstance().save();
            uuids = KitFile.getInstance().getList("UsedKit");
            return;
        }
        uuids = KitFile.getInstance().getList("UsedKit");
        u.addAll(uuids);
        loadKits();
        return;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (KitFile.getInstance().contains(p.getUniqueId() + ".timeLeft")) {
            int left = KitFile.getInstance().getInt(p.getUniqueId() + ".timeLeft");
            time.put(p,left);
            task.put(p, new BukkitRunnable() {
                @Override
                public void run() {
                    if (time.get(p) == 0) {
                        task.remove(p);
                        time.remove(p);
                        cancel();
                        return;
                    }
                    time.put(p, time.get(p) - 1);
                }
            });
            task.get(p).runTaskTimer(this,20,20);
        }
    }

    @Override
    public void onDisable() {
        writeCooldownsToFile();
        task.clear();
        time.clear();
    }


    public void writeCooldownsToFile() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!time.containsKey(online)) return;
            task.get(online).cancel();
            KitFile.getInstance().set(online.getUniqueId() + ".timeLeft", time.get(online));
            KitFile.getInstance().save();
        }
    }

    public void loadKits() {
        for (String name : KitFile.getInstance().<ConfigurationSection>get("Kits").getKeys(false)) {
            String perm = KitFile.getInstance().getString("Kits." + name + ".permission");
            String kitName = KitFile.getInstance().getString("Kits." + name + ".kitName");
            MattKit mk = new MattKit(name,perm,kitName);
        }
    }

    Map<Player,Integer> time = new HashMap<>();
    Map<Player,BukkitRunnable> task = new HashMap<>();

    public static String formatTime(int secs) {
        int remainder = secs % 86400;

        int days 	= secs / 86400;
        int hours 	= remainder / 3600;
        int minutes	= (remainder / 60) - (hours * 60);
        int seconds	= (remainder % 3600) - (minutes * 60);

        String fDays 	= (days > 0 	? " " + days + " day" 		+ (days > 1 ? "s" : "") 	: "");
        String fHours 	= (hours > 0 	? " " + hours + " hour" 	+ (hours > 1 ? "s" : "") 	: "");
        String fMinutes = (minutes > 0 	? " " + minutes + " minute"	+ (minutes > 1 ? "s" : "") 	: "");
        String fSeconds = (seconds > 0 	? " " + seconds + " second"	+ (seconds > 1 ? "s" : "") 	: "");

        return new StringBuilder().append(fDays).append(fHours)
                .append(fMinutes).append(fSeconds).toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("upgrade")) {
            if (!sender.hasPermission("upgrade.upgrade")) {
                sender.sendMessage(ChatColor.RED + "No permission!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.YELLOW + "/upgrade <name>");
                return true;
            }
            if (args.length == 1) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (!u.contains(player.getUniqueId().toString())) {
                    return true;
                }
                if (u.contains(player.getUniqueId().toString())) {
                   u.remove(player.getUniqueId().toString());
                    KitFile.getInstance().set("UsedKit",u);
                    KitFile.getInstance().save();
                    sender.sendMessage(ChatColor.AQUA + "Upgraded " + player.getName());
                    return true;
                }
//                u.add(player.getUniqueId().toString());
//                KitFile.getInstance().set("UsedKit",u);
//                KitFile.getInstance().save();
//                sender.sendMessage(ChatColor.AQUA + "You have disabled " + player.getName() + "'s upgrade!");
//                return true;
            }
        }
        return false;
    }

    public MattKit getAppropriateKit(Player p) {
        for (MattKit mk : MattKit.kits) {
            if (p.hasPermission(mk.getPermission())) {
                return mk;
            }
        }
        return null;
    }

    @EventHandler
    public void onKit(PlayerCommandPreprocessEvent e) {
        String[] args = e.getMessage().split(" ");
        final Player p = e.getPlayer();
        if (args.length != 2) return;
        if (args[0].equalsIgnoreCase("/kit") && args[1].equalsIgnoreCase("once")) {
            if (getAppropriateKit(p) == null) {
                p.sendMessage(ChatColor.RED + "Only donators have permission to use this kit!");
                e.setCancelled(true);
                return;
            } else if (u.contains(p.getUniqueId().toString())) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "You have already used your kit once!");
                return;
            } else if (time.containsKey(p)) {
                String timeLeft = formatTime(time.get(p));
                p.sendMessage(ChatColor.RED + "You've already used a \"kit once\" within 3 hours. Please wait " + ChatColor.GRAY + timeLeft + ChatColor.RED + " to do it again!");
                e.setCancelled(true);
                return;
            } else {
                time.put(p,10800);
                task.put(p, new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (time.get(p) == 0) {
                            task.remove(p);
                            time.remove(p);
                            cancel();
                            return;
                        }
                        time.put(p, time.get(p) - 1);
                    }
                });
                task.get(p).runTaskTimer(this,20,20);
                u.add(p.getUniqueId().toString());
                KitFile.getInstance().set("UsedKit",u);
                KitFile.getInstance().save();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"kit " + getAppropriateKit(p).getKitName() + " " + p.getName());
                p.sendMessage(ChatColor.GREEN + "You have received your kit "+ getAppropriateKit(p).getName() + "!");
                e.setCancelled(true);
            }
        }
    }




}

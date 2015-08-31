package me.giantcrack.mb;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class Events implements Listener {

    private int currentTime = (int) System.currentTimeMillis() / 1000;

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        int currentTime = (int) (System.currentTimeMillis() / 1000L);
        if (BanManager.getInstance().getBan(p) != null) {
            e.disallow(Result.KICK_OTHER, Main.prefix + ChatColor.WHITE + "You have been banned for " + BanManager.getInstance().getBan(p).getReason());
            return;
        }
        if (BanManager.getInstance().getBan(e.getAddress().getHostAddress()) != null) {
            e.disallow(Result.KICK_OTHER, Main.prefix + ChatColor.WHITE + "You have been banned for " + BanManager.getInstance().getBan(e.getAddress().getHostAddress()).getReason());
            return;
        }
        if (BanManager.getInstance().isSuspicious(e.getAddress().getHostAddress())) {
            PlayerManager.getInstance().handle(p, e.getAddress().getHostAddress());
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + p.getName() + " has logged on with a suspicious ip!", "mattban.staff");
            return;
        }
        if ((TempbanManager.getInstance().getBan(p) != null) && (TempbanManager.getInstance().getBan(p).isExpired())) {
            TempbanManager.getInstance().unbanPlayer(p);
            Bukkit.getLogger().log(Level.INFO, p.getName() + "'s tempban has expired!");
            e.allow();
            return;
        }
        if (TempbanManager.getInstance().getBan(p) != null) {
            e.disallow(Result.KICK_OTHER, Main.prefix + ChatColor.WHITE + "You have been temporarily banned!\n Time Remaining \n " + Main.getInstance().formatTime(TempbanManager.getInstance().getBan(p).getSecondsLeft()));
            return;
        }
        if (TempbanManager.getInstance().getBan(e.getAddress().getHostAddress()) != null) {
            e.disallow(Result.KICK_OTHER, Main.prefix + ChatColor.WHITE + "You have been temporarily banned!\n Time Remaining \n " + Main.getInstance().formatTime(TempbanManager.getInstance().getBan(p).getSecondsLeft()));
            return;
        }
        if (TempbanManager.getInstance().isSuspicious(e.getAddress().getHostAddress())) {
            PlayerManager.getInstance().handle(p, e.getAddress().getHostAddress());
            Bukkit.broadcast(Main.prefix + ChatColor.WHITE + p.getName() + " has logged on with a suspicious ip!", "mattban.staff");
            return;
        }
        PlayerManager.getInstance().handle(p, e.getAddress().getHostAddress());
    }
}


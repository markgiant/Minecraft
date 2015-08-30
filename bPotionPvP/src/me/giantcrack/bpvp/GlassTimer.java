package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markvolkov on 7/25/15.
 */
public class GlassTimer extends BukkitRunnable {

    private Player bitch;

    public static List<GlassTimer> timers = new ArrayList<>();

    public static GlassTimer getTimer(Player bitch) {
        for (GlassTimer timer : timers) {
            if (timer.getBitch().getName().equals(bitch.getName())) {
                return timer;
            }
        }
        return null;
    }

    public void end() {
        cancel();
        timers.remove(this);
    }

    public GlassTimer(Player bitch) {
        this.bitch = bitch;
        timers.add(this);
        runTaskTimer(Main.getInstance(),0,5);
    }

    public Player getBitch() {
        return bitch;
    }

    @Override
    public void run() {
        if (bitch.isOnline() && DuelManager.getInstance().getDuel(bitch) != null) {
            bitch.damage(2.0);
        } else {
            end();
        }
    }
}

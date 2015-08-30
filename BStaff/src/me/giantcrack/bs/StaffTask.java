package me.giantcrack.bs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by shoot_000 on 6/22/2015.
 */
public class StaffTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (Player staff : Events.staff) {
                if (!Main.getInstance().isStaff(online)){
                    online.hidePlayer(staff);
                    continue;
                }
                if (online.isFlying()) continue;
                online.setAllowFlight(true);
            }
        }
    }
}

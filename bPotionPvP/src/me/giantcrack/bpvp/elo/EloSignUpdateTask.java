package me.giantcrack.bpvp.elo;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by markvolkov on 8/26/15.
 */
public class EloSignUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        SignManager.get().updateAll();
    }
}

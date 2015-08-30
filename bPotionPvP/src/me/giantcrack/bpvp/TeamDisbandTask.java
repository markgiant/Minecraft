package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.DuelManager;
import me.giantcrack.bpvp.duels.InventoryHandler;
import me.giantcrack.bpvp.teams.Team;
import me.giantcrack.bpvp.teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

/**
 * Created by markvolkov on 8/27/15.
 */
public class TeamDisbandTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Team t : TeamManager.getInstance().teams) {
            if (t.canDisband() && DuelManager.getInstance().getDuel(t.getOwner()) == null) {
                t.setJoinable(false);
                InventoryHandler.giveDefaultItems(t.getOwner());
                t.getOwner().sendMessage(ChatColor.RED + "You have disbanded your team!");
                try {
                    Iterator<Player> i = t.getMembers().iterator();
                    while (i.hasNext()) {
                        Player pl = i.next();
                        if (pl.isOnline() && pl != null && !pl.getName().equals(t.getOwner().getName())) {
                            i.remove();
                            TeamManager.getInstance().createTeam(pl);
                            InventoryHandler.giveDefaultItems(pl);
                            pl.sendMessage(ChatColor.RED + "Your team has been disbanded!");
                        }
                    }
                } catch (NullPointerException ex) {

                }
            }
        }
    }
}

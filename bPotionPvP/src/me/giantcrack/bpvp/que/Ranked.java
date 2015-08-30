package me.giantcrack.bpvp.que;

import me.giantcrack.bpvp.duels.DuelManager;
import me.giantcrack.bpvp.duels.DuelType;
import me.giantcrack.bpvp.duels.InventoryHandler;
import me.giantcrack.bpvp.elo.EloManager;
import me.giantcrack.bpvp.elo.Range;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.que.Que;
import me.giantcrack.bpvp.que.QueType;
import me.giantcrack.bpvp.teams.Team;
import me.giantcrack.bpvp.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by shoot_000 on 6/27/2015.
 */
public class Ranked extends Que {

    public Ranked(Kit k, QueType type) {
        super(k, type);
    }

    @Override
    public void addPlayer(Player p) {
        super.addPlayer(p);
        Range r = new Range(p, EloManager.getInstance().getElo(p,getKit()).getElo());
    }

    @Override
    public void run() {
        for (int i = 0; i < getPlayers().size(); i++) {
            for (int j = 0; j < getPlayers().size(); j++) {
                if (getPlayers().get(i).getName().equals(getPlayers().get(j).getName())) continue;
                if (Range.getRange(getPlayers().get(i)).inRange(Range.getRange(getPlayers().get(j)))) {
                    Player one = getPlayers().get(i);
                    Player two = getPlayers().get(j);
                    if (InventoryHandler.search.containsKey(one)) {
                        InventoryHandler.search.remove(one);
                    }
                    if (InventoryHandler.search.containsKey(two)) {
                        InventoryHandler.search.remove(two);
                    }
                    if (InventoryHandler.task.containsKey(one)) {
                        InventoryHandler.task.get(one).cancel();
                        InventoryHandler.task.remove(one);
                    }
                    if (InventoryHandler.task.containsKey(two)) {
                        InventoryHandler.task.get(two).cancel();
                        InventoryHandler.task.remove(two);
                    }
                    Team team1 = TeamManager.getInstance().getTeam(one);
                    Team team2 = TeamManager.getInstance().getTeam(two);
                    DuelManager.getInstance().createDuel(team1,team2, getKit(), DuelType.RANKED);
                    removePlayer(team1.getOwner());
                    removePlayer(team2.getOwner());
                }
            }
        }
    }
}

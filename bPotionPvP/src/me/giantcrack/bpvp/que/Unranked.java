package me.giantcrack.bpvp.que;

import me.giantcrack.bpvp.duels.DuelManager;
import me.giantcrack.bpvp.duels.DuelType;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.que.Que;
import me.giantcrack.bpvp.que.QueType;
import me.giantcrack.bpvp.teams.Team;
import me.giantcrack.bpvp.teams.TeamManager;

import java.util.Random;

/**
 * Created by shoot_000 on 6/25/2015.
 */
public class Unranked extends Que {

    public Unranked(Kit k, QueType type) {
        super(k, type);
    }

    @Override
    public void run() {
        if (getPlayers().isEmpty() || getPlayers().size() < 2) return;
        Team team1 = TeamManager.getInstance().getTeam(getPlayers().get(0));
        Team team2 = TeamManager.getInstance().getTeam(getPlayers().get(1));
        DuelManager.getInstance().createDuel(team1,team2, getKit(), DuelType.UNRANKED);
        getPlayers().remove(team1.getOwner());
        getPlayers().remove(team2.getOwner());
    }
}

package me.giantcrack.bpvp.duels;

import me.giantcrack.bpvp.teams.Team;
import org.bukkit.entity.Player;

/**
 * Created by shoot_000 on 7/16/2015.
 */
public class DeathTracker {

    private Duel d;
    private Team team1, team2;
    private int t1Max, t2Max;

    public DeathTracker(Duel d) {
        this.d = d;
        this.team1 = d.getTeam1();
        this.team2 = d.getTeam2();
        this.t1Max = d.getTeam1().getMembers().size();
        this.t2Max = d.getTeam2().getMembers().size();
    }


    public int getMaxDeathsTeam1() {
        return t1Max;
    }

    public int getMaxDeathsTeam2() {
        return t2Max;
    }

    private int team1Tracker,team2Tracker = 0;

    public int getTeam1Deaths() {
        return team1Tracker;
    }

    public int getTeam2Deaths() {
        return team2Tracker;
    }


    public void addDeath(Player p) {
        if (this.team1.getMembers().contains(p)) {
            this.team1Tracker += 1;
            if (this.getTeam1Deaths() - this.getMaxDeathsTeam1() == 0) {
                d.setWinner(team2);
                d.end();
            }
        } else {
            this.team2Tracker+=1;
            if (this.getTeam2Deaths() - this.getMaxDeathsTeam2() == 0) {
                d.setWinner(team1);
                d.end();
            }
        }
    }

}

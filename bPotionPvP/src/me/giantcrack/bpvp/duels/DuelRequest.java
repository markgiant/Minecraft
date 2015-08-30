package me.giantcrack.bpvp.duels;

import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.teams.Team;

/**
 * Created by shoot_000 on 6/27/2015.
 */
public class DuelRequest {

    public Team t1,t2;
    public Kit k;

    public DuelRequest(Team t1, Team t2, Kit k) {
        this.t1 = t1;
        this.t2 = t2;
        this.k = k;
    }
}

package me.giantcrack.bpvp.teams;

import org.bukkit.entity.Player;

/**
 * Created by shoot_000 on 7/15/2015.
 */
public class TeamInvite
{

    Team team;
    Player invited;

    public TeamInvite(Team team, Player invited) {
        this.team = team;
        this.invited = invited;
    }
}

package me.giantcrack.bpvp.teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class TeamManager {

    private static TeamManager instance = new TeamManager();

    private TeamManager() {}

    public static TeamManager getInstance() {
        return instance;
    }

    public List<Team> teams = new ArrayList<>();

    public List<TeamInvite> invites = new ArrayList<>();

    public Team getTeam(Player p) {
        for (Team t : teams) {
            if (t.getOwner().getName().equals(p.getName()) || t.getMembers().contains(p)) {
                return t;
            }
        }
        return null;
    }

    public boolean isOwner(Player p, Team t) {
        return t.getOwner().getName().equals(p.getName());
    }

    public void createTeam(Player p) {
        if (getTeam(p) != null) {
            if (isOwner(p, getTeam(p))) {
                teams.remove(getTeam(p));
            } else {
                getTeam(p).getMembers().remove(p);
            }
        }
        Team t = new Team(p);
        teams.add(t);
        return;
    }

    public TeamInvite getInvites(Player p, Team t) {
        for (TeamInvite ti : invites) {
            if (ti.invited.getName().equals(p.getName()) && ti.team.getOwner().getName().equals(t.getOwner().getName())) {
                return ti;
            }
        }
        return null;
    }

    public void clearTeamInvites(Player p) {
        try {
            Iterator<TeamInvite> i = invites.iterator();
            while(i.hasNext()) {
                TeamInvite ti = i.next();
                if (ti.invited.getName().equals(p.getName())) {
                    i.remove();
                }
            }
        } catch (NullPointerException ex) {

        }
    }


    public void invitePlayer(Team t,Player p) {
        clearTeamInvites(p);
        TeamInvite invite = new TeamInvite(t,p);
        invites.add(invite);
        t.sendMessage(ChatColor.GREEN + p.getName() + " was invited to the team!");
        p.sendMessage(ChatColor.GREEN + t.getOwner().getName() + " invited you to join their team!");
        p.sendMessage(ChatColor.GOLD + "/team join " + t.getOwner().getName());
        return;
    }

}

package me.giantcrack.bpvp.teams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class Team {

    private Player owner;
    private boolean joinable, disband;
    private List<Player> members;

    public Team(Player owner) {
        this.owner = owner;
        this.members = new ArrayList<>();
        this.members.add(owner);
        this.joinable = false;
        this.disband = false;
    }

    public void setDisbanded() {
        this.disband = true;
    }

    public boolean canDisband() {
        return this.disband;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public void sendMessage(String str) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getName().equals(owner.getName()) || members.contains(online)) {
                online.sendMessage(str);
            }
        }
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

    public boolean isJoinable() {
        return joinable;
    }

    public void addMember(Player p) {
        if (members.contains(p)) return;
        if (!joinable) return;
        members.add(p);
    }

    public void removeMember(Player p) {
        if (!members.contains(p)) return;
        members.remove(p);
    }
}

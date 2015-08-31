package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.DuelManager;
import me.giantcrack.bpvp.listeners.InventoryHandler;
import me.giantcrack.bpvp.que.QueManager;
import me.giantcrack.bpvp.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

/**
 * Created by shoot_000 on 7/15/2015.
 */
public class TeamCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Must be a player!");
            return true;
        }
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "/team <create | leave | join | invite>");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("create")) {
                if (TeamManager.getInstance().getTeam(p).isJoinable() && TeamManager.getInstance().getTeam(p) != null) {
                    p.sendMessage(ChatColor.RED + "You are already on a team!");
                    return true;
                }
                TeamManager.getInstance().getTeam(p).setJoinable(true);
                InventoryHandler.giveTeamItems(p);
                if (QueManager.getInstance().getQue(p) != null) {
                    QueManager.getInstance().getQue(p).removePlayer(p);
                }
                if (InventoryHandler.search.containsKey(p)) {
                    InventoryHandler.search.remove(p);
                }
                if (InventoryHandler.task.containsKey(p)) {
                    InventoryHandler.task.get(p).cancel();
                    InventoryHandler.task.remove(p);
                }
                p.sendMessage(ChatColor.GREEN + "You have created a team!");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                if (!TeamManager.getInstance().getTeam(p).isJoinable()) {
                    p.sendMessage(ChatColor.RED + "You are not on a team!");
                    return true;
                }
                if (DuelManager.getInstance().getDuel(p) != null && TeamManager.getInstance().getTeam(p).getOwner().getName().equals(p.getName())) {
                    TeamManager.getInstance().getTeam(p).sendMessage(ChatColor.RED + "Your team leader has queued your team to be disbanded!");
                    TeamManager.getInstance().getTeam(p).sendMessage(ChatColor.RED + "Your team will disband after your duel!");
                    TeamManager.getInstance().getTeam(p).setDisbanded();
                    return true;
                }
                if (TeamManager.getInstance().getTeam(p).getOwner().getName().equals(p.getName())) {
                    TeamManager.getInstance().getTeam(p).setJoinable(false);
                    InventoryHandler.giveDefaultItems(p);
                    p.sendMessage(ChatColor.RED + "You have disbanded your team!");
                    try {
                        Iterator<Player> i = TeamManager.getInstance().getTeam(p).getMembers().iterator();
                        while (i.hasNext()) {
                            Player pl = i.next();
                            if (pl.isOnline() && pl != null && !pl.getName().equals(p.getName())) {
                                i.remove();
                                TeamManager.getInstance().createTeam(pl);
                                InventoryHandler.giveDefaultItems(pl);
                                pl.sendMessage(ChatColor.RED + "Your team has been disbanded!");
                            }
                        }
                    } catch (NullPointerException ex) {

                    }
                } else {
                    TeamManager.getInstance().getTeam(p).sendMessage(ChatColor.RED + p.getName() + " has left the team!");
                    TeamManager.getInstance().getTeam(p).removeMember(p);
                    InventoryHandler.giveDefaultItems(p);
                    return true;
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(ChatColor.RED + args[0] + " was not found!");
                    return true;
                }
                if (!TeamManager.getInstance().getTeam(target).isJoinable()) {
                    p.sendMessage(ChatColor.RED + "That player is not on a team!");
                    return true;
                }
                if (TeamManager.getInstance().getTeam(p).isJoinable()) {
                    p.sendMessage(ChatColor.RED + "You are already on a team!");
                    return true;
                }
                if (TeamManager.getInstance().getInvites(p,TeamManager.getInstance().getTeam(target)) != null) {
                    if (QueManager.getInstance().getQue(p) != null) {
                        QueManager.getInstance().getQue(p).removePlayer(p);
                    }
                    if (InventoryHandler.search.containsKey(p)) {
                        InventoryHandler.search.remove(p);
                    }
                    if (InventoryHandler.task.containsKey(p)) {
                        InventoryHandler.task.get(p).cancel();
                        InventoryHandler.task.remove(p);
                    }
                    TeamManager.getInstance().clearTeamInvites(p);
                    TeamManager.getInstance().teams.remove(TeamManager.getInstance().getTeam(p));
                    TeamManager.getInstance().getTeam(target).addMember(p);
                    InventoryHandler.giveTeamItems(p);
                    TeamManager.getInstance().getTeam(p).sendMessage(ChatColor.GREEN + p.getName() + " has joined the team!");
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + "You haven't been invited to that team!");
                    return true;
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("invite")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(ChatColor.RED + args[1] + " was not found!");
                    return true;
                }
                if (target.getName().equals(p.getName())) {
                    p.sendMessage(ChatColor.RED + "You can't invite yourself!");
                    return true;
                }
                if (!TeamManager.getInstance().getTeam(p).isJoinable()) {
                    p.sendMessage(ChatColor.RED + "You are not on a team! /team create!");
                    return true;
                }
                if (TeamManager.getInstance().getInvites(p,TeamManager.getInstance().getTeam(target)) != null) {
                    p.sendMessage(ChatColor.RED + "That player already has a current invite for your team!");
                    return true;
                } else {
                    TeamManager.getInstance().invitePlayer(TeamManager.getInstance().getTeam(p),target);
                    return true;
                }
            }
        }
        return false;
    }
}

package me.giantcrack.bpvp;

import me.giantcrack.bpvp.duels.DuelManager;
import me.giantcrack.bpvp.duels.DuelType;
import me.giantcrack.bpvp.listeners.InventoryHandler;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.que.QueManager;
import me.giantcrack.bpvp.teams.Team;
import me.giantcrack.bpvp.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shoot_000 on 6/27/2015.
 */
public class DuelCmd implements CommandExecutor {

    //TODO: Handle requests and shtuff

    public static Map<Team, Team> tracker = new HashMap<>();

    public static Map<Team,Kit> ktracker = new HashMap<>();


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can enter duels!");
            return true;
        }
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("duel")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "/duel <name>");
                p.sendMessage(ChatColor.RED + "Choose a specific player to duel!");
                //Bukkit.broadcastMessage(ChatColor.GREEN + "" + EloManager.getInstance().getHighestElo(KitManager.getInstance().getKit("Archer")).getUserName());
                return true;
            }
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(ChatColor.RED + "That player is not online!");
                    return true;
                }
                if (DuelManager.getInstance().getDuel(target) != null || QueManager.getInstance().getQue(target) != null) {
                    p.sendMessage(ChatColor.RED + target.getName() + " is either in a duel or in a queue!");
                    return true;
                }
                if (QueManager.getInstance().getQue(p) != null) {
                    p.sendMessage(ChatColor.RED + "You must first leave your queue by clicking on it's icon again!");
                    return true;
                }
                if (target.getName().equals(p.getName())) {
                    p.sendMessage(ChatColor.RED + "You can't duel your self silly!");
                    return true;
                }
                Team myTeam = TeamManager.getInstance().getTeam(p);
                Team targetTeam = TeamManager.getInstance().getTeam(target);
                if (!myTeam.isJoinable() && !targetTeam.isJoinable()) {
                    //Regular duel (One player teams)
                    InventoryHandler.openKitOptions(p);
                    tracker.put(myTeam,targetTeam);
                    return true;
                } else if (myTeam.isJoinable() && targetTeam.isJoinable()) {
                    if (!myTeam.getOwner().getName().equals(p.getName())) {
                        p.sendMessage(ChatColor.RED + "Only the team captain can duel other teams!");
                        return true;
                    }
                    if (myTeam.getMembers().size() < 2) {
                        p.sendMessage(ChatColor.RED + "You need at least 2 people to duel other teams!");
                        return true;
                    }
                    if (targetTeam.getMembers().size() < 2) {
                        p.sendMessage(ChatColor.RED + "That persons team needs at least 2 people to participate in team fights!");
                        return true;
                    }
                    //Team duel (Greater than one member in size)
                    InventoryHandler.openKitOptions(p);
                    tracker.put(myTeam,targetTeam);
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + "A duel exception has occurred!");
                    p.sendMessage(ChatColor.RED + "Incompatible duel types!");
                    return true;
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("accept")) {
            if (args.length == 0 || args.length > 1) {
                p.sendMessage(ChatColor.RED + "/accept <name>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ChatColor.RED + args[0] + " was not found!");
                return true;
            }
            Team myTeam = TeamManager.getInstance().getTeam(p);
            Team targetTeam = TeamManager.getInstance().getTeam(target);
            if (DuelManager.getInstance().getRequest(targetTeam,myTeam,ktracker.get(targetTeam)) != null) {
                if ((myTeam.isJoinable() && !targetTeam.isJoinable()) || (!myTeam.isJoinable() && targetTeam.isJoinable())) {
                    p.sendMessage(ChatColor.RED + "A duel exception has occurred!");
                    p.sendMessage(ChatColor.RED + "Incompatible duel types!");
                    return true;
                }
                if (myTeam.isJoinable() && targetTeam.isJoinable()) {
                    if (!myTeam.getOwner().getName().equals(p.getName())) {
                        p.sendMessage(ChatColor.RED + "Only the team captain can accept the request!");
                        return true;
                    }
                    DuelManager.getInstance().createDuel(targetTeam,myTeam,ktracker.get(targetTeam), DuelType.TEAM);
                    myTeam.sendMessage(ChatColor.GREEN + "Your team is now dueling against " + ChatColor.RED + targetTeam.getOwner().getName() + ChatColor.GREEN + "!");
                    targetTeam.sendMessage(ChatColor.GREEN + "Your team is now dueling against " + ChatColor.RED + myTeam.getOwner().getName() + ChatColor.GREEN + "!");
                    ktracker.remove(targetTeam);
                    return true;
                }
                DuelManager.getInstance().createDuel(targetTeam,myTeam,ktracker.get(targetTeam), DuelType.UNRANKED);
                myTeam.sendMessage(ChatColor.GREEN + "You are now dueling against " + ChatColor.RED + targetTeam.getOwner().getName() + ChatColor.GREEN + "!");
                targetTeam.sendMessage(ChatColor.GREEN + "You are now dueling against " + ChatColor.RED + myTeam.getOwner().getName() + ChatColor.GREEN + "!");
                ktracker.remove(targetTeam);
                return true;
            } else {
                p.sendMessage(ChatColor.RED + "That request doesn't exist!");
                return true;
            }
        }
        return false;
    }

}

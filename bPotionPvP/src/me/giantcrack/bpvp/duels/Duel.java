package me.giantcrack.bpvp.duels;

import me.giantcrack.bpvp.EntityHider;
import me.giantcrack.bpvp.Main;
import me.giantcrack.bpvp.arenas.Arena;
import me.giantcrack.bpvp.elo.EloManager;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.kits.KitManager;
import me.giantcrack.bpvp.listeners.InventoryHandler;
import me.giantcrack.bpvp.teams.Team;
import me.giantcrack.bpvp.teams.TeamManager;
import me.giantcrack.bpvp.utilities.LocationUtility;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class Duel {

    private Team team1, team2, winner;
    private Arena a;
    private Kit k;
    private EntityHider hider;
    private DuelState state;
    private DuelType type;
    private DeathTracker tracker;

    private Map<Team, Integer> deathTracker = new HashMap<>();

    public Map<Player, Player> pairs = new HashMap<>();

    public void spectate(Player rider, Player spectated) {
        if (pairs.containsKey(rider) && pairs.get(rider).getName().equals(spectated.getName())) {
            pairs.remove(rider);
            showAll(rider);
            rider.eject();
            rider.sendMessage(ChatColor.YELLOW + "No longer spectating " + ChatColor.RED + spectated.getName());
            return;
        } else if (pairs.containsKey(rider) && !pairs.get(rider).getName().equals(spectated.getName())) {
            pairs.remove(rider);
            showAll(rider);
            rider.eject();
            rider.sendMessage(ChatColor.YELLOW + "No longer spectating " + ChatColor.RED + pairs.get(rider).getName());
            pairs.put(rider, spectated);
            spectated.setPassenger(rider);
            hideAll(rider);
            rider.sendMessage(ChatColor.YELLOW + "Now spectating " + ChatColor.RED + spectated.getName());
            return;
        } else {
            pairs.put(rider, spectated);
            spectated.setPassenger(rider);
            hideAll(rider);
            rider.sendMessage(ChatColor.YELLOW + "Now spectating " + ChatColor.RED + spectated.getName());
        }
    }

    public void hideAll(Player p) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getName().equals(p.getName())) continue;
            this.hider.hideEntity(online, p);
        }
    }

    public void showAll(Player p) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getName().equals(p.getName())) continue;
            this.hider.showEntity(online, p);
        }
    }

    public Duel(Team team1, Team team2, Arena a, Kit k, DuelType type) {
        this.team1 = team1;
        this.team2 = team2;
        this.winner = null;
        this.a = a;
        this.k = k;
        this.type = type;
        this.tracker = new DeathTracker(this);
        this.hider = new EntityHider(Main.getInstance(), EntityHider.Policy.BLACKLIST);
        this.state = DuelState.STARTING;
        for (Player p : team1.getMembers()) {
            this.a.addPlayer(p);
        }
        for (Player p : team2.getMembers()) {
            this.a.addPlayer(p);
        }
    }

    public DeathTracker getTracker() {
        return tracker;
    }

    public Map<Team, Integer> getDeathTracker() {
        return deathTracker;
    }

    public void heal(Player p) {
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
        p.teleport(LocationUtility.getLocation("Spawn"));
        if (TeamManager.getInstance().getTeam(p).isJoinable()) {
            InventoryHandler.giveTeamItems(p);
        } else {
            InventoryHandler.giveDefaultItems(p);
        }
        p.updateInventory();
        //checkSpecifications();
    }


    public void start() {
        //checkSpecifications();
        this.state = DuelState.INGAME;
    }


    public void end() {
        //Bukkit.broadcastMessage("Called end method")
//            if (team1.getMembers().contains(online) || team2.getMembers().contains(online)) continue;
//            for (Player p : team1.getMembers()) {
//                this.hider.showEntity(p, online);
//            }
//            Bukkit.broadcastMessage("Showed team1");
//            for (Player p : team2.getMembers()) {
//                this.hider.showEntity(p, online);
//            }
//            Bukkit.broadcastMessage("Showed team2");
        this.state = DuelState.ENDING;
        Bukkit.getScheduler().cancelTask(this.taskID);
        // Bukkit.broadcastMessage("Closed hider");
        Player winner = getWinner().getOwner();
        Player loser = getOtherTeam(winner).getOwner();
        if (this.type == DuelType.RANKED) {
            EloManager.getInstance().handleRankedMatch(EloManager.getInstance().getElo(winner, k), EloManager.getInstance().getElo(loser, k));
            winner.sendMessage(ChatColor.RED + "Your new ELO is " + ChatColor.GREEN + EloManager.getInstance().getElo(winner, k).getElo());
            loser.sendMessage(ChatColor.RED + "Your new ELO is " + ChatColor.GREEN + EloManager.getInstance().getElo(loser, k).getElo());
        }
        if (!getWinner().isJoinable()) {
            getOtherTeam(winner).sendMessage(ChatColor.RED + "You lost a duel to " + ChatColor.BLUE + getWinner().getOwner().getName() + ChatColor.RED + "!");
            getWinner().sendMessage(ChatColor.GREEN + "You won a duel against " + ChatColor.BLUE + getOtherTeam(winner).getOwner().getName() + ChatColor.GREEN + "!");
            for (Player p : getWinner().getMembers()) {
                if (InventoryManager.get().getInv(p) != null) continue;
                Collection<ItemStack> items = new ArrayList<>();
                items.addAll(Arrays.asList(p.getInventory().getContents()));
                items.addAll(Arrays.asList(p.getInventory().getArmorContents()));
                InventoryManager.get().createDeathInv(p, items);
            }
            FancyMessage fm = new FancyMessage("Click to view inventories: ").color(ChatColor.RED);
            Collection<Player> allPlayers = new ArrayList<>();
            allPlayers.addAll(team1.getMembers());
            allPlayers.addAll(team2.getMembers());
            for (Player p : allPlayers) {
                fm.then(p.getName() + " ").color(ChatColor.BLUE).command("/di " + p.getName());
            }
            fm.send(team1.getMembers());
            fm.send(team2.getMembers());
            allPlayers = new ArrayList<>();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for (Player online : a.getArenaPlayers()) {
                        if (team1.getMembers().contains(online) || team2.getMembers().contains(online)) continue;
                        if (DuelManager.getInstance().getDuel(online) != null) continue;
                        for (Player p : team1.getMembers()) {
                            if (!online.isOnline()) continue;
                            hider.showEntity(p, online);
                            hider.showEntity(online, p);
                        }
                        for (Player p : team2.getMembers()) {
                            if (!online.isOnline()) continue;
                            hider.showEntity(p, online);
                            hider.showEntity(online, p);
                        }
                    }
                    for (Player p : getWinner().getMembers()) {
                        heal(p);
                    }
                    hider.close();
                }
            }, 20 * 5);
            for (Player p : team1.getMembers()) {
                this.a.removePlayer(p);
            }
            for (Player p : team2.getMembers()) {
                this.a.removePlayer(p);
            }
            DuelManager.getInstance().duels.remove(this);
            return;
        } else {
            getOtherTeam(winner).sendMessage(ChatColor.RED + "Your team lost a duel to " + ChatColor.AQUA + getWinner().getOwner().getName() + "'s " + ChatColor.RED + "Team!");
            getWinner().sendMessage(ChatColor.GREEN + "Your team won a duel against " + ChatColor.AQUA + getOtherTeam(winner).getOwner().getName() + "'s " + ChatColor.GREEN + "Team!");
            for (Player p : getWinner().getMembers()) {
                if (InventoryManager.get().getInv(p) != null) continue;
                Collection<ItemStack> items = new ArrayList<>();
                items.addAll(Arrays.asList(p.getInventory().getContents()));
                items.addAll(Arrays.asList(p.getInventory().getArmorContents()));
                InventoryManager.get().createDeathInv(p, items);
            }
            FancyMessage fm = new FancyMessage("Click to view inventories: ").color(ChatColor.RED);
            Collection<Player> allPlayers = new ArrayList<>();
            allPlayers.addAll(team1.getMembers());
            allPlayers.addAll(team2.getMembers());
            for (Player p : allPlayers) {
                fm.then(p.getName() + " ").color(ChatColor.BLUE).command("/di " + p.getName());
            }
            fm.send(team1.getMembers());
            fm.send(team2.getMembers());
            allPlayers = new ArrayList<>();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for (final Player online : a.getArenaPlayers()) {
                        if (team1.getMembers().contains(online) || team2.getMembers().contains(online)) continue;
                        if (DuelManager.getInstance().getDuel(online) != null) continue;
                        for (Player p : team1.getMembers()) {
                            if (!online.isOnline()) continue;
                            hider.showEntity(p, online);
                            hider.showEntity(online, p);
                        }
                        for (Player p : team2.getMembers()) {
                            if (!online.isOnline()) continue;
                            hider.showEntity(p, online);
                            hider.showEntity(online, p);
                        }

                    }
                    for (Player p : getWinner().getMembers()) {
                        heal(p);
                    }
                    hider.close();
                }
            }, 20 * 5);
            for (Player p : team1.getMembers()) {
                this.a.removePlayer(p);
            }
            for (Player p : team2.getMembers()) {
                this.a.removePlayer(p);
            }
            DuelManager.getInstance().duels.remove(this);
            return;
        }

        // Bukkit.broadcastMessage("Healed winners");
        //Bukkit.broadcastMessage("Removed duel from ram");
    }

    public Team getLosers() {
        if (team1.getOwner().getName().equals(getWinner().getOwner().getName())) {
            return team2;
        }
        return team2;
    }

    public Team getOtherTeam(Player p) {
        if (team1.getMembers().contains(p)) return team2;
        return team1;
    }

    private int taskID;

    private int countDown = 5;


//    public void checkSpecifications() {
//        int count = 0;
//        for (Player p : team1.getMembers()) {
//            if (!p.isOnline()) {
//                count++;
//            }
//        }
//        try {
//            if (count == team1.getMembers().size()) {
//                team2.sendMessage(ChatColor.RED + "Your opponent forfeited. You win by default.");
//            }
//            for (Player p : team2.getMembers()) {
//                if (!p.isOnline()) {
//                    count++;
//                }
//            }
//            if (count == team2.getMembers().size()) {
//                team1.sendMessage(ChatColor.RED + "Your opponent forfeited. You win by default.");
//            }
//            end();
//        } catch (NullPointerException ex) {

    //}
//        if (team1.getMembers().isEmpty() || team1 == null) {
//            team2.sendMessage(ChatColor.RED + "Your opponent forfeited. You win by default.");
//            end();
//        } else if (team2.getMembers().isEmpty() || team2 == null) {
//            team1.sendMessage(ChatColor.RED + "Your opponent forfeited. You win by default.");
//            end();
//        } else if (team1.getMembers().isEmpty() || team1 == null && team2.getMembers().isEmpty() || team2 == null) {
//            DuelManager.getInstance().duels.remove(this);
//        }
    //}


    public void giveBooks(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        if (KitManager.getInstance().getKitEdits(p, k).isEmpty()) {
            KitManager.getInstance().getKit(k.getName()).giveDefaultKit(p);
            return;
        }
        int count = 1;
        try {
            ItemStack defaultb = new ItemStack(Material.BOOK);
            ItemMeta dmeta = defaultb.getItemMeta();
            dmeta.setDisplayName(ChatColor.YELLOW + "Default");
            dmeta.setLore(Arrays.asList(ChatColor.GOLD + "Kit: " + ChatColor.GREEN + k.getName()));
            defaultb.setItemMeta(dmeta);
            p.getInventory().addItem(defaultb);
            while (count < 6) {
                if (KitManager.getInstance().getKitEdit(p, k, count) == null) {
                    count++;
                    continue;
                }
                ItemStack book = new ItemStack(Material.BOOK);
                ItemMeta meta = book.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + String.valueOf(count));
                meta.setLore(Arrays.asList(ChatColor.GOLD + "Kit: " + ChatColor.GREEN + k.getName()));
                book.setItemMeta(meta);
                p.getInventory().addItem(book);
                count++;
            }
        } catch (NullPointerException ex) {

        }
    }


    public void teleport(Team team, Location l) {
        Location nl = new Location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ(), l.getYaw(), l.getPitch());
        for (Player p : team.getMembers()) {
            p.teleport(nl);
        }
    }

    public void tick() {
//        for (Player online : Bukkit.getOnlinePlayers()) {
//            if (team1.getMembers().contains(online) || team2.getMembers().contains(online)) continue;
//            for (Player p : team1.getMembers()) {
//                this.hider.hideEntity(p, online);
//            }
//            for (Player p : team2.getMembers()) {
//                this.hider.hideEntity(p, online);
//            }
//        }
//
//        for (Player online : Bukkit.getOnlinePlayers()) {
//            if (!team1.getMembers().contains(online) || !team2.getMembers().contains(online)) continue;
//            for (Player p : team1.getMembers()) {
//                if (p.getName().equals(online.getName())) continue;
//                this.hider.showEntity(p, online);
//            }
//            for (Player p : team2.getMembers()) {
//                if (p.getName().equals(online.getName())) continue;
//                this.hider.showEntity(p, online);
//            }
//        }


    }

    public void countdown() {
        //checkSpecifications();
        //Give books to get kits and check if there are no kits, load default, or just give default book
        for (Player online : this.a.getArenaPlayers()) {
            if (team1.getMembers().contains(online) || team2.getMembers().contains(online)) continue;
            for (Player p : team1.getMembers()) {
                this.hider.hideEntity(p, online);
                this.hider.hideEntity(online, p);
            }
            for (Player p : team2.getMembers()) {
                this.hider.hideEntity(p, online);
                this.hider.hideEntity(online, p);
            }
        }
        teleport(team1, a.getSpawn1());
        teleport(team2, a.getSpawn2());
        for (Player p : team1.getMembers()) {
            giveBooks(p);
            if (type == DuelType.RANKED) {
                team1.sendMessage(ChatColor.AQUA + "Current ELO: " + ChatColor.YELLOW + EloManager.getInstance().getElo(p, k).getElo());
                team1.sendMessage(ChatColor.AQUA + "Current Opponent: " + ChatColor.YELLOW + team2.getOwner().getName());
                team1.sendMessage(ChatColor.AQUA + "Opponent's ELO: " + ChatColor.YELLOW + EloManager.getInstance().getElo(getOtherTeam(p).getOwner(), k).getElo());
            }
        }
        for (Player p : team2.getMembers()) {
            giveBooks(p);
            if (type == DuelType.RANKED) {
                team2.sendMessage(ChatColor.AQUA + "Current ELO: " + ChatColor.YELLOW + EloManager.getInstance().getElo(p, k).getElo());
                team2.sendMessage(ChatColor.RED + "Current Opponent: " + ChatColor.GRAY + team1.getOwner().getName());
                team2.sendMessage(ChatColor.AQUA + "Opponent's ELO: " + ChatColor.YELLOW + EloManager.getInstance().getElo(getOtherTeam(p).getOwner(), k).getElo());
            }
        }

        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (countDown == 0) {
                    start();
                    //checkSpecifications();
                    Bukkit.getScheduler().cancelTask(taskID);
                    team1.sendMessage(ChatColor.YELLOW + "GO " + ChatColor.GREEN + "GO " + ChatColor.RED + "GO" + ChatColor.BLUE + "!!!");
                    team2.sendMessage(ChatColor.YELLOW + "GO " + ChatColor.GREEN + "GO " + ChatColor.RED + "GO" + ChatColor.BLUE + "!!!");
                    return;
                }
                //checkSpecifications();
                team1.sendMessage(ChatColor.GREEN + "Match starting in " + ChatColor.GOLD + countDown + ChatColor.GREEN + " seconds");
                team2.sendMessage(ChatColor.GREEN + "Match starting in " + ChatColor.GOLD + countDown + ChatColor.GREEN + " seconds");
                countDown--;
                //checkSpecifications();
                return;
            }
        }, 20, 20);
    }

    public Team getTeam(Player p) {
        if (team1.getMembers().contains(p)) return team1;
        return team2;
    }

    public void setWinner(Team team) {
        this.winner = team;
    }

    public Team getWinner() {
        return winner;
    }


    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Arena getA() {
        return a;
    }

    public void setA(Arena a) {
        this.a = a;
    }

    public Kit getK() {
        return k;
    }

    public void setK(Kit k) {
        this.k = k;
    }

    public EntityHider getHider() {
        return hider;
    }

    public void setHider(EntityHider hider) {
        this.hider = hider;
    }

    public DuelState getState() {
        return state;
    }

    public void setState(DuelState state) {
        this.state = state;
    }

    public DuelType getType() {
        return type;
    }

    public void setType(DuelType type) {
        this.type = type;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }


}

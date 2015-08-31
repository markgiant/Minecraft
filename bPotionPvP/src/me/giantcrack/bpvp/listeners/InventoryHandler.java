package me.giantcrack.bpvp.listeners;

import me.giantcrack.bpvp.DuelCmd;
import me.giantcrack.bpvp.Main;
import me.giantcrack.bpvp.PvPCmd;
import me.giantcrack.bpvp.duels.Duel;
import me.giantcrack.bpvp.duels.DuelManager;
import me.giantcrack.bpvp.duels.DuelRequest;
import me.giantcrack.bpvp.duels.DuelType;
import me.giantcrack.bpvp.elo.Elo;
import me.giantcrack.bpvp.elo.EloManager;
import me.giantcrack.bpvp.elo.Range;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.kits.KitManager;
import me.giantcrack.bpvp.que.QueManager;
import me.giantcrack.bpvp.que.QueType;
import me.giantcrack.bpvp.teams.Team;
import me.giantcrack.bpvp.teams.TeamManager;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by shoot_000 on 6/27/2015.
 */
public class InventoryHandler implements Listener {


    public static void openUnrankedMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.YELLOW + "Unranked");
        for (Kit k : KitManager.getInstance().kits) {
            int duels = 0;
            for (Duel d : DuelManager.getInstance().duels) {
                if (d.getK().getName().equals(k.getName()) && d.getType() == DuelType.UNRANKED) {
                    duels++;
                }
            }
            ItemStack icon = k.getIcon();
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(Arrays.asList(ChatColor.DARK_RED + "Queued: " + ChatColor.WHITE + QueManager.getInstance().getQue(k, QueType.UNRANKED).getPlayers().size(),ChatColor.DARK_RED + "Matches: " + ChatColor.WHITE + duels));
            icon.setItemMeta(meta);
            inv.addItem(icon);
        }
        p.openInventory(inv);
    }

    public static void giveDefaultItems(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setHealth(20);
        p.setFoodLevel(20);
        {
            ItemStack edit = new ItemStack(Material.BOOK);
            ItemMeta emeta = edit.getItemMeta();
            emeta.setDisplayName(ChatColor.GREEN + "Edit Kits");
            emeta.setLore(Arrays.asList(ChatColor.GREEN + "Use this to edit your kits!"));
            edit.setItemMeta(emeta);
            p.getInventory().setItem(0, edit);
        }
        {
            ItemStack team = new ItemStack(Material.NAME_TAG);
            ItemMeta tmeta = team.getItemMeta();
            tmeta.setDisplayName(ChatColor.GREEN + "Team");
            tmeta.setLore(Arrays.asList(ChatColor.GREEN + "Right click to create a team"));
            team.setItemMeta(tmeta);
            p.getInventory().setItem(4, team);
        }
        {
            ItemStack unranked = new ItemStack(Material.WOOD_SWORD);
            ItemMeta umeta = unranked.getItemMeta();
            umeta.setDisplayName(ChatColor.GREEN + "Unranked");
            umeta.setLore(Arrays.asList(ChatColor.GREEN + "Find unranked matches"));
            unranked.setItemMeta(umeta);
            p.getInventory().setItem(7, unranked);
        }
        {
            ItemStack ranked = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta umeta = ranked.getItemMeta();
            umeta.setDisplayName(ChatColor.GREEN + "Ranked");
            umeta.setLore(Arrays.asList(ChatColor.GREEN + "Find ranked matches"));
            ranked.setItemMeta(umeta);
            p.getInventory().setItem(8, ranked);
        }
        p.updateInventory();

    }

    @EventHandler
    public void onRightClickUnranked(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.WOOD_SWORD) {
                    if (!e.getItem().getItemMeta().hasDisplayName()) return;
                    if (!e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "Unranked")) return;
                    e.setCancelled(true);
                    openUnrankedMenu(e.getPlayer());
                }
            }
        }
    }


    @EventHandler
    public void onRightClickRanked(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.DIAMOND_SWORD) {
                    if (!e.getItem().getItemMeta().hasDisplayName()) return;
                    if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "Ranked")) {
                        e.setCancelled(true);
                        openRankedMenu(e.getPlayer());
                    }
                }
            }
        }
    }


    @EventHandler
    public void onRightClickEdit(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.BOOK) {
                    try {
                        if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "Edit")) {
                            if (DuelManager.getInstance().getDuel(e.getPlayer()) != null) return;
                            e.setCancelled(true);
                            openEditKitsMenu(e.getPlayer());
                        }
                    } catch (NullPointerException ex) {

                    }
                }
            }
        }
    }

    public void openEditKitsMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.AQUA + "Edit Kits");
        for (Kit k : KitManager.getInstance().kits) {
            inv.addItem(k.getIcon());
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onKitEditClick(InventoryClickEvent e) {
        if (e.getInventory().getName().contains(ChatColor.AQUA + "Edit Kits")) {
            Player p = (Player) e.getWhoClicked();
            if (DuelManager.getInstance().getDuel(p) != null) return;
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                Kit k = KitManager.getInstance().getKit(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                if (k != null) {
                    try {
                        p.teleport(k.getEditLocation());
                        p.closeInventory();
                        p.sendMessage(ChatColor.RED + "Now editing " + ChatColor.GREEN + k.getName());
                        k.giveDefaultKit(p);
                    } catch (Exception ex) {
                        p.sendMessage(ChatColor.RED + "No location has been set!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onRightClickTeam(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.NAME_TAG) {
                    if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "Team")) {
                        e.setCancelled(true);
                        e.getPlayer().performCommand("team create");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTeamDisplayInv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (e.getInventory().getName().contains(ChatColor.RED + "Teams To Fight")) {
            e.setCancelled(true);
            Team t = TeamManager.getInstance().getTeam(Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            if (t != null) {
                p.closeInventory();
                p.performCommand("duel " + t.getOwner().getName());
            }
        }
    }

    @EventHandler
    public void onTeamInv(InventoryClickEvent e) {
        if (e.getInventory().getName().contains(ChatColor.BLUE + "Team Members")) {
            e.setCancelled(true);
        }
    }


    public void showFirst54Teams(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.RED + "Teams To Fight");
        for (Team t : TeamManager.getInstance().teams) {
            if (t.isJoinable()) {
                if (t.getMembers().contains(p)) continue;
                if (DuelManager.getInstance().getDuel(t.getOwner()) == null) {
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta meta = (SkullMeta) head.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + t.getOwner().getName());
                    List<String> mem = new ArrayList<>();
                    for (Player pl : t.getMembers()) {
                        mem.add(ChatColor.BLUE + pl.getName());
                    }
                    meta.setLore(mem);
                    head.setItemMeta(meta);
                    inv.addItem(head);
                } else {
                    ItemStack head = new ItemStack(Material.getMaterial(397));
                    SkullMeta meta = (SkullMeta) head.getItemMeta();
                    meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + t.getOwner().getName());
                    List<String> mem = new ArrayList<>();
                    for (Player pl : t.getMembers()) {
                        mem.add(ChatColor.RED + pl.getName());
                    }
                    meta.setLore(mem);
                    head.setItemMeta(meta);
                    inv.addItem(head);
                }
            }
        }
        p.openInventory(inv);
    }

    public void showMembers(Player p, Team t) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Team Members");
        for (Player pl : t.getMembers()) {
            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setDisplayName(t.getOwner().getName().equals(pl.getName()) ? ChatColor.GREEN + "" + ChatColor.BOLD + pl.getName() : ChatColor.AQUA + pl.getName());
            head.setItemMeta(meta);
            inv.addItem(head);
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onRightClickTeamFight(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.BLAZE_ROD) {
                    if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Team")) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Coming in the next update!");
                        return;
                    }
                }
            }
        }
    }


    @EventHandler
    public void onRightClickShowTeams(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.SLIME_BALL) {
                    if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Duel")) {
                        e.setCancelled(true);
                        showFirst54Teams(e.getPlayer());
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onRightClickLeave(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.BLAZE_POWDER) {
                    if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Leave")) {
                        e.setCancelled(true);
                        e.getPlayer().performCommand("team leave");
                    }
                }
            }
        }
    }


    @EventHandler
    public void onRightClickWho(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.SKULL_ITEM) {
                    if (e.getItem().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Who") && TeamManager.getInstance().getTeam(e.getPlayer()).isJoinable()) {
                        e.setCancelled(true);
                        showMembers(e.getPlayer(), TeamManager.getInstance().getTeam(e.getPlayer()));
                    }
                }
            }
        }
    }


    public static void giveTeamItems(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setHealth(20);
        p.setFoodLevel(20);
        {
            ItemStack who = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            ItemMeta emeta = who.getItemMeta();
            emeta.setDisplayName(ChatColor.GOLD + "Who is in your team");
            who.setItemMeta(emeta);
            p.getInventory().setItem(0, who);
        }
        {
            ItemStack team = new ItemStack(Material.BLAZE_POWDER);
            ItemMeta tmeta = team.getItemMeta();
            tmeta.setDisplayName(ChatColor.GOLD + "Leave this team");
            team.setItemMeta(tmeta);
            p.getInventory().setItem(3, team);
        }
        {
            ItemStack unranked = new ItemStack(Material.SLIME_BALL);
            ItemMeta umeta = unranked.getItemMeta();
            umeta.setDisplayName(ChatColor.GOLD + "Duel other teams");
            unranked.setItemMeta(umeta);
            p.getInventory().setItem(5, unranked);
        }
        {
            ItemStack ranked = new ItemStack(Material.BLAZE_ROD);
            ItemMeta umeta = ranked.getItemMeta();
            umeta.setDisplayName(ChatColor.GOLD + "Team Fight");
            ranked.setItemMeta(umeta);
            p.getInventory().setItem(8, ranked);
        }
        p.updateInventory();
    }

    public static void openKitOptions(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.LIGHT_PURPLE + "Kits");
        for (Kit k : KitManager.getInstance().kits) {
            inv.addItem(k.getIcon());
        }
        p.openInventory(inv);
    }

    public static void openRankedMenu(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, ChatColor.GREEN + "Ranked");
        for (Kit k : KitManager.getInstance().kits) {
            int duels = 0;
            for (Duel d : DuelManager.getInstance().duels) {
                if (d.getK().getName().equals(k.getName())) {
                    duels++;
                }
            }
            ItemStack icon = k.getIcon();
            ItemMeta meta = icon.getItemMeta();
            meta.setLore(Arrays.asList(ChatColor.DARK_RED + "Queued: " + ChatColor.WHITE + QueManager.getInstance().getQue(k, QueType.RANKED).getPlayers().size(),ChatColor.DARK_RED + "Matches: " + ChatColor.WHITE + duels));
            icon.setItemMeta(meta);
            inv.addItem(icon);
        }
        p.openInventory(inv);
    }


    @EventHandler
    public void onDuelInvClick(InventoryClickEvent e) {
        if (e.getInventory().getName().contains(ChatColor.RED + ">")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onUnrankedClick(InventoryClickEvent e) {
        if (e.getInventory().getName().contains(ChatColor.YELLOW + "Unranked")) {
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                Kit k = KitManager.getInstance().getKit(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                if (search.containsKey(p)) {
                    search.remove(p);
                }
                if (task.containsKey(p)) {
                    task.get(p).cancel();
                    task.remove(p);
                }
                if (k != null) {
                    if (QueManager.getInstance().getQue(p) != null && QueManager.getInstance().getQue(p).getKit().getName().equals(k.getName())) {
                        QueManager.getInstance().getQue(p).removePlayer(p);
                        return;
                    }
                    if (QueManager.getInstance().getQue(p) != null) {
                        QueManager.getInstance().getQue(p).removePlayer(p);
                        QueManager.getInstance().getQue(k, QueType.UNRANKED).addPlayer(p);
                        p.closeInventory();
                    } else {
                        QueManager.getInstance().getQue(k, QueType.UNRANKED).addPlayer(p);
                        p.closeInventory();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onOptionClick(InventoryClickEvent e) {
        if (e.getInventory().getName().contains(ChatColor.LIGHT_PURPLE + "Kits")) {
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                Kit k = KitManager.getInstance().getKit(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                if (k != null) {
                    DuelManager.getInstance().clearRequests(DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)));
                    DuelRequest request = new DuelRequest(TeamManager.getInstance().getTeam(p), DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)), k);
                    DuelManager.getInstance().requests.add(request);
                    if (TeamManager.getInstance().getTeam(p).isJoinable()) {
                        TeamManager.getInstance().getTeam(p).sendMessage(ChatColor.GREEN + "You have requested a duel against " + DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).getOwner().getName() + "'s Team!");
                        DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).sendMessage(ChatColor.GREEN + p.getName() + "'s Team has requested to duel you with kit " + ChatColor.LIGHT_PURPLE + k.getName() + ChatColor.GREEN + "!");
                        DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).sendMessage(ChatColor.GREEN + "Accept the request with " + ChatColor.AQUA + "/accept " + p.getName() + ChatColor.GREEN + "!");
                        new FancyMessage("Or Click This Message To Accept!").color(ChatColor.YELLOW).command("/accept " + p.getName()).send(DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).getMembers());
                        DuelCmd.tracker.remove(TeamManager.getInstance().getTeam(p));
                        DuelCmd.ktracker.put(TeamManager.getInstance().getTeam(p), k);
                        p.closeInventory();
                        return;
                    } else {
                        TeamManager.getInstance().getTeam(p).sendMessage(ChatColor.GREEN + "You have requested a duel against " + DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).getOwner().getName() + "!");
                        DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).sendMessage(ChatColor.GREEN + p.getName() + " has requested to duel you with kit " + ChatColor.LIGHT_PURPLE + k.getName() + ChatColor.GREEN + "!");
                        DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).sendMessage(ChatColor.GREEN + "Accept the request with " + ChatColor.AQUA + "/accept " + p.getName() + ChatColor.GREEN + "!");
                        new FancyMessage("Or Click This Message To Accept!").color(ChatColor.YELLOW).command("/accept " + p.getName()).send(DuelCmd.tracker.get(TeamManager.getInstance().getTeam(p)).getMembers());
                        DuelCmd.tracker.remove(TeamManager.getInstance().getTeam(p));
                        DuelCmd.ktracker.put(TeamManager.getInstance().getTeam(p), k);
                        p.closeInventory();
                        return;
                    }
                }
            }
        }
    }

    //What the fuck
    public static Elo getHighestElo(Kit k) {
        int high = 1200;
        Elo elo = null;
        for (Elo e : EloManager.getInstance().elos) {
            if (e.getK().getName().equals(k.getName())) {
                if (e.getElo() > high) {
                    high = e.getElo();
                    elo = e;
                }
            }
        }
        return elo;
    }

    public Player getPlayerWithHighElo(Elo e) {
        Player high = Bukkit.getPlayer(e.getUuid());
        return high;
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (DuelManager.getInstance().getDuel(e.getPlayer()) == null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onStatsInv(InventoryClickEvent e) {
        if (e.getInventory().getName().contains(ChatColor.RED + "Ranked Stats")) {
            e.setCancelled(true);
        }
    }

    public static void showStats(Player p) {
        Inventory inv = Bukkit.createInventory(p,9,ChatColor.RED + "Ranked Stats");
        for (Kit k : KitManager.getInstance().kits) {
            if (EloManager.getInstance().getElo(p,k) == null) continue;
            ItemStack book = new ItemStack(Material.BOOK);
            ItemMeta meta = book.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + k.getName());
            if (getHighestElo(k) == null) {
                meta.setLore(Arrays.asList(ChatColor.GOLD + "Ranked Stats", ChatColor.GREEN + "Wins: " + ChatColor.BLUE + EloManager.getInstance().getElo(p,k).getWins(), ChatColor.GREEN + "Loses: " + ChatColor.BLUE + EloManager.getInstance().getElo(p,k).getLoses(), ChatColor.GREEN + "ELO: " + ChatColor.BLUE + EloManager.getInstance().getElo(p,k).getElo()));
            } else {
                meta.setLore(Arrays.asList(ChatColor.GOLD + "Ranked Stats", ChatColor.GREEN + "Wins: " + ChatColor.BLUE + EloManager.getInstance().getElo(p,k).getWins(), ChatColor.GREEN + "Loses: " + ChatColor.BLUE + EloManager.getInstance().getElo(p,k).getLoses(), ChatColor.GREEN + "ELO: " + ChatColor.BLUE + EloManager.getInstance().getElo(p,k).getElo(), ChatColor.RED + "Top ELO: " + ChatColor.YELLOW + getHighestElo(k).getUserName() + " " + getHighestElo(k).getElo()));
            }
            book.setItemMeta(meta);
            inv.addItem(book);
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getName().contains(ChatColor.LIGHT_PURPLE + "Kits")) {
            Player p = (Player) e.getPlayer();
            if (DuelCmd.tracker.containsKey(TeamManager.getInstance().getTeam(p))) {
                DuelCmd.tracker.remove(TeamManager.getInstance().getTeam(p));
            }
        }
    }

    public static Map<Player, Integer> search = new HashMap<>();

    public static Map<Player, BukkitRunnable> task = new HashMap<>();

    @EventHandler
    public void onRankedClick(InventoryClickEvent e) {
        if (e.getInventory().getName().contains(ChatColor.GREEN + "Ranked")) {
            final Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                final Kit k = KitManager.getInstance().getKit(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                if (k != null) {
                    if (EloManager.getInstance().getElo(p, k) == null) {
                        EloManager.getInstance().createElo(p, k);
                        EloManager.getInstance().getElo(p,k).setUserName(p.getName());
                    }
                    if (QueManager.getInstance().getQue(p) != null && QueManager.getInstance().getQue(p).getKit().getName().equals(k.getName())) {
                        QueManager.getInstance().getQue(p).removePlayer(p);
                        return;
                    }
                    if (!EloManager.getInstance().getElo(p,k).getUserName().equals(p.getName()) && EloManager.getInstance().getElo(p,k) !=null)  {
                        EloManager.getInstance().getElo(p,k).setUserName(p.getName());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                EloManager.getInstance().getElo(p,k).save();
                            }
                        }.runTaskAsynchronously(Main.getInstance());
                    }
                    if (task.containsKey(p)) {
                        task.get(p).cancel();
                        task.remove(p);
                        search.remove(p);
                        search.put(p, 0);
                        Range.getRange(p).setSearch(100);
                        task.put(p, new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (search.get(p) == 6) {
                                    search.remove(p);
                                    task.remove(p);
                                    cancel();
                                    Range.getRange(p).setSearch(100);
                                    p.sendMessage(ChatColor.RED + "No matches were found please try again later!");
                                    if (QueManager.getInstance().getQue(p) != null) {
                                        QueManager.getInstance().getQue(p).removePlayer(p);
                                    }
                                    return;
                                }
                                search.put(p, search.get(p) + 1);
                                Range.getRange(p).setSearch(Range.getRange(p).getSearch() + 50);
                                p.sendMessage(ChatColor.YELLOW + "Searching for ranked match!");
                                p.sendMessage(ChatColor.DARK_GREEN + "" + (Range.getRange(p).getElo() - Range.getRange(p).getSearch()) + ChatColor.AQUA + " -> " + ChatColor.DARK_GREEN + (Range.getRange(p).getElo() + Range.getRange(p).getSearch()));
                                return;
                            }
                        });
                        task.get(p).runTaskTimerAsynchronously(Main.getInstance(), 20 * 3, 20 * 3);
                        QueManager.getInstance().getQue(k, QueType.RANKED).addPlayer(p);
                        p.closeInventory();
                    } else {
                        QueManager.getInstance().getQue(k, QueType.RANKED).addPlayer(p);
                        search.put(p, 0);
                        Range.getRange(p).setSearch(100);
                        task.put(p,
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (search.get(p) == 6) {
                                            search.remove(p);
                                            task.remove(p);
                                            cancel();
                                            Range.getRange(p).setSearch(100);
                                            p.sendMessage(ChatColor.RED + "No matches were found please try again later!");
                                            if (QueManager.getInstance().getQue(p) != null) {
                                                QueManager.getInstance().getQue(p).removePlayer(p);
                                            }
                                            return;
                                        }
                                        search.put(p, search.get(p) + 1);
                                        Range.getRange(p).setSearch(Range.getRange(p).getSearch() + 50);
                                        p.sendMessage(ChatColor.YELLOW + "Searching for ranked match!");
                                        p.sendMessage(ChatColor.DARK_GREEN + "" + (Range.getRange(p).getElo() - Range.getRange(p).getSearch()) + ChatColor.AQUA + " -> " + ChatColor.DARK_GREEN + (Range.getRange(p).getElo() + Range.getRange(p).getSearch()));
                                        return;
                                    }
                                });
                        task.get(p).runTaskTimerAsynchronously(Main.getInstance(), 20 * 3, 20 * 3);
                        p.closeInventory();
                    }
                }
//                    if (QueManager.getInstance().getQue(p) != null) {
//                        QueManager.getInstance().getQue(p).removePlayer(p);
//                        if (task.containsKey(p)) {
//                            task.get(p).cancel();
//                            task.remove(p);
//                            search.remove(p);
//                            search.put(p, 0);
//                            task.put(p,
//                                    new BukkitRunnable() {
//                                        @Override
//                                        public void run() {
//                                            if (search.get(p) == 6) {
//                                                search.remove(p);
//                                                task.remove(p);
//                                                cancel();
//                                                p.sendMessage(ChatColor.RED + "No matches were found please try again later!");
//                                                if (QueManager.getInstance().getQue(p) != null) {
//                                                    QueManager.getInstance().getQue(p).removePlayer(p);
//                                                }
//                                                return;
//                                            }
//                                            search.put(p, search.get(p) + 1);
//                                            Range.getRange(p).setSearch(Range.getRange(p).getSearch() + 50);
//                                            p.sendMessage(ChatColor.YELLOW + "Searching for ranked match!");
//                                            p.sendMessage(ChatColor.DARK_GREEN + "" + (Range.getRange(p).getElo()-Range.getRange(p).getSearch()) + ChatColor.AQUA + " -> " + ChatColor.DARK_GREEN + (Range.getRange(p).getElo()+Range.getRange(p).getSearch()));
//                                            return;
//                                        }
//                                    });
//                            task.get(p).runTaskTimer(Main.getInstance(), 20*3, 20*3);
//                            QueManager.getInstance().getQue(k, QueType.RANKED).addPlayer(p);
//                            p.closeInventory();
//                        }
//                    } else {
//                        QueManager.getInstance().getQue(k, QueType.RANKED).addPlayer(p);
//                        search.put(p, 0);
//                        task.put(p,
//                                new BukkitRunnable() {
//                                    @Override
//                                    public void run() {
//                                        if (search.get(p) == 6) {
//                                            search.remove(p);
//                                            task.remove(p);
//                                            cancel();
//                                            p.sendMessage(ChatColor.RED + "No matches were found please try again later!");
//                                            if (QueManager.getInstance().getQue(p) != null) {
//                                                QueManager.getInstance().getQue(p).removePlayer(p);
//                                            }
//                                            return;
//                                        }
//                                        search.put(p, search.get(p) + 1);
//                                        Range.getRange(p).setSearch(Range.getRange(p).getSearch() + 50);
//                                        p.sendMessage(ChatColor.YELLOW + "Searching for ranked match!");
//                                        p.sendMessage(ChatColor.DARK_GREEN + "" + (Range.getRange(p).getElo()-Range.getRange(p).getSearch()) + ChatColor.AQUA + " -> " + ChatColor.DARK_GREEN + (Range.getRange(p).getElo()+Range.getRange(p).getSearch()));
//                                        return;
//                                    }
//                                });
//                        task.get(p).runTaskTimer(Main.getInstance(), 20*3, 20*3);
//                        p.closeInventory();
//                    }
//                }
//            }
            }
        }

    }
}

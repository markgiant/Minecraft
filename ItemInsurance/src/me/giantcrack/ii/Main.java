package me.giantcrack.ii;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by shoot_000 on 6/19/2015.
 */
public class Main extends JavaPlugin implements Listener {

    private static Main i;

    private Economy econ = null;

    public static Main getInstance() {
        return i;
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getLogger().log(Level.INFO,"------------------------------------------------------------");
            Bukkit.getLogger().log(Level.INFO,"ItemInsurance Has Been Disabled! No Economy Found! (Vault)");
            Bukkit.getLogger().log(Level.INFO,"-----------------------------------------------------------");
            return;
        }
        i = this;
        if (getConfig().getConfigurationSection("InsurancePolicy") == null) {
            getConfig().createSection("InsurancePolicy");
        }
        Bukkit.getPluginManager().registerEvents(this,this);
        InsuranceManager.getInstance().setUP();
        getConfig().addDefault("InsurancePricePerItem",500);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public int getCost() {
        return getConfig().getInt("InsurancePricePerItem");
    }

    @Override
    public void onDisable() {
        i = null;
    }

    /***
     * the command is /insure. You type this when you hold an item. It used essentials eco to take money out of a players balance. When you insure an item it's name changes (not the lore). It will be like "gold:insured diamond sword". I can set how much money it will take to insure an item in the config. When a player dies with this item, he/she will receive the item when after they die. They killer will receive the price of the insurance that the victim payed for the insurance. If the player had multiple items with insurance, the killer will receive more money based on how many items there were total. The killer receives a message that tells them why they received money and how much they received (I don't need this in the config but you can put it there if u want). The person that had items insured will receive a message when they die that they kept their insured items (it doesn't have to list the items that they kept). Also insurance is lost after death so the victim needs to reinsure
     [2:54:33 PM] Spotsindude: And I don't need permissions
     [3:07:10 PM] Spotsindude: Also, if the item was not in the player inventory ondeath then the killer cant receive extra money
     * @return
     */


    Map<Player,List<ItemStack>> respawn = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
            Player killed = (Player) e.getEntity();
            Player killer = (Player) e.getEntity().getKiller();
            if (InsuranceManager.getInstance().getPolicy(killed) == null) return;
            List<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < InsuranceManager.getInstance().getPolicy(killed).getItems().size(); i++) {
                for (int j = 0; j < e.getDrops().size(); j++) {
                    if (InsuranceManager.getInstance().getPolicy(killed).getItems().get(i).equals(e.getDrops().get(j))) {
                        items.add(e.getDrops().get(j));
                        e.getDrops().remove(j);
                    }
                }
            }
            respawn.put(killed,items);
            econ.bankDeposit(killer.getName(),respawn.get(killed).size() * getCost());
            killer.sendMessage(ChatColor.GREEN + "The player " + killed.getName() + " had " + items.size() + " items insured! You received " + respawn.get(killed).size() * getCost() + " dollars!");
        } else {
            Player killed = (Player) e.getEntity();
            if (InsuranceManager.getInstance().getPolicy(killed) == null) return;
            List<ItemStack> items = new ArrayList<>();
            for (int i = 0; i < InsuranceManager.getInstance().getPolicy(killed).getItems().size(); i++) {
                for (int j = 0; j < e.getDrops().size(); j++) {
                    if (InsuranceManager.getInstance().getPolicy(killed).getItems().get(i).equals(e.getDrops().get(j))) {
                        items.add(e.getDrops().get(j));
                        e.getDrops().remove(j);
                    }
                }
            }
            respawn.put(killed,items);
        }
    }

    public String format(String s)
    {
        if (!s.contains("_"))
        {
            return capitalize(s);
        }
        String[] j = s.split("_");

        String c = "";

        for (String f : j)
        {
            f = capitalize(f);
            c += c.equalsIgnoreCase("") ? f : " " + f;
        }
        return c;
    }

    public String capitalize(String text)
    {
        String firstLetter = text.substring(0, 1).toUpperCase();
        String next = text.substring(1).toLowerCase();
        String capitalized = firstLetter + next;

        return capitalized;
    }

    public String getFriendlyName(ItemStack item)
    {
        Material m = item.getType();
        String name = format(m.name());
        return name;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (respawn.get(e.getPlayer()) != null) {
           for (int i = 0; i < respawn.get(e.getPlayer()).size(); i++) {
               ItemMeta meta = respawn.get(e.getPlayer()).get(i).getItemMeta();
               meta.setDisplayName(getFriendlyName(respawn.get(e.getPlayer()).get(i)));
               respawn.get(e.getPlayer()).get(i).setItemMeta(meta);
               e.getPlayer().getInventory().addItem(respawn.get(e.getPlayer()).get(i));
               InsuranceManager.getInstance().getPolicy(e.getPlayer()).restore(e.getPlayer(),i);
           }
           respawn.remove(e.getPlayer());
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Players only!");
            return true;
        }
        Player p = (Player)sender;
        if (command.getName().equals("insure")) {
            if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
                p.sendMessage(ChatColor.RED + "You cannot insure air!");
                return true;
            }
            if (econ.getBalance(p.getName()) < getCost() * p.getItemInHand().getAmount()) {
                p.sendMessage(ChatColor.RED + "You need at least $" + getCost() * p.getItemInHand().getAmount() + " to insure an item!");
                return true;
            }
            if (p.getItemInHand().getItemMeta().hasDisplayName() && p.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Insured")) {
                p.sendMessage(ChatColor.RED + "That item is already insured!");
                return true;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"eco take " + p.getName() + " " + getCost()*p.getItemInHand().getAmount());
            InsuranceManager.getInstance().createPolicy(p);
            for (int i = 0; i < p.getItemInHand().getAmount(); i++) {
                InsuranceManager.getInstance().getPolicy(p).add(p.getItemInHand());
            }
            InsuranceManager.getInstance().getPolicy(p).save();
            p.sendMessage(ChatColor.GREEN + "Item Insured!");
            p.sendMessage(ChatColor.GREEN + "Your new balance is now $" + ChatColor.AQUA  + econ.getBalance(p.getName()));
            return true;
        }
        return false;
    }
}

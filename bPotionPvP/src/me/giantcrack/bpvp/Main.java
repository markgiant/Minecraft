package me.giantcrack.bpvp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import me.giantcrack.bpvp.arenas.Arena;
import me.giantcrack.bpvp.arenas.ArenaManager;
import me.giantcrack.bpvp.duels.*;
import me.giantcrack.bpvp.elo.EloManager;
import me.giantcrack.bpvp.elo.EloSignUpdateTask;
import me.giantcrack.bpvp.elo.SignManager;
import me.giantcrack.bpvp.files.*;
import me.giantcrack.bpvp.files.Archer;
import me.giantcrack.bpvp.files.Debuff;
import me.giantcrack.bpvp.files.Gapple;
import me.giantcrack.bpvp.files.MCSG;
import me.giantcrack.bpvp.files.NoDebuff;
import me.giantcrack.bpvp.files.Soup;
import me.giantcrack.bpvp.kits.*;
import me.giantcrack.bpvp.listeners.SignListener;
import me.giantcrack.bpvp.que.QueManager;
import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class Main extends JavaPlugin {

    private static Main i;

    //Dont hide non duelers from seeing everyone

    private static class PriorityEntity implements Comparable<PriorityEntity> {
        public final double priority;
        public final Entity entity;

        public PriorityEntity(double priority, Entity entity) {
            this.priority = priority;
            this.entity = entity;
        }

        @Override
        public int compareTo(PriorityEntity o) {
            return o != null ? Double.compare(priority, o.priority) : -1;
        }
    }

    private Iterable<Entity> findClosestEntities(World world, float x, float y, float z, float radius) {
        // For quick lookup of the distance
        List<PriorityEntity> result = Lists.newArrayList();
        double radiusSquared = radius * radius;

        for (Entity entity : world.getEntities()) {
            Location loc = entity.getLocation();
            double dX = loc.getX() - x;
            double dY = loc.getY() - y;
            double dZ = loc.getZ() - z;
            double distanceSquared = dX * dX + dY * dY + dZ * dZ;

            // Limit to nearby entities
            if (distanceSquared <= radiusSquared) {
                result.add(new PriorityEntity(distanceSquared, entity));
            }
        }
        Collections.sort(result);

        // Unwrap list of priority entities
        return Iterables.transform(result, new Function<PriorityEntity, Entity>() {
            public Entity apply(PriorityEntity wrapped) {
                return wrapped.entity;
            }

            ;
        });
    }

    private Multimap<String, Integer> hiddenEntities = HashMultimap.create();


    public Multimap<String, Integer> getHiddenEntities() {
        return hiddenEntities;
    }


    @Override
    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(this, WrapperPlayServerWorldEvent.TYPE) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        WrapperPlayServerWorldEvent packet = new WrapperPlayServerWorldEvent(event.getPacket());
                        Player player = event.getPlayer();

                        // See if there are any nearby entities that should cancel this event
                        for (Entity entity : findClosestEntities(player.getWorld(), packet.getX() + 0.5f, packet.getY() + 0.5f, packet.getZ() + 0.5f, 2)) {
                            if (hiddenEntities.containsEntry(event.getPlayer().getName(), entity.getEntityId())) {
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                });
        i = this;
        getCommand("team").setExecutor(new TeamCmd());
        getCommand("arena").setExecutor(new ArenaCmd());
        getCommand("setedit").setExecutor(new SetEditLocationCmd());
        getCommand("duel").setExecutor(new DuelCmd());
        getCommand("spawn").setExecutor(new SpawnCmd());
        getCommand("setspawn").setExecutor(new SpawnCmd());
        getCommand("accept").setExecutor(new DuelCmd());
        getCommand("spectate").setExecutor(new SpectateCmd());
        getCommand("pvp").setExecutor(new PvPCmd());
        getCommand("stats").setExecutor(new StatsCmd());
        getCommand("leave").setExecutor(new LeaveCmd());
        getCommand("di").setExecutor(new DICmd());
        ArenaData.getInstance().setup(this);
        PlayerData.getInstance().setup(this);
        NoDebuff.getInstance().setup(this);
        Debuff.getInstance().setup(this);
        Archer.getInstance().setup(this);
        Gapple.getInstance().setup(this);
        Soup.getInstance().setup(this);
        MCSG.getInstance().setup(this);
        Signs.getInstance().setup(this);
        ArenaManager.getInstance().setUP();
        KitManager.getInstance().loadKits();
        KitManager.getInstance().loadKitsFromFile();
        EloManager.getInstance().loadElos();
        QueManager.getInstance().loadUnrankedQues();
        QueManager.getInstance().loadRankedQues();
        Bukkit.getPluginManager().registerEvents(new InventoryHandler(), this);
        Bukkit.getPluginManager().registerEvents(new DuelEvents(), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getPluginManager().registerEvents(new PvPCmd(), this);
        SignManager.get().setUp();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(i, new EloSignUpdateTask(), 20 * 10, 20 * 10);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TeamDisbandTask(), 20*5,20*5);
    }

    @Override
    public void onDisable() {
        try {
            Iterator<Duel> i = DuelManager.getInstance().duels.iterator();
            while (i.hasNext()) {
                Duel d = i.next();
                i.remove();
            }
        } catch (NullPointerException ex) {

        }
        for (Arena a : ArenaManager.getInstance().arenas) {
            a.cleanupPlayers();
        }
        i = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("set") && sender.isOp()) {
            if (sender instanceof Player) {
                if (args.length == 1) {
                    LocationUtility.saveLocation(args[0], ((Player) sender).getTargetBlock(null, 6).getLocation());
                    ((Player) sender).sendMessage("Set location at section " + args[0] + "!");
                    return true;
                }
            }
        }
        return false;
    }


    public static Main getInstance() {
        return i;
    }
}

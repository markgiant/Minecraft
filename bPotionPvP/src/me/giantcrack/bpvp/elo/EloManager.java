package me.giantcrack.bpvp.elo;

import me.giantcrack.bpvp.Main;
import me.giantcrack.bpvp.files.PlayerData;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.kits.KitManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.Configuration;
import java.util.*;

/**
 * Created by shoot_000 on 6/24/2015.
 */
public class EloManager {

    private static EloManager instance = new EloManager();

    private EloManager() {}

    public static EloManager getInstance() {
        return instance;
    }

    public List<Elo> elos = new ArrayList<>();

    public void handleRankedMatch(final Elo winner, final Elo loser) {
         int winnerElo = winner.getElo();
         int loserElo = loser.getElo();
                Random rand = new Random();
                if (winnerElo-loserElo >= 100) {
                    int amount = (rand.nextInt(11)+10);
                    winner.setElo(winner.getElo() + amount);
                    loser.setElo(loser.getElo()-amount);
                    winner.setWins(winner.getWins()+1);
                    loser.setLoses(loser.getLoses()+1);
                } else if (winnerElo - loserElo <= -50) {
                    int amount = (rand.nextInt(14) + 12);
                    winner.setElo(winner.getElo() + amount);
                    loser.setElo(loser.getElo() - amount);
                    winner.setWins(winner.getWins()+1);
                    loser.setLoses(loser.getLoses()+1);
                } else if (Math.abs(winnerElo - loserElo) <= 10) {
                    int amount = (rand.nextInt(3) + 1);
                    winner.setElo(winner.getElo() + amount);
                    loser.setElo(loser.getElo() - amount);
                    winner.setWins(winner.getWins()+1);
                    loser.setLoses(loser.getLoses()+1);
                } else {
                    int amount = (rand.nextInt(6)+15);
                    winner.setElo(winner.getElo() + amount);
                    loser.setElo(loser.getElo()-amount);
                    winner.setWins(winner.getWins()+1);
                    loser.setLoses(loser.getLoses()+1);
                }
        new BukkitRunnable() {
            @Override
            public void run() {
                winner.save();
                loser.save();
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public Elo getElo(Player p,Kit k) {
        for (Elo elo : elos) {
            if (elo.getUuid().equals(p.getUniqueId()) && elo.getK().getName().equals(k.getName())) {
                return elo;
            }
        }
        return null;
    }

    public void createElo(Player p, Kit k) {
        if (getElo(p,k) != null) return;
        Elo elo = new Elo(p.getUniqueId(),k);
        elo.save();
        elos.add(elo);
    }

    public void loadElos() {
        if (PlayerData.getInstance().<ConfigurationSection>get("Elo") == null) {
            PlayerData.getInstance().createConfigurationSection("Elo");
        }
        for (String uuid : PlayerData.getInstance().<ConfigurationSection>get("Elo").getKeys(false)) {
            for (String kit : PlayerData.getInstance().<ConfigurationSection>get("Elo." + uuid).getKeys(false)) {
                UUID uid = UUID.fromString(uuid);
                Kit k = KitManager.getInstance().getKit(kit);
                int elo = PlayerData.getInstance().getInt("Elo." + uuid + "." + k.getName() + ".elo");
                int wins = PlayerData.getInstance().getInt("Elo." + uuid + "." + k.getName() + ".wins");
                int loses = PlayerData.getInstance().getInt("Elo." + uuid + "." + k.getName()+ ".loses");
                String name = PlayerData.getInstance().getString("Elo." + uuid+ "." + k.getName() + ".name");
                Elo e = new Elo(uid, name, k, elo, wins, loses);
                elos.add(e);
            }
        }
    }


}

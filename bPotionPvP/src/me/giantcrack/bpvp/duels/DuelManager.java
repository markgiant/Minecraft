package me.giantcrack.bpvp.duels;

import me.giantcrack.bpvp.arenas.Arena;
import me.giantcrack.bpvp.arenas.ArenaManager;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.teams.Team;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by shoot_000 on 6/21/2015.
 */
public class DuelManager {

    private static DuelManager instance = new DuelManager();

    private DuelManager() {}

    public static DuelManager getInstance() {
        return instance;
    }

    public List<Duel> duels = new ArrayList<>();
    public List<DuelRequest> requests = new ArrayList<>();

    //TODO: Handle duel requests

    public Duel getDuel(Player p) {
        for (Duel d : duels) {
            if (d.getTeam1().getMembers().contains(p) || d.getTeam2().getMembers().contains(p)) {
                return d;
            }
        }
        return null;
    }

    public DuelRequest getRequest(Team team1, Team team2, Kit k) {
        for (DuelRequest dr : requests) {
            if (team1.getOwner() == null || team2.getOwner() == null) return null;
            if (dr.t1.getOwner().getName().equals(team1.getOwner().getName()) && dr.t2.getOwner().getName().equals(team2.getOwner().getName()) && dr.k.getName().equals(k.getName())) {
                return dr;
            }
        }
        return null;
    }


    public void clearRequests(Team t) {
        try{
            Iterator<DuelRequest> i = requests.iterator();
            while(i.hasNext()) {
                DuelRequest r = i.next();
                if (r.t1.getOwner().getName().equals(t.getOwner().getName()) || r.t2.getOwner().getName().equals(t.getOwner().getName())) {
                    i.remove();
                }
            }
        }catch (NullPointerException ex) {

        }
    }

    public void createDuel(Team team1, Team team2,Kit k, DuelType type) {
        Duel d = new Duel(team1, team2, ArenaManager.getInstance().getRandomArena(),k,type);
        duels.add(d);
        d.countdown();
        return;
    }


}

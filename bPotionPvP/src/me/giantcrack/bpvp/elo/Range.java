package me.giantcrack.bpvp.elo;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoot_000 on 6/27/2015.
 */
public class Range {

    private Player player;
    private int elo;
    private int search;


    public static List<Range> ranges = new ArrayList<>();


    public static Range getRange(Player p) {
        for (Range r : ranges) {
            if (r.getPlayer().getName().equals(p.getName())) {
                return r;
            }
        }
        return null;
    }

    public boolean inRange(Range other) {
        return (Math.abs(elo - other.getElo()) <= this.search);
    }

    public Range(Player player, int elo) {
        this.player = player;
        this.elo = elo;
        this.search = 100;
        ranges.add(this);
    }

    public int getSearch() {
        return search;
    }

    public void setSearch(int search) {
        this.search = search;
    }

    public void destroyRange() {
        ranges.remove(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}

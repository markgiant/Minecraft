package me.giantcrack.bpvp.elo;

import me.giantcrack.bpvp.files.PlayerData;
import me.giantcrack.bpvp.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by shoot_000 on 6/24/2015.
 */
public class Elo {

    private UUID uuid;

    private Kit k;

    private int elo,wins,loses;

    private String userName;


    public void save() {
        PlayerData.getInstance().createConfigurationSection("Elo." + uuid.toString() + "." + k.getName());
        PlayerData.getInstance().set("Elo." + uuid.toString() + "." + k.getName() + ".elo",elo);
        PlayerData.getInstance().set("Elo." + uuid.toString() + "." + k.getName() + ".wins",wins);
        PlayerData.getInstance().set("Elo." + uuid.toString() + "." + k.getName() + ".loses",loses);
        PlayerData.getInstance().set("Elo." + uuid.toString() + "." + k.getName() + ".name",userName);
        PlayerData.getInstance().save();
    }


    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public Elo(UUID uuid, Kit k) {
        this.uuid = uuid;
        this.k = k;
        this.elo = 1200;
        this.userName = Bukkit.getPlayer(uuid).getName();
    }


    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public Elo(UUID uuid, String name,Kit k, int elo, int wins,int loses) {
        this.uuid = uuid;
        this.k = k;
        this.elo = elo;
        this.wins = wins;
        this.loses = loses;
        this.userName = name;

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Kit getK() {
        return k;
    }

    public void setK(Kit k) {
        this.k = k;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}

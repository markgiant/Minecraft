package me.giantcrack.bpvp.elo;

import me.giantcrack.bpvp.files.Signs;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Sign;

/**
 * Created by markvolkov on 8/26/15.
 */
public class EloSign {

    private int id;
    private Kit k;
    private Location signLocation;
    private Sign sign;
    private String topElo;


    public EloSign(Kit k, Location signLocation) {
        this.id = SignManager.get().signs.size() + 1;
        this.k = k;
        this.signLocation = signLocation;
        this.sign = (Sign) this.signLocation.getBlock().getState();
        this.topElo = getTop().getUserName();
    }

    public Elo getTop() {
        Elo elo = null;
        int high = 1200;
        for (Elo e : EloManager.getInstance().elos) {
            if (e.getElo() > high && e.getK().getName().equals(k.getName())) {
                high = e.getElo();
                elo = e;
            }
        }
        return elo;
    }

    public EloSign update() {
        if (sign == null || k == null || signLocation == null) return this;
        if (!getTop().getUserName().equals(topElo)) {
            sign.getLocation().getWorld().playEffect(sign.getLocation(), Effect.MOBSPAWNER_FLAMES, 20, 20);
            this.topElo = getTop().getUserName();
            save();
        }
        this.sign.setLine(0, ChatColor.GREEN + "Top Elo");
        this.sign.setLine(1, ChatColor.RED + "Kit: " + k.getName());
        this.sign.setLine(2, ChatColor.GOLD + topElo != null ? topElo : "None");
        this.sign.setLine(3, getTop() != null ? ChatColor.AQUA + "ELO: " + getTop().getElo() : "None");
        this.sign.update();
        return this;
    }


    public int getId() {
        return id;
    }

    public void save() {
        String sid = String.valueOf(id);
        Signs.getInstance().createConfigurationSection("EloSigns." + sid);
        Signs.getInstance().set("EloSigns." + sid + ".kit", k.getName());
        LocationUtility.saveLocationSign("EloSigns." + sid + ".location", signLocation);
        Signs.getInstance().save();
    }

    public void delete() {
        String sid = String.valueOf(id);
        Signs.getInstance().set("EloSigns." + sid, null);
        Signs.getInstance().set("EloSigns." + sid + ".kit", null);
        Signs.getInstance().set("EloSigns." + sid + ".location", null);
        Signs.getInstance().save();
    }

    public Kit getK() {
        return k;
    }

    public void setK(Kit k) {
        this.k = k;
    }

    public Location getSignLocation() {
        return signLocation;
    }

    public void setSignLocation(Location signLocation) {
        this.signLocation = signLocation;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public String getTopElo() {
        return topElo;
    }

    public void setTopElo(String topElo) {
        this.topElo = topElo;
    }
}

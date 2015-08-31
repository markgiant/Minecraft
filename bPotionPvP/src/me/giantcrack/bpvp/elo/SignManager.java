package me.giantcrack.bpvp.elo;

import com.google.common.collect.ImmutableList;
import me.giantcrack.bpvp.files.Signs;
import me.giantcrack.bpvp.kits.Kit;
import me.giantcrack.bpvp.kits.KitManager;
import me.giantcrack.bpvp.utilities.LocationUtility;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markvolkov on 8/26/15.
 */
public class SignManager {

    private static final SignManager i = new SignManager();

    private SignManager() {}

    public static final SignManager get() {
        return i;
    }

    public List<EloSign> signs = new ArrayList<>();


    public List<EloSign> getSigns() {
        return ImmutableList.copyOf(signs);
    }

    public void setUp() {
        signs = new ArrayList<>();
        if (Signs.getInstance().<ConfigurationSection>get("EloSigns") == null) {
            Signs.getInstance().createConfigurationSection("EloSigns");
        }
        for (String sid : Signs.getInstance().<ConfigurationSection>get("EloSigns").getKeys(false)) {
            Kit k = KitManager.getInstance().getKit(Signs.getInstance().getString("EloSigns." + sid + ".kit"));
            Location loc = LocationUtility.getLocationSign("EloSigns." + sid + ".location");
            signs.add(new EloSign(k,loc));
            getSign(loc).update();
        }
        updateAll();
    }

    public void createSign(Kit k, Location loc) {
        if (getSign(loc) != null) return;
        signs.add(new EloSign(k,loc));
        getSign(loc).update().save();
    }

    public void removeSign(Location loc) {
        if (getSign(loc) == null) return;
        getSign(loc).delete();
        signs.remove(getSign(loc));
    }


    public EloSign getSign(Kit k) {
        for (EloSign s : signs) {
            if (s.getK().getName().equals(k.getName())) {
                return s;
            }
        }
        return null;
    }

    public EloSign getSign(Location l) {
        for (EloSign s : signs) {
            if (s.getSignLocation().equals(l)) {
                return s;
            }
        }
        return null;
    }

    public void updateAll() {
        for (EloSign es : signs) {
            es.update();
        }
    }




}

package me.giantcrack.mk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoot_000 on 6/14/2015.
 */
public class MattKit {

    private String name;
    private String permission;
    private String kitName;
    public static List<MattKit> kits = new ArrayList<>();

    public static MattKit getKit(String s) {
        for (MattKit mk : kits) {
            if (mk.getName().equals(s) || mk.getPermission().equals(s) || mk.getKitName().equals(s)) {
                return mk;
            }
        }
        return null;
    }

    public MattKit(String name, String permission, String kitName) {
        this.name = name;
        this.permission = permission;
        this.kitName = kitName;
        kits.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }
}

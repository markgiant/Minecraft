package me.giantcrack.mb;

import java.util.UUID;

public class TempbanData {
    private PlayerData userData;
    private String banner;
    private String nameWhenBanned;
    private String ipBanned;
    private int endTime;

    public TempbanData(PlayerData userData, String banner, String nameWhenBanned, int end) {
        this.userData = userData;
        this.banner = banner;
        this.nameWhenBanned = nameWhenBanned;
        this.ipBanned = userData.getCurrentIP();
        this.endTime = ((int) System.currentTimeMillis() / 1000 + end);
    }

    public TempbanData(PlayerData userData, String banner, String nameWhenBanned, int end, boolean notused) {
        this.userData = userData;
        this.banner = banner;
        this.nameWhenBanned = nameWhenBanned;
        this.ipBanned = userData.getCurrentIP();
        this.endTime = end;
    }

    public String getBannedIp() {
        return this.ipBanned;
    }

    public int getSecondsLeft() {
        return this.endTime - (int) System.currentTimeMillis() / 1000;
    }

    public void setIpBanned(String ip) {
        this.ipBanned = ip;
    }

    public boolean isExpired() {
        return (int) System.currentTimeMillis() / 1000 > this.endTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void save() {
        Bans.getInstance().createConfigurationSection("TempBans." + this.userData.getUuid());
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".banner", this.banner);
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".nwb", this.nameWhenBanned);
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".end", Integer.valueOf(this.endTime));
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".ipbanned", getBannedIp());
        Bans.getInstance().save();
    }

    public void delete() {
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".banner", null);
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".nwb", null);
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".end", null);
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString() + ".ipbanned", null);
        Bans.getInstance().set("TempBans." + this.userData.getUuid().toString(), null);
        Bans.getInstance().save();
    }

    public PlayerData getUserData() {
        return this.userData;
    }

    public void setUserData(PlayerData userData) {
        this.userData = userData;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getNameWhenBanned() {
        return this.nameWhenBanned;
    }

    public void setNameWhenBanned(String nameWhenBanned) {
        this.nameWhenBanned = nameWhenBanned;
    }
}


package com.developerdecuple.core;

import com.developerdecuple.Main;

import java.io.File;

public class Player {

    private final long id;
    private String name;
    private int level;
    private long exp, gold, cash;
    private final File databaseFile;

    private String backupName;
    private int backupLevel;
    private long backupEXP, backupGold, backupCash;

    public Player(long id, String name, int level, long exp, long gold, long cash) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.gold = gold;
        this.cash = cash;

        this.databaseFile = new File(Main.BASIC_PATH + "/Database/" + id);
    }

    private String getPlayerInfoForSVCFormat() {
        return id + "," + name + "," + level + "," + exp + "," + gold + "," + cash + ",0";
    }

    private String getPlayerInfoForSVCFormat(long id, String name, int level, long exp, long gold, long cash) {
        return id + "," + name + "," + level + "," + exp + "," + gold + "," + cash + ",0";
    }

    public long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public long getCash() {
        return cash;
    }

    public long getExp() {
        return exp;
    }

    public long getGold() {
        return gold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        backupName = this.name;
        this.name = name;
        applyPlayerInfoIntoPlayerLists();
    }

    public void setCash(boolean add, long cash) {
        backupCash = this.cash;
        this.cash = add ? this.cash + cash : cash;
        applyPlayerInfoIntoPlayerLists();
    }

    public void setExp(boolean add, long exp) {
        backupEXP = this.exp;
        this.exp = add ? this.exp + exp : exp;
        applyPlayerInfoIntoPlayerLists();
    }

    public void setGold(long gold) {
        backupGold = this.gold;
        this.gold = gold;
        applyPlayerInfoIntoPlayerLists();
    }

    public void setLevel(boolean add, int level) {
        backupLevel = this.level;
        this.level = add ? this.level + level : level;
        applyPlayerInfoIntoPlayerLists();
    }

    public boolean existsInPlayerLists() {
        String playerInfo = getPlayerInfoForSVCFormat();
        File playerListFile = new File(Main.BASIC_PATH + "/PlayerList.txt");

        if (!playerListFile.exists()) return false;
        String playerListStr = new ReadFile().readString(playerListFile);

        return playerListStr != null && playerListStr.contains(playerInfo);
    }

    public void applyPlayerInfoIntoPlayerLists() {
        File playerListFile = new File(Main.BASIC_PATH + "/PlayerList.txt");

        if (existsInPlayerLists()) {
            String playerListStr = new ReadFile().readString(playerListFile);
            String result = playerListStr != null ? playerListStr.replace(getPlayerInfoForSVCFormat(id, name, backupLevel, backupEXP, backupGold, backupCash), getPlayerInfoForSVCFormat()) : "";
            new WriteFile().writeString(playerListFile, result);
        } else {
            String playerListStr = playerListFile.exists() ? new ReadFile().readString(playerListFile) : "";

            assert playerListStr != null;
            String result = playerListStr.equals("") ? getPlayerInfoForSVCFormat() : playerListStr + "\n" + getPlayerInfoForSVCFormat();
            new WriteFile().writeString(playerListFile, result);
        }

        if (Main.verbose) LogHelper.userLog("Applied user information", this);
    }

    public String toString() {
        return "ID | " + id + ", Name | " + name + ", Level | " + level + " EXP | " + exp + ", Gold | " + gold + "G, Cash | " + cash + "C";
    }

}

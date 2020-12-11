package com.developerdecuple.core;

public class Player {

    private final long id;
    private final String name;
    private int level;
    private long exp, gold, cash;

    public Player(long id, String name, int level, long exp, long gold, long cash) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.gold = gold;
        this.cash = cash;
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

    public void setCash(boolean add, long cash) {
        this.cash = add ? this.cash + cash : cash;
    }

    public void setExp(boolean add, long exp) {
        this.exp = add ? this.exp + exp : exp;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public void setLevel(boolean add, int level) {
        this.level = add ? this.level + level : level;
    }
}

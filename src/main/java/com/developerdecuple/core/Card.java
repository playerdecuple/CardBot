package com.developerdecuple.core;

public class Card {

    private final int id;
    private final String name;
    private final int star;
    private int atk;
    private int def;
    private final String description;

    public Card(int id, String name, int star, int atk, int def, String description) {
        this.id = id;
        this.name = name;
        this.star = star;
        this.atk = atk;
        this.def = def;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStar() {
        return star;
    }

    public int getATK() {
        return atk;
    }

    public int getDEF() {
        return def;
    }

    public String getDescription() {
        return description;
    }

    public void setATK(int amount, boolean add) {
        atk = add ? atk += amount : amount;
    }

    public void setDEF(int amount, boolean add) {
        def = add ? def += amount : amount;
    }

}

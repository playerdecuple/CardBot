package com.developerdecuple.core;

import com.developerdecuple.Main;

import java.io.File;
import java.util.Arrays;

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
        atk = add ? atk + amount : amount;
    }

    public void setDEF(int amount, boolean add) {
        def = add ? def + amount : amount;
    }

    public String getCardInfoForSVCFormat() {
        String[] info = {String.valueOf(id), name, String.valueOf(star), String.valueOf(atk), String.valueOf(def), description};
        return String.join(",", Arrays.copyOfRange(info, 0, info.length));
    }

    public String toString(boolean msg) {
        return msg ? name + "(★" + star + ") / ATK " + atk + ", DEF " + def + " / " + description : id + name + "(★" + star + ") / ATK " + atk + ", DEF " + def + " / " + description;
    }

    public void saveDeck(String id) {
        String cardInfo = getCardInfoForSVCFormat();
        File deckFile = new File(Main.BASIC_PATH + "/Database/" + id + "/deck.txt");
        if (!deckFile.exists()) return;

        String decks = new ReadFile().readString(deckFile);
        String result = decks == null || decks.equals("") ? cardInfo : decks + "\n" + cardInfo;

        new WriteFile().writeString(deckFile, result);
    }

}

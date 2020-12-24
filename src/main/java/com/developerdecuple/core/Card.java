package com.developerdecuple.core;

import com.developerdecuple.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Card {

    private int customId = 0;
    private final int id;
    private final String name;
    private final int star;
    private int atk;
    private int def;
    private final String description;
    private boolean battleCard;

    public Card(int id, String name, int star, int atk, int def, String description, boolean battleCard) {
        this.id = id;
        this.name = name;
        this.star = star;
        this.atk = atk;
        this.def = def;
        this.description = description.replace("\n", "");
        this.battleCard = battleCard;
    }

    public Card(int customId, int id, String name, int star, int atk, int def, String description, boolean battleCard) {
        this.customId = customId;
        this.id = id;
        this.name = name;
        this.star = star;
        this.atk = atk;
        this.def = def;
        this.description = description.replace("\n", "");
        this.battleCard = battleCard;
    }

    public boolean isBattleCard() {
        return battleCard;
    }

    public int getCustomId() {
        return customId;
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

    public void setBattleCard(boolean battleCard) {
        this.battleCard = battleCard;
    }

    public void setCustomId(int id) {
        this.customId = id;
    }

    public String getCardInfoForSVCFormat() {
        String sb = getCustomId() + "," +
                id + "," +
                name + "," +
                star + "," +
                atk + "," +
                def + "," +
                description + "," +
                battleCard;
        return sb.replace("\n", "");
    }

    public String getCardInfoForSVCFormat(String id) {
        List<Card> cardList = PlayerManager.getCardListById(id);
        this.customId = Objects.requireNonNull(cardList).size();

        String sb = getCustomId() + "," +
                this.id + "," +
                name + "," +
                star + "," +
                atk + "," +
                def + "," +
                description + "," +
                battleCard;
        return sb.replace("\n", "");
    }

    public String toString(boolean msg) {
        return msg ? name + "(★" + star + ") / ATK " + atk + ", DEF " + def + " / " + description + (battleCard ? " (♠)" : "") : id + name + "(★" + star + ") / ATK " + atk + ", DEF " + def + " / " + description + (battleCard ? " (♠)" : "");
    }

    public void saveDeck(String id) {
        String cardInfo = getCardInfoForSVCFormat(id).replace("\n", "");
        File deckFile = new File(Main.BASIC_PATH + "/Database/" + id + "/deck.txt");
        if (!deckFile.exists()) return;

        String decks = new ReadFile().readString(deckFile);
        String result = decks == null || decks.equals("") ? cardInfo : decks + "\n" + cardInfo;

        new WriteFile().writeString(deckFile, result);
    }

    public void applyDeck(String id) {
        File deckFile = new File(Main.BASIC_PATH + "/Database/" + id + "/deck.txt");
        if (!deckFile.exists()) return;

        String[] deckListStr = Objects.requireNonNull(new ReadFile().readString(deckFile)).split("\n");

        for (int i = 0; i < deckListStr.length; i++) {
            String[] deckStr = deckListStr[i].split(",");
            int deckCustomId = Integer.parseInt(deckStr[0]);

            if (deckCustomId == this.customId) {
                deckListStr[i] = getCardInfoForSVCFormat();
            }
        }

        String result = String.join("\n", Arrays.copyOfRange(deckListStr, 0, deckListStr.length));
        new WriteFile().writeString(deckFile, result);
    }

}

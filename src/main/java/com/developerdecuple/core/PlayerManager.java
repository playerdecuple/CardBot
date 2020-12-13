package com.developerdecuple.core;

import com.developerdecuple.Main;
import net.dv8tion.jda.api.JDA;
import sun.rmi.runtime.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private static final File databaseFile = new File(Main.BASIC_PATH + "/Database/");

    public static void updatePlayer(String id) {

        final boolean existsInDatabase = getPlayerById(id) != null;

        String playerName = Main.jda.retrieveUserById(id).complete().getName();

        if (existsInDatabase) { // <- getPlayerById(id) != null
            Player player = getPlayerById(id);
            if (player != null) { // <- Why IntelliJ shows warning message me?
                player.setName(playerName);
            }

            System.out.println("Course A");
        } else {
            final File newUserDirectory = new File(databaseFile.getPath() + "/" + id);
            newUserDirectory.mkdir();

            final File deckInformationFile = new File(newUserDirectory.getPath() + "/deck.txt"); // DECK
            new WriteFile().writeString(deckInformationFile, "-1,EMPTY_SLOT,-1,-1,-1,EMPTY_SLOT");

            Player newPlayer = new Player(Long.parseLong(id), playerName, 1, 0L, 0L, 0L);
            newPlayer.applyPlayerInfoIntoPlayerLists();

        }

    }

    public static void updatePlayer(String id, JDA jda) {

        final boolean existsInDatabase = getPlayerById(id) != null;

        String playerName = jda.retrieveUserById(id).complete().getName();

        if (existsInDatabase) { // <- getPlayerById(id) != null
            Player player = getPlayerById(id);
            if (player != null) { // <- Why IntelliJ shows warning message me?
                player.setName(playerName);
                if (Main.verbose) LogHelper.userLog("Updated user information", player);
            }
        } else {
            final File newUserDirectory = new File(databaseFile.getPath() + "/" + id);
            newUserDirectory.mkdir();

            final File deckInformationFile = new File(newUserDirectory.getPath() + "/deck.txt");
            new WriteFile().writeString(deckInformationFile, "-1,EMPTY_SLOT,-1,-1,-1,EMPTY_SLOT");

            Player newPlayer = new Player(Long.parseLong(id), playerName, 1, 0L, 0L, 0L);
            newPlayer.applyPlayerInfoIntoPlayerLists();

            LogHelper.userLog("Updated new user information", newPlayer);
        }

    }

    public static Player getPlayerById(String id) {

        String basicPath = Main.BASIC_PATH;
        File playerListFile = new File(basicPath + "/PlayerList.txt");

        if (!playerListFile.exists()) {
            try {
                throw new NoPlayerFileException("Move or create Player List File at '" + basicPath + "'.");
            } catch (NoPlayerFileException e) {
                e.printStackTrace();
            }
        }

        String playerListText = new ReadFile().readString(playerListFile);

        if (playerListText == null) return null;

        if (!playerListText.equals("")) {
            String[] playerListStr = playerListText.split("\n");

            for (String player : playerListStr) {

                String[] playerInfo = player.split(",");

                if (playerInfo[0].equalsIgnoreCase(id)) {

                    long playerId = Long.parseLong(playerInfo[0]);
                    String playerName = playerInfo[1];
                    int playerLevel = Integer.parseInt(playerInfo[2]);
                    long playerEXP = Long.parseLong(playerInfo[3]);
                    long playerGold = Long.parseLong(playerInfo[4]);
                    long playerCash = Long.parseLong(playerInfo[5]);

                    return new Player(playerId, playerName, playerLevel, playerEXP, playerGold, playerCash);

                }

            }
        } else {
            return null;
        }

        return null;

    }

    public static List<Card> getCardListById(String id) {
        File deckFile = new File(Main.BASIC_PATH + "/Database/" + id + "/deck.txt");
        if (!deckFile.exists()) return null;

        String deckString = new ReadFile().readString(deckFile);
        String[] deckList = deckString != null ? deckString.split("\n") : new String[0];

        List<Card> cardList = new ArrayList<>();

        for (String deck : deckList) {
            String[] deckInfo = deck.split(",");
            int cardId = Integer.parseInt(deckInfo[0]);

            if (cardId != -1) {
                String cardName = deckInfo[1];
                int cardStar = Integer.parseInt(deckInfo[2]);
                int cardATK = Integer.parseInt(deckInfo[3]);
                int cardDEF = Integer.parseInt(deckInfo[4]);
                String cardDescription = deckInfo[5];

                cardList.add(new Card(cardId, cardName, cardStar, cardATK, cardDEF, cardDescription));
            }
        }

        return cardList;
    }

}

package com.developerdecuple.core;

import com.developerdecuple.Main;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class CardManager {

    public static void giveCard(int cardId, long playerId) {

        /*
         * DECK SVC FORMAT
         * CARD_ID/CARD_NAME/CARD_STAR/CARD_ATK/CARD_DEF/CARD_DESC
         */

        Card giveCard = CardReader.readCardById(cardId);
        List<Card> cardList = PlayerManager.getCardListById(String.valueOf(playerId));

        int cardNumber = Objects.requireNonNull(cardList).size() - 1;

        if (giveCard == null) {
            try {
                throw new InvalidCardInformationException("ID is wrong!");
            } catch (InvalidCardInformationException e) {
                e.printStackTrace();
            }

            return;
        }

        giveCard.setCustomId(cardNumber);

        giveCard.saveDeck(String.valueOf(playerId));
        if (Main.verbose) LogHelper.userLog("Given a card", Objects.requireNonNull(PlayerManager.getPlayerById(String.valueOf(playerId))));

    }

    public static void removeCard(int index, long playerId) {

        index++; // 왜 5번이 사라지는지 모르겠음

        File deckFile = new File(Main.BASIC_PATH + "/Database/" + playerId + "/deck.txt");
        if (!deckFile.exists()) return;
        String deckStr = new ReadFile().readString(deckFile);

        String[] decks = Objects.requireNonNull(deckStr).split("\n");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < decks.length; i++) {
            if (i != index) {
                if (i == decks.length - 1) {
                    builder.append(decks[i]);
                } else {
                    builder.append(decks[i]).append("\n");
                }
            }
        }

        new WriteFile().writeString(deckFile, builder.toString());
        if (Main.verbose) LogHelper.userLog("Removed a card", Objects.requireNonNull(PlayerManager.getPlayerById(String.valueOf(playerId))));

    }

}

class InvalidCardInformationException extends Exception {

    InvalidCardInformationException(String msg) {
        super(msg);
    }

}
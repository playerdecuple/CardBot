package com.developerdecuple.core;

import com.developerdecuple.Main;

import java.io.File;
import java.util.Objects;

public class CardManager {

    public static void giveCard(int cardId, long playerId) {

        /*
         * DECK SVC FORMAT
         * CARD_ID/CARD_NAME/CARD_STAR/CARD_ATK/CARD_DEF/CARD_DESC
         */

        Card giveCard = CardReader.readCardById(cardId);

        if (giveCard == null) {
            try {
                throw new InvalidCardInformationException("ID is wrong!");
            } catch (InvalidCardInformationException e) {
                e.printStackTrace();
            }

            return;
        }

        File deckFile = new File(Main.BASIC_PATH + "/Database/" + playerId + "/deck.txt");

        if (!deckFile.exists()) return;

        String deckStr = new ReadFile().readString(deckFile);
        String result = deckStr + "\n" + giveCard.getCardInfoForSVCFormat();

        new WriteFile().writeString(deckFile, result);
        if (Main.verbose) LogHelper.userLog("Given a card", Objects.requireNonNull(PlayerManager.getPlayerById(String.valueOf(playerId))));

    }

}

class InvalidCardInformationException extends Exception {

    InvalidCardInformationException(String msg) {
        super(msg);
    }

}
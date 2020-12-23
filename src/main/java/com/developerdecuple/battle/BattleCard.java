package com.developerdecuple.battle;

import com.developerdecuple.Main;
import com.developerdecuple.core.*;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class BattleCard {

    public static void battleCardSet(Player player, Card[] cards) {

        File battleDeckFile = new File(Main.BASIC_PATH + "/Database/" + player.getId() + "/battle.txt");
        StringBuilder saveResult = new StringBuilder();

        if (battleDeckFile.exists()) return;
        
        for (int i = 0; i < cards.length; i++) {
            
            saveResult.append(i)
                    .append(",")
                    .append(cards[i].getCardInfoForSVCFormat())
                    .append(i == cards.length - 1 ? "" : "\n"); // 마지막엔 \n을 넣지 않습니다.
            
        }
        
        new WriteFile().writeString(battleDeckFile, saveResult.toString());

    }

    public static void battleCardAdd(Player player, Card card) {

        File battleDeckFile = new File(Main.BASIC_PATH + "/Database/" + player.getId() + "/battle.txt");
        if (!battleDeckFile.exists()) return;

        String battleDeckInformation = new ReadFile().readString(battleDeckFile);
        if (battleDeckInformation == null || battleDeckInformation.equals("")) return;

        String[] battleDecks = Objects.requireNonNull(battleDeckInformation).split("\n");

        String appendResult = battleDeckInformation + "\n" +
                battleDecks.length + "," +
                card.getCardInfoForSVCFormat();

        new WriteFile().writeString(battleDeckFile, appendResult);

    }

    public static void printBattleCards(String id) {

        List<Card> cardList = PlayerManager.getBattleCardListById(id);

        for (Card card : Objects.requireNonNull(cardList)) {
            System.out.println(card.toString(false));
        }

    }

}

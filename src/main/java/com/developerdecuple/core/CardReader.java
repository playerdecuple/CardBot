package com.developerdecuple.core;

import com.developerdecuple.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CardReader {

    public static List<Card> readAllCards() {
        String basicPath = Main.BASIC_PATH;
        File cardListFile = new File(basicPath + "/CardList.txt");

        if (!cardListFile.exists()) {
            try {
                throw new NoCardFileException("Move or create Card List File at '" + basicPath + "'.");
            } catch (NoCardFileException e) {
                e.printStackTrace();
            }
        }

        String cardListStr = new ReadFile().readString(cardListFile);

        /*
         * Card List Text File is using svc type. [ DON'T USE SPACE AND COMMA ]
         * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
         * ID,NAME,STAR,ATK,DEF,DESCRIPTION
         */

        String[] cards = cardListStr != null ? cardListStr.split("\n") : new String[0];
        List<Card> cardList = new ArrayList<Card>();

        for (String card : cards) {
            String[] cardInfo = card.split(",");

            int id = 0, star = 0, attackStatus = 0, defenseStatus = 0;
            String name = null, description = null;

            try {

                id = Integer.parseInt(cardInfo[0]);
                name = cardInfo[1];
                star = Integer.parseInt(cardInfo[2]);
                attackStatus = Integer.parseInt(cardInfo[3]);
                defenseStatus = Integer.parseInt(cardInfo[4]);
                description = cardInfo[5];

            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {

                try {
                    throw new CardInfoFormatMismatchException("The card information format is incorrect.");
                } catch (CardInfoFormatMismatchException cardInfoFormatMismatchException) {
                    cardInfoFormatMismatchException.printStackTrace();
                }

            }

            cardList.add(new Card(id, name, star, attackStatus, defenseStatus, description));


        }

        return cardList;
    }

    public static void printAllCardInfo() {
        List<Card> cardList = readAllCards();

        for (Card card : cardList) {
            System.out.println(card.getId() + ". \"" + card.getName() + "\"(★" + card.getStar() + ") : A" + card.getATK() + "/D" + card.getDEF() + ", Description : " + card.getDescription());
        }
    }

}

class NoCardFileException extends FileNotFoundException {

    NoCardFileException(String message) {
        super(message);
    }

}

class CardInfoFormatMismatchException extends Exception {

    CardInfoFormatMismatchException(String message) {
        super(message);
    }

}

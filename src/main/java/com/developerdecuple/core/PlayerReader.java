package com.developerdecuple.core;

import com.developerdecuple.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PlayerReader {

    public static List<Player> readAllPlayers() {

        String basicPath = Main.BASIC_PATH;
        File playerListFile = new File(basicPath + "/PlayerList.txt");

        if (!playerListFile.exists()) {
            try {
                throw new NoPlayerFileException("Move or create Player List File at '" + basicPath + "'.");
            } catch (NoPlayerFileException e) {
                e.printStackTrace();
            }
        }

        String playerListStr = new ReadFile().readString(playerListFile);

        /*
         * Player List Text File is using svc format. [ DON'T USE SPACE AND COMMA ]
         * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
         * ID,NAME,LEVEL,EXP,GOLD,CASH,0
         */

        String[] players = playerListStr != null ? playerListStr.split("\n") : new String[0];
        List<Player> playerList = new ArrayList<Player>();

        for (String player : players) {
            String[] playerInfo = player.split(",");

            long id = 0L, exp = 0L, gold = 0L, cash = 0L;
            String name = null;
            int level = 0;

            try {
                id = Long.parseLong(playerInfo[0]);
                name = playerInfo[1];
                level = Integer.parseInt(playerInfo[2]);
                exp = Long.parseLong(playerInfo[3]);
                gold = Long.parseLong(playerInfo[4]);
                cash = Long.parseLong(playerInfo[5]);
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {

                try {
                    throw new PlayerInfoFormatMismatchException("The player information format is incorrect.");
                } catch (PlayerInfoFormatMismatchException playerInfoFormatMismatchException) {
                    playerInfoFormatMismatchException.printStackTrace();
                }

            }

            playerList.add(new Player(id, name, level, exp, gold, cash));
        }

        return playerList;

    }

    public static void printAllPlayer() {

        List<Player> pl = readAllPlayers();

        for (Player player : pl) {
            System.out.println(player.toString());
        }

    }

}

class NoPlayerFileException extends FileNotFoundException {

    NoPlayerFileException(String message) {
        super(message);
    }

}

class PlayerInfoFormatMismatchException extends Exception {

    PlayerInfoFormatMismatchException(String message) {
        super(message);
    }

}

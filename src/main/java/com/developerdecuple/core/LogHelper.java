package com.developerdecuple.core;

public class LogHelper {

    public static void userLog(String shortComment, Player player) {
        System.out.println(" [ " + shortComment + " ] " + player.toString());
    }

}

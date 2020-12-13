package com.developerdecuple;

import com.developerdecuple.core.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Listener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent ev) {

        User discordUser = ev.getAuthor();
        TextChannel channel = ev.getChannel();
        Message message = ev.getMessage();

        String messageStr = message.getContentRaw();
        String[] messageArguments = messageStr.split(" ");

        if (discordUser.isBot()) return;

        try {
            if (messageArguments[0].equalsIgnoreCase("/등록")) {
                if (messageArguments.length == 1 && PlayerManager.getPlayerById(discordUser.getId()) == null) {
                    PlayerManager.updatePlayer(discordUser.getId(), ev.getJDA());
                    channel.sendMessage("등록을 완료하였습니다.").delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();
                    message.delete().queue();
                } else {
                    channel.sendMessage("이미 등록되어 계십니다.").delay(10, TimeUnit.SECONDS).flatMap(Message::delete).queue();
                    message.delete().queue();
                }
            }

            if (messageArguments[0].equalsIgnoreCase("/테스트")) {
                if (messageArguments.length == 1 && PlayerManager.getPlayerById(discordUser.getId()) != null) {
                    CardManager.giveCard(0, Long.parseLong(discordUser.getId()));
                    channel.sendMessage("`" + CardReader.readCardById(0).getName() + "` 카드를 드렸습니다.").queue();
                    message.delete().queue();
                } else {
                    channel.sendMessage("카드를 받으려면 등록을 해야 합니다. `/등록`을 먼저 입력해 주세요.").queue();
                    message.delete().queue();
                }
            }

            if (messageArguments[0].equalsIgnoreCase("/리스트")) {
                if (messageArguments.length == 1 && PlayerManager.getPlayerById(discordUser.getId()) != null) {
                    List<Card> cards = PlayerManager.getCardListById(discordUser.getId());

                    if (cards == null || cards.size() < 1) {
                        channel.sendMessage("카드가 없으시네요.").queue();
                        return;
                    }

                    StringBuilder cardMessageBuilder = new StringBuilder("```md\n# " + discordUser.getName() + "님의 카드들\n\n" + "> 카드 개수 : " + cards.size() + "\n\n");

                    for (int i = 1; i <= cards.size(); i++) {
                        if (cardMessageBuilder.toString().length() < 1600) {
                            cardMessageBuilder.append(i).append(". ").append(cards.get(i - 1).toString(true)).append("\n");
                        }
                    }

                    channel.sendMessage(cardMessageBuilder.append("\n```").toString()).delay(1, TimeUnit.MINUTES).queue();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

}

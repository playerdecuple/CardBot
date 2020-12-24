package com.developerdecuple;

import com.developerdecuple.battle.BattleCard;
import com.developerdecuple.core.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Random;
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
                    channel.sendMessage("`" + Objects.requireNonNull(CardReader.readCardById(0)).getName() + "` 카드를 드렸습니다.").queue();
                    message.delete().queue();
                } else {
                    channel.sendMessage("카드를 받으려면 등록을 해야 합니다. `/등록`을 먼저 입력해 주세요.").queue();
                    message.delete().queue();
                }
            }

            if (messageArguments[0].equalsIgnoreCase("/리스트")) {
                if (messageArguments.length == 1 && PlayerManager.getPlayerById(discordUser.getId()) != null) {
                    sendCardList(channel, discordUser);
                }
            }

            if (messageArguments[0].equalsIgnoreCase("/강화")) {
                if (messageArguments.length == 1) {
                    channel.sendMessage("카드를 선택해 주세요. (`/강화 [카드번호]`, 카드 번호는 `/리스트`에서 확인할 수 있습니다.)").queue();
                    sendCardList(channel, discordUser);
                } else if (messageArguments.length == 2) {

                    if (PlayerManager.getPlayerById(discordUser.getId()) == null) return;

                    int cardNumber = Integer.parseInt(messageArguments[1]);
                    List<Card> cardList = PlayerManager.getCardListById(discordUser.getId());

                    if (cardNumber > Objects.requireNonNull(cardList).size()) {
                        channel.sendMessage("카드 번호가 올바르지 않습니다.").queue();
                        return;
                    }

                    int index = cardNumber - 1;
                    Card card = cardList.get(index);

                    if (card.isBattleCard()) {
                        channel.sendMessage("이 카드는 배틀카드이므로 강화할 수 없습니다. 배틀카드를 해제해 주세요.").queue();
                        return;
                    }

                    card.setATK(new Random().nextInt(5) + 1, true);
                    card.setDEF(new Random().nextInt(5) + 1, true);
                    card.applyDeck(discordUser.getId());

                    channel.sendMessage("강화 결과!\n```md\n1. " + card.toString(true) + "\n```").queue();

                }
            }

            if (messageArguments[0].equalsIgnoreCase("/배틀카드")) {
                if (messageArguments.length == 1) {
                    channel.sendMessage("카드를 선택해 주세요. (`/배틀카트 [카드번호]`, 카드 번호는 `/리스트`에서 확인할 수 있습니다.)").queue();
                    sendCardList(channel, discordUser);
                } else {

                    if (PlayerManager.getPlayerById(discordUser.getId()) == null) return;

                    int cardNumber = Integer.parseInt(messageArguments[1]);
                    List<Card> cardList = PlayerManager.getCardListById(discordUser.getId());

                    if (cardNumber > Objects.requireNonNull(cardList).size()) {
                        channel.sendMessage("카드 번호가 올바르지 않습니다.").queue();
                        return;
                    }

                    int index = cardNumber - 1;
                    Card card = cardList.get(index);

                    List<Card> battleCardList = PlayerManager.getBattleCardListById(discordUser.getId());

                    if (!card.isBattleCard() && battleCardList != null) {
                        if (battleCardList.size() >= 5) {
                            channel.sendMessage("배틀 카드는 5개까지만 선택할 수 있습니다.").delay(10, TimeUnit.SECONDS).queue();
                            return;
                        }
                    }

                    card.setBattleCard(!card.isBattleCard());
                    card.applyDeck(discordUser.getId());

                    channel.sendMessage(cardNumber + "번 카드(`" + card.getName() + "`)를 배틀 카드" + (card.isBattleCard() ? "로 설정했습니다." : "에서 제외했습니다.")).queue();

                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    public void sendCardList(TextChannel channel, User discordUser) {
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

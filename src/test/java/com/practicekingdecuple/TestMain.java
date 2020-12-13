package com.practicekingdecuple;

import com.developerdecuple.Listener;
import com.developerdecuple.Main;
import com.developerdecuple.core.PlayerManager;
import com.developerdecuple.core.PlayerReader;
import com.developerdecuple.core.ReadFile;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.File;

public class TestMain {

    public static void main(String[] args) throws LoginException, InterruptedException {
        final String token = new ReadFile().readString(new File(Main.BASIC_PATH + "/BotToken.txt"));

        JDA botCore = JDABuilder.createDefault(token).setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_BANS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_EMOJIS)
                .setAutoReconnect(true)
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new Listener())
                .build()
                .awaitReady();
    }

}

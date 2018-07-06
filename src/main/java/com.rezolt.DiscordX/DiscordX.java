package com.rezolt.DiscordX;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;


public class DiscordX extends PluginBase {
    private Config config;
    private JDA jda;
    private TextChannel channel;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();
        this.getLogger().info(TextFormat.GREEN + "Plugin Enabled!");

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(config.getString("discordBotToken"))
                    .buildBlocking();
            this.getLogger().info(TextFormat.GREEN + "Connected to bot " + jda.getSelfUser().getName());
            this.getServer().getPluginManager().registerEvents(new Events(jda.getTextChannelById(config.getString("channelId")), jda.getGuildById(config.getString("guildId")), this), this);
            this.channel = jda.getTextChannelById(config.getString("channelId"));
            if(config.getBoolean("serverUpMessages", true)) {
                channel.sendMessage(config.getString("serverUpMessage", "The Server is now online!")).queue();
            }
        } catch(Exception e) {
            e.printStackTrace();
            this.getLogger().info(TextFormat.RED + "JDA not found! Please refer to this: ");
        }
    }
    @Override
    public void onDisable()
    {
        if(config.getBoolean("serverDownMessages", true))
        {
            channel.sendMessage(config.getString("serverDownMessage", "The Server is now offline")).queue();
        }
    }
}

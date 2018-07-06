package com.rezolt.DiscordX;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class Events implements Listener {
    private TextChannel channel;
    private Guild guild;
    private final DiscordX main;

    public Events(TextChannel channel, Guild guild, DiscordX discord) {
        this.channel = channel;
        this.guild = guild;
        this.main = discord;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (main.getConfig().getBoolean("joinMessages", true)) {
            channel.sendMessage(main.getConfig().getString("joinMessage", "[player] joined the server!").replace("[player]", event.getPlayer().getName())).queue();
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (main.getConfig().getBoolean("leaveMessages", true)) {
            channel.sendMessage(main.getConfig().getString("leaveMessage", "[player] left the server").replace("[player]", event.getPlayer().getName())).queue();
        }
    }
}

package io.github.ryanpereiras.listeners;

import io.github.ryanpereiras.commands.BanExecutor;
import io.github.ryanpereiras.rBans;
import io.github.ryanpereiras.utils.ConfigUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;


public class JoinEvent implements Listener {
    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public static void onJoinBan(PlayerLoginEvent e){

        String player = e.getPlayer().getName().toLowerCase();
        if (rBans.bans.containsKey(player)){
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            e.setKickMessage(ConfigUtils.joinBan(e.getPlayer().getName().toLowerCase()));
        }
    }
}

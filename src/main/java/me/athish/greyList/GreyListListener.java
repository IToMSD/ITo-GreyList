package me.athish.greyList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GreyListListener implements Listener {

    private final GreyListManager greyListManager;

    public GreyListListener(GreyListManager greyListManager) {
        this.greyListManager = greyListManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (greyListManager.isGreyListed(event.getPlayer().getName())) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        } else if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (greyListManager.isGreyListed(event.getPlayer().getName()) && !greyListManager.isChatAllowed()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', greyListManager.greyListConfig.getString("settings.chatBlockedMessage", "&cYou are not allowed to chat.")));
        }
    }
}
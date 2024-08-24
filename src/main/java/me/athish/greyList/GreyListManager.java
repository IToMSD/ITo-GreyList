package me.athish.greyList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GreyListManager {
    private final Set<String> greyListedPlayers = new HashSet<>();
    private boolean chatAllowed = false;
    private final GreyList plugin;
    private final File greyListFile;
    public FileConfiguration greyListConfig;

    public GreyListManager(GreyList plugin) {
        this.plugin = plugin;
        this.greyListFile = new File(plugin.getDataFolder(), "greylist.yml");
        this.greyListConfig = YamlConfiguration.loadConfiguration(greyListFile);
    }

    public void addPlayer(String playerName) {
        greyListedPlayers.add(playerName.toLowerCase());

        if (Bukkit.getPlayer(playerName) != null) {
            Bukkit.getPlayer(playerName).setGameMode(org.bukkit.GameMode.SPECTATOR);
        }
    }

    public void removePlayer(String playerName) {
        greyListedPlayers.remove(playerName.toLowerCase());

        if (Bukkit.getPlayer(playerName) != null) {
            Bukkit.getPlayer(playerName).setGameMode(GameMode.SURVIVAL);
        }
    }

    public boolean isGreyListed(String playerName) {
        return greyListedPlayers.contains(playerName.toLowerCase());
    }

    public void setChatAllowed(boolean allowed) {
        this.chatAllowed = allowed;
    }

    public boolean isChatAllowed() {
        return chatAllowed;
    }

    public Set<String> getGreyListedPlayers() {
        return greyListedPlayers;
    }

    public void loadGreyList() {
        if (greyListFile.exists()) {
            chatAllowed = greyListConfig.getBoolean("settings.chatAllowed", false);
            greyListedPlayers.addAll(greyListConfig.getStringList("greylistedPlayers"));
        }
    }

    public void saveGreyList() {
        greyListConfig.set("settings.chatAllowed", chatAllowed);
        greyListConfig.set("settings.chatBlockedMessage", "&cChat is blocked for greylisted players.");
        greyListConfig.set("greylistedPlayers", new ArrayList<>(greyListedPlayers));
        try {
            greyListConfig.save(greyListFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package me.athish.greyList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GreyList extends JavaPlugin {

    private GreyListManager greyListManager;

    @Override
    public void onEnable() {
        greyListManager = new GreyListManager(this);
        greyListManager.loadGreyList();
        getCommand("greylist").setExecutor(new GreyListCommand(this, greyListManager));
        Bukkit.getPluginManager().registerEvents(new GreyListListener(greyListManager), this);
    }

    @Override
    public void onDisable() {
        greyListManager.saveGreyList();
    }

    public GreyListManager getGreyListManager() {
        return greyListManager;
    }
}
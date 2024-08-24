package me.athish.greyList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GreyListCommand implements CommandExecutor {

    private final GreyList plugin;
    private final GreyListManager greyListManager;

    public GreyListCommand(GreyList plugin, GreyListManager greyListManager) {
        this.plugin = plugin;
        this.greyListManager = greyListManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /greylist <add|remove|show|chat> [player]");
            return true;
        }

        if (args[0].equalsIgnoreCase("add") && args.length == 2) {
            if (sender.hasPermission("itomsd.greylist.admin")) {
                greyListManager.addPlayer(args[1]);
                sender.sendMessage("Player " + args[1] + " added to GreyList.");
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
        } else if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
            if (sender.hasPermission("itomsd.greylist.admin")) {
                greyListManager.removePlayer(args[1]);
                sender.sendMessage("Player " + args[1] + " removed from GreyList.");
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
        } else if (args[0].equalsIgnoreCase("show")) {
            if (sender.hasPermission("itomsd.greylist.admin")) {
                sender.sendMessage("GreyListed players: " + greyListManager.getGreyListedPlayers());
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
        } else if (args[0].equalsIgnoreCase("chat")) {
            if (sender.hasPermission("itomsd.greylist.admin")) {
                greyListManager.setChatAllowed(!greyListManager.isChatAllowed());
                sender.sendMessage("Chat for non-greylisted players is now " + (greyListManager.isChatAllowed() ? "allowed" : "blocked") + ".");
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
        } else {
            sender.sendMessage("Invalid command usage.");
        }

        return true;
    }
}
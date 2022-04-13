package de.voidserver.commands;

import de.voidserver.managers.BackManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {

    private final BackManager manager;

    public BackCommand(BackManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            Location location = manager.get(player);
            if (location == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo location history."));
                return true;
            }
            player.teleport(location);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTeleporting..."));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cThis command is only available to players."));
            return true;
        }
        return true;
    }
}

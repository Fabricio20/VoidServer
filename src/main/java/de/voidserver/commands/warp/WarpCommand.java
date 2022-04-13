package de.voidserver.commands.warp;

import de.voidserver.managers.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

    private final WarpManager manager;

    public WarpCommand(WarpManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&eWarps: " + String.join("&e, &a", manager.list()) + "&e."));
            } else {
                String name = args[0];
                Location home = this.manager.getWarp(name);
                if (home == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid warp."));
                    return true;
                }
                player.teleport(home);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&aTeleported to &e" + name + "&a."));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cThis command is only available to players."));
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        } else if (!command.getName().equalsIgnoreCase("warp")
                && !command.getName().equalsIgnoreCase("warps")) {
            return null;
        }
        return this.manager.list().stream()
                .toList();
    }

}

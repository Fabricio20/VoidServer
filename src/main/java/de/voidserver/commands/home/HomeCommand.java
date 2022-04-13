package de.voidserver.commands.home;

import de.voidserver.managers.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class HomeCommand implements CommandExecutor, TabCompleter {

    private final HomeManager manager;

    public HomeCommand(HomeManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&eHomes: " + String.join("&e, &a", manager.list(player)) + "&e."));
            } else {
                String name = args[0];
                Location home = this.manager.getHome(player, name);
                if (home == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid home."));
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
        if (!(sender instanceof Player player)) {
            return null;
        } else if (!command.getName().equalsIgnoreCase("home")
                && !command.getName().equalsIgnoreCase("homes")) {
            return null;
        }
        return this.manager.list(player).stream()
                .toList();
    }

}

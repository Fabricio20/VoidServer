package de.voidserver.commands.home;

import de.voidserver.managers.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    private final HomeManager manager;

    public SetHomeCommand(HomeManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMissing home name."));
                return true;
            } else {
                String name = args[0];
                manager.create(player, name);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aHome created successfully."));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cThis command is only available to players."));
            return true;
        }
        return true;
    }
}

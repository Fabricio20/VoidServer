package de.voidserver.commands.warp;

import de.voidserver.managers.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    private final WarpManager manager;

    public SetWarpCommand(WarpManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMissing warp name."));
                return true;
            } else {
                String name = args[0];
                manager.create(player, name);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aWarp created successfully."));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cThis command is only available to players."));
            return true;
        }
        return true;
    }

}

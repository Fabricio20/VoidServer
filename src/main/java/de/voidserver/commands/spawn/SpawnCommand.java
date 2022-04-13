package de.voidserver.commands.spawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&aTeleporting.."));
            World world = Bukkit.getWorld("world");
            if (world == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&cMain world not found.."));
                return true;
            }
            player.teleport(world.getSpawnLocation());
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cThis command is only available to players."));
            return true;
        }
        return true;
    }

}

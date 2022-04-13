package de.voidserver.managers;

import net.notfab.spigot.simpleconfig.Section;
import net.notfab.spigot.simpleconfig.SimpleConfig;
import net.notfab.spigot.simpleconfig.SimpleConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class WarpManager {

    private final SimpleConfigManager configManager;

    public WarpManager(SimpleConfigManager configManager) {
        this.configManager = configManager;
    }

    private SimpleConfig getFile() {
        return this.configManager.getNewConfig("warps.yml");
    }

    public Set<String> list() {
        return this.getFile().getKeys();
    }

    public Location getWarp(String name) {
        SimpleConfig config = this.getFile();
        if (!config.contains(name)) {
            return null;
        }
        Section warp = config.getSection(name);
        World world = Bukkit.getWorld(UUID.fromString(warp.getString("world")));
        if (world == null) {
            return null;
        }
        double x = warp.getDouble("x");
        double y = warp.getDouble("y");
        double z = warp.getDouble("z");
        return new Location(world, x, y, z);
    }

    public void create(Player player, String name) {
        SimpleConfig config = this.getFile();
        Location location = player.getLocation();
        config.set(name + ".x", location.getX());
        config.set(name + ".y", location.getY());
        config.set(name + ".z", location.getZ());
        config.set(name + ".world", location.getWorld().getUID().toString());
        config.save();
    }

    public void delete(String name) {
        SimpleConfig config = this.getFile();
        if (!config.contains(name)) {
            return;
        }
        config.removeKey(name);
        config.save();
    }

}

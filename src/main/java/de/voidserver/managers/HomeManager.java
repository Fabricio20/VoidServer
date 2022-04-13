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

public class HomeManager {

    private final SimpleConfigManager configManager;

    public HomeManager(SimpleConfigManager configManager) {
        this.configManager = configManager;
    }

    private SimpleConfig getFile(Player player) {
        return this.configManager.getNewConfig("homes/" + player.getUniqueId() + ".yml");
    }

    public Set<String> list(Player player) {
        return this.getFile(player).getKeys();
    }

    public Location getHome(Player player, String name) {
        SimpleConfig config = this.getFile(player);
        if (!config.contains(name)) {
            return null;
        }
        Section home = config.getSection(name);
        World world = Bukkit.getWorld(UUID.fromString(home.getString("world")));
        if (world == null) {
            return null;
        }
        double x = home.getDouble("x");
        double y = home.getDouble("y");
        double z = home.getDouble("z");
        return new Location(world, x, y, z);
    }

    public void create(Player player, String name) {
        SimpleConfig config = this.getFile(player);
        Location location = player.getLocation();
        config.set(name + ".x", location.getX());
        config.set(name + ".y", location.getY());
        config.set(name + ".z", location.getZ());
        config.set(name + ".world", location.getWorld().getUID().toString());
        config.save();
    }

    public void delete(Player player, String name) {
        SimpleConfig config = this.getFile(player);
        if (!config.contains(name)) {
            return;
        }
        config.removeKey(name);
        config.save();
    }

}

package de.voidserver.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackManager {

    private final Map<UUID, Location> history = new HashMap<>();

    public void update(Player player) {
        this.update(player, player.getLocation());
    }

    public void update(Player player, Location location) {
        history.put(player.getUniqueId(), location);
    }

    public Location get(Player player) {
        return this.history.get(player.getUniqueId());
    }

}

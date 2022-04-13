package de.voidserver.listeners;

import de.voidserver.managers.BackManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Set;

public class BackListener implements Listener {

    private final BackManager manager;
    private final Set<PlayerTeleportEvent.TeleportCause> causes = new HashSet<>();

    public BackListener(BackManager manager) {
        this.manager = manager;
        this.causes.add(PlayerTeleportEvent.TeleportCause.COMMAND);
        this.causes.add(PlayerTeleportEvent.TeleportCause.PLUGIN);
        this.causes.add(PlayerTeleportEvent.TeleportCause.UNKNOWN);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        manager.update(event.getEntity());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (!causes.contains(event.getCause())) {
            return;
        }
        manager.update(event.getPlayer(), event.getFrom());
    }

}

package de.voidserver;

import de.voidserver.commands.BackCommand;
import de.voidserver.commands.home.DelHomeCommand;
import de.voidserver.commands.home.HomeCommand;
import de.voidserver.commands.home.SetHomeCommand;
import de.voidserver.commands.spawn.SetSpawnCommand;
import de.voidserver.commands.spawn.SpawnCommand;
import de.voidserver.commands.warp.DelWarpCommand;
import de.voidserver.commands.warp.SetWarpCommand;
import de.voidserver.commands.warp.WarpCommand;
import de.voidserver.listeners.BackListener;
import de.voidserver.listeners.SpawnerListener;
import de.voidserver.managers.BackManager;
import de.voidserver.managers.HomeManager;
import de.voidserver.managers.WarpManager;
import net.notfab.spigot.simpleconfig.SimpleConfigManager;
import net.notfab.spigot.simpleconfig.spigot.SpigotConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VoidServer extends JavaPlugin {

    private final SimpleConfigManager configManager;

    public VoidServer() {
        this.configManager = new SpigotConfigManager(this);
    }

    @Override
    public void onEnable() {
        HomeManager homeManager = new HomeManager(this.configManager);
        this.getCommand("home").setExecutor(new HomeCommand(homeManager));
        this.getCommand("sethome").setExecutor(new SetHomeCommand(homeManager));
        this.getCommand("delhome").setExecutor(new DelHomeCommand(homeManager));

        WarpManager warpManager = new WarpManager(this.configManager);
        this.getCommand("warp").setExecutor(new WarpCommand(warpManager));
        this.getCommand("setwarp").setExecutor(new SetWarpCommand(warpManager));
        this.getCommand("delwarp").setExecutor(new DelWarpCommand(warpManager));

        BackManager backManager = new BackManager();
        this.getCommand("back").setExecutor(new BackCommand(backManager));

        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());

        getServer().getPluginManager()
                .registerEvents(new SpawnerListener(this), this);
        getServer().getPluginManager()
                .registerEvents(new BackListener(backManager), this);
    }

}

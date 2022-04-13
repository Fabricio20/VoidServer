package de.voidserver.listeners;

import de.voidserver.VoidServer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.logging.Logger;

public class SpawnerListener implements Listener {

    private final Logger logger;
    private final NamespacedKey key;

    public SpawnerListener(VoidServer plugin) {
        this.logger = plugin.getLogger();
        this.key = new NamespacedKey(plugin, "spawner-type");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        } else if (event.getBlock().getType() != Material.SPAWNER) {
            return;
        }
        ItemStack hand = event.getPlayer().getItemInUse();
        if (hand == null
                || !hand.containsEnchantment(Enchantment.SILK_TOUCH)) {
            logger.warning(event.getPlayer().getName() + " broke a spawner without silk touch.");
            return;
        }
        EntityType type = ((CreatureSpawner) event.getBlock().getState())
                .getSpawnedType();

        ItemStack stack = new ItemStack(Material.SPAWNER);
        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(this.key, PersistentDataType.STRING, type.name());
        stack.setItemMeta(itemMeta);

        logger.info(event.getPlayer().getName() + " dropped a " + type.name() + " spawner.");
        event.getBlock().getWorld()
                .dropItemNaturally(event.getBlock().getLocation(), stack);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) {
            return;
        }
        ItemStack stack = event.getItemInHand();
        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(this.key, PersistentDataType.STRING)) {
            String name = container.get(this.key, PersistentDataType.STRING);
            EntityType type = EntityType.valueOf(name);

            CreatureSpawner spawner = ((CreatureSpawner) event.getBlock().getState());
            spawner.setSpawnedType(type);
            logger.info(event.getPlayer().getName() + " placed a " + type.name() + " spawner.");
        }
    }

}

package com.omniointeractive.phoenyx.item.listener;

import com.omniointeractive.phoenyx.PhoenyxPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Listener class to prevent {@link com.omniointeractive.phoenyx.api.item.Item}'s tagged as
 * {@link com.omniointeractive.phoenyx.api.item.annotation.NonPlaceable} from being placed in the world.
 */
public class NonPlaceableListener implements Listener {

    private final PhoenyxPlugin plugin;

    /**
     * Initialises the listener.
     *
     * @param plugin The parent plugin's main class instance.
     */
    public NonPlaceableListener(PhoenyxPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Invoked when a player places a block in the world.
     *
     * @param event The block place event fired by the server.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event) {
        this.plugin.getItemRegister().getItem(event.getItemInHand()).ifPresent(item -> {
            if (!item.isPlaceable()) {
                event.setCancelled(true);
            }
        });
    }
}

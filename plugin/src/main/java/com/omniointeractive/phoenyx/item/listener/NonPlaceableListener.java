package com.omniointeractive.phoenyx.item.listener;

import com.omniointeractive.phoenyx.Phoenyx;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Listener class to prevent {@link com.omniointeractive.phoenyx.api.item.Item}'s tagged as
 * {@link com.omniointeractive.phoenyx.api.item.annotation.NonPlaceable} from being placed in the world.
 */
public class NonPlaceableListener implements Listener {

    private final Phoenyx phoenyx;

    /**
     * Initialises the listener.
     *
     * @param phoenyx The parent plugin's main class instance.
     */
    public NonPlaceableListener(@NotNull final Phoenyx phoenyx) {
        this.phoenyx = phoenyx;
    }

    /**
     * Invoked when a player places a block in the world.
     *
     * @param event The block place event fired by the server.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(final BlockPlaceEvent event) {
        this.phoenyx.getItemRegister().getItem(event.getItemInHand()).ifPresent(item -> {
            if (!item.isPlaceable()) {
                event.setCancelled(true);
            }
        });
    }
}

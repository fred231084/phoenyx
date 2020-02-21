package com.omniointeractive.phoenyx.item.listener;

import com.omniointeractive.phoenyx.Phoenyx;
import com.omniointeractive.phoenyx.api.item.interfaces.Placeable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Listener class to only allow {@link com.omniointeractive.phoenyx.api.item.Item}'s tagged as {@link Placeable} to be
 * placed in the world.
 */
public class PlaceListener implements Listener {

    private final Phoenyx phoenyx;

    /**
     * Initialises the listener.
     *
     * @param phoenyx The parent plugin's main class instance.
     */
    public PlaceListener(@NotNull final Phoenyx phoenyx) {
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

package com.omniointeractive.phoenyx.item.listener;

import com.omniointeractive.phoenyx.Phoenyx;
import com.omniointeractive.phoenyx.api.item.interfaces.BlockBreaking;
import com.omniointeractive.phoenyx.api.util.BlockBreakCheck;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Listener class to invoke custom actions defined by any {@link com.omniointeractive.phoenyx.api.item.Item}'s tagged
 * as {@link BlockBreaking}.
 */
public class BlockBreakListener implements Listener {

    private final Phoenyx phoenyx;

    /**
     * Initialises the listener.
     *
     * @param phoenyx The parent plugin's main class instance.
     */
    public BlockBreakListener(@NotNull final Phoenyx phoenyx) {
        this.phoenyx = phoenyx;
    }

    /**
     * Invoked when a player breaks a block in the world.
     *
     * @param event The block break event fired by the server.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if (!BlockBreakCheck.isGhostEvent(event)) {
            this.phoenyx.getItemRegister().getItem(event.getPlayer().getInventory().getItemInMainHand())
                    .ifPresent(item -> {
                        if (item instanceof BlockBreaking) {
                            ((BlockBreaking) item).onBlockBreak(event);
                        }
                    });
        }
    }
}

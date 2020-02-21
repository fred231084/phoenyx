package com.omniointeractive.phoenyx.api.item.interfaces;

import org.bukkit.event.block.BlockBreakEvent;

/**
 * Interface used to indicate that an {@link com.omniointeractive.phoenyx.api.item.Item} performs custom login when it
 * is used to break a block.
 */
public interface BlockBreaking {

    /**
     * Invoked whenever a block is broken by a player using the {@link com.omniointeractive.phoenyx.api.item.Item}
     * implementing this interface.
     *
     * @param event The block break event.
     */
    void onBlockBreak(BlockBreakEvent event);
}

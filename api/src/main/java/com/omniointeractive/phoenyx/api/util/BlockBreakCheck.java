package com.omniointeractive.phoenyx.api.util;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for checking if a block can be broken by a player (by simulating the BlockBreakEvent).
 */
public class BlockBreakCheck {

    private static final List<Integer> ghostEvents = new ArrayList<>();

    /**
     * Checks if a provided event is a ghost event that this class has created during a simulation.
     *
     * @param event The event to check.
     * @return True if the provided event is a ghost event, and thus should be ignored, otherwise false.
     */
    public static boolean isGhostEvent(BlockBreakEvent event) {
        return ghostEvents.contains(event.hashCode());
    }

    /**
     * Calls a fake BlockBreakEvent to check if a player can break a block.
     *
     * @param block  The block to check.
     * @param player The player to check.
     * @return True if the player can break the block, otherwise false.
     */
    public static boolean canBreak(Block block, Player player) {
        BlockBreakEvent event = new BlockBreakEvent(block, player);
        ghostEvents.add(event.hashCode());
        Bukkit.getPluginManager().callEvent(event);
        ghostEvents.remove(event.hashCode());
        return !event.isCancelled();
    }
}

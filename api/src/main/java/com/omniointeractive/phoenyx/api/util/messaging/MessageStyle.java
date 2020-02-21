package com.omniointeractive.phoenyx.api.util.messaging;

import net.md_5.bungee.api.ChatColor;

/**
 * Different styles that can be applied to {@link Message}s sent to players.
 */
public enum MessageStyle {

    /**
     * Indicates a normal {@link Message} that is used to convey information.
     */
    NORMAL(ChatColor.WHITE),
    /**
     * Indicates that the {@link Message} is conveying details of an error.
     */
    ERROR(ChatColor.RED),
    /**
     * Indicates a {@link Message} that is signalling a successful event has occurred.
     */
    SUCCESS(ChatColor.GREEN);

    private final ChatColor color;

    MessageStyle(ChatColor color) {
        this.color = color;
    }

    /**
     * Returns the chat color of this style.
     *
     * @return The style's color.
     */
    public ChatColor getColor() {
        return this.color;
    }
}

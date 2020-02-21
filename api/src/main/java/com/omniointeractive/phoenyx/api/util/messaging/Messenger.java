package com.omniointeractive.phoenyx.api.util.messaging;

import com.omniointeractive.phoenyx.api.addon.Addon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Responsible for creating {@link Message}s to be sent to players.
 */
public class Messenger {

    /**
     * Returns the generic messenger instance for Phoenyx.
     *
     * @return The Phoenyx messenger.
     */
    public static Messenger getPhoenyxMessenger() {
        return new Messenger("PX");
    }

    /**
     * Returns the messenger instance for an {@link Addon} of Phoenyx.
     *
     * @param addon The {@link Addon} to retrieve a messenger for.
     * @return The {@link Addon}'s messenger instance.
     */
    public static Messenger getAddonMessenger(@NotNull final Addon addon) {
        return new Messenger("PX", addon.getAddonName());
    }

    private final BaseComponent[] prefix;

    private Messenger(@NotNull final String prefix) {
        this.prefix = new ComponentBuilder(prefix).color(ChatColor.DARK_PURPLE).bold(true)
                .append(" \u00BB").color(ChatColor.GRAY).bold(true)
                .append(" ").reset().create();
    }

    private Messenger(@NotNull final String prefix, @NotNull final String addon) {
        this.prefix = new ComponentBuilder(prefix).color(ChatColor.DARK_PURPLE).bold(true)
                .append(" \u00BB ").color(ChatColor.GRAY).bold(true)
                .append(addon).color(ChatColor.LIGHT_PURPLE).bold(true)
                .append(" \u00BB").color(ChatColor.GRAY).bold(true)
                .append(" ").reset().create();
    }

    /**
     * Creates a {@link Message} from a string, defaulting to {@link MessageStyle#NORMAL}.
     *
     * @param message The contents of the {@link Message} to create.
     * @return The {@link Message} object.
     */
    public Message create(@NotNull final String message) {
        return this.create(message, MessageStyle.NORMAL);
    }

    /**
     * Creates a {@link Message} from an array of components created using Spigot's Component API, defaulting to
     * {@link MessageStyle#NORMAL}.
     *
     * @param message The contents of the {@link Message} to create.
     * @return The {@link Message} object.
     */
    public Message create(@NotNull final BaseComponent[] message) {
        return this.create(message, MessageStyle.NORMAL);
    }

    /**
     * Creates a {@link Message} from a string.
     *
     * @param message The contents of the {@link Message} to create.
     * @param style   The {@link MessageStyle} to use for the created {@link Message}.
     * @return The {@link Message} object.
     */
    public Message create(@NotNull final String message, @NotNull final MessageStyle style) {
        return this.create(TextComponent.fromLegacyText(message), style);
    }

    /**
     * Creates a {@link Message} from an array of components created using Spigot's Component API.
     *
     * @param message The contents of the {@link Message} to create.
     * @param style   The {@link MessageStyle} to use for the created {@link Message}.
     * @return The {@link Message} object.
     */
    public Message create(@NotNull final BaseComponent[] message, @NotNull final MessageStyle style) {
        return new ChatMessage(new ComponentBuilder("")
                .append(this.prefix)
                .append("").reset().color(style.getColor())
                .append(message)
                .create());
    }
}

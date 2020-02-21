package com.omniointeractive.phoenyx.api.addon;

import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.item.ItemRegister;
import com.omniointeractive.phoenyx.api.util.messaging.Messenger;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an addon plugin for Phoenyx. The addon .jar file should be placed in the 'plugins' directory of the server
 * (as it's still technically a JavaPlugin).
 */
public abstract class Addon extends JavaPlugin {

    private ItemRegister itemRegister;
    private Messenger messenger;

    /**
     * Returns the {@link ItemRegister} for Phoenyx. With this instance, custom
     * {@link com.omniointeractive.phoenyx.api.item.Item}s can be registered for this addon.
     *
     * @return The {@link ItemRegister} instance.
     */
    private ItemRegister getItemRegister() {
        return this.itemRegister;
    }

    /**
     * Registers {@link Item}s with Phoenyx.
     *
     * @param items The {@link Item}s to register.
     */
    protected void registerItems(@NotNull final Item... items) {
        this.itemRegister.registerItems(this, items);
    }

    /**
     * Returns the {@link Messenger} for this addon. With this instance,
     * {@link com.omniointeractive.phoenyx.api.util.messaging.Message}s can be created and sent to players (with
     * consistent formatting across Phoenyx).
     *
     * @return The {@link Messenger} instance.
     */
    protected Messenger getMessenger() {
        return this.messenger;
    }

    /**
     * Returns the Bukkit Material of the icon for this {@link Addon}.
     *
     * @return The icon for this {@link Addon}.
     */
    public final Material getAddonIcon() {
        AddonInfo info = this.getClass().getAnnotation(AddonInfo.class);
        if (info != null) {
            return info.icon();
        }
        return Material.CAMPFIRE;
    }

    /**
     * Returns the name of this {@link Addon}.
     *
     * @return The {@link Addon}'s name.
     */
    public final String getAddonName() {
        AddonInfo info = this.getClass().getAnnotation(AddonInfo.class);
        if (info != null && !info.name().isEmpty()) {
            return info.name();
        }
        return this.getName();
    }

    /**
     * Returns the version of this {@link Addon}.
     *
     * @return The {@link Addon}'s version.
     */
    public final String getAddonVersion() {
        AddonInfo info = this.getClass().getAnnotation(AddonInfo.class);
        if (info != null && !info.version().isEmpty()) {
            return info.version();
        }
        return this.getDescription().getVersion();
    }

    /**
     * Returns the description of this {@link Addon}.
     *
     * @return The {@link Addon}'s description.
     */
    public final String getAddonDescription() {
        AddonInfo info = this.getClass().getAnnotation(AddonInfo.class);
        if (info != null && !info.description().isEmpty()) {
            return info.description();
        }
        return this.getDescription().getDescription();
    }

    /**
     * Returns the unique identifier of this {@link Addon}. This identifier is used for identification purposes and as
     * a namespace for content registered by this {@link Addon}.
     *
     * @return The {@link Addon}'s identifier.
     */
    public final String getAddonID() {
        return this.getAddonName().toLowerCase();
    }
}

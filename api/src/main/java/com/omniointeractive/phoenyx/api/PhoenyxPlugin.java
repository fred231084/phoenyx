package com.omniointeractive.phoenyx.api;

import com.omniointeractive.phoenyx.api.addon.AddonManager;
import com.omniointeractive.phoenyx.api.item.ItemRegister;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Abstract class for Phoenyx's main plugin class.
 */
public abstract class PhoenyxPlugin extends JavaPlugin {

    /**
     * Returns the global {@link AddonManager} used by Phoenyx.
     *
     * @return The {@link AddonManager} instance for Phoenyx.
     */
    public abstract AddonManager getAddonManager();

    /**
     * Returns the global {@link ItemRegister} used by Phoenyx.
     *
     * @return The {@link ItemRegister} instance for Phoenyx.
     */
    public abstract ItemRegister getItemRegister();

}

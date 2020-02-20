package com.omniointeractive.phoenyx.addon;

import com.omniointeractive.phoenyx.Phoenyx;
import com.omniointeractive.phoenyx.api.addon.Addon;
import com.omniointeractive.phoenyx.api.addon.AddonManager;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implementation of {@link AddonManager} using Bukkit's PluginManager system.
 */
public class PluginAddonManager implements AddonManager {

    private final Phoenyx phoenyx;
    private final List<Addon> addons;

    /**
     * Initialises the manager.
     *
     * @param phoenyx The parent plugin's main class instance.
     */
    public PluginAddonManager(@NotNull final Phoenyx phoenyx) {
        this.phoenyx = phoenyx;
        this.addons = new ArrayList<>();
    }

    /**
     * Returns all {@link Addon}s currently registered (and enabled) with this manager.
     *
     * @return The stream of registered and enabled {@link Addon}s.
     */
    @Override
    public Stream<Addon> getAddons() {
        return this.addons.stream().filter(JavaPlugin::isEnabled);
    }

    /**
     * Loads an {@link Addon} from a .jar file.
     *
     * @param file The {@link Addon} JAR file to load.
     */
    @Override
    public void loadAddon(@NotNull final File file) {
        Plugin plugin;
        try {
            plugin = this.phoenyx.getServer().getPluginManager().loadPlugin(file);
        } catch (InvalidPluginException | InvalidDescriptionException exception) {
            this.phoenyx.getLogger().severe(String.format("Unable to load addon '%s'!", file.getName()));
            exception.printStackTrace();
            return;
        }

        if (plugin instanceof Addon) {
            Addon addon = (Addon) plugin;

            if (this.inject(addon)) {
                this.addons.add(addon);
                this.phoenyx.getLogger().info(String.format("Loaded addon %s v%s!", addon.getName(),
                        addon.getDescription().getVersion()));
            }
        } else {
            this.phoenyx.getLogger().severe(
                    String.format("'%s' is not an Addon! The main plugin class must extend Addon, not JavaPlugin.",
                            file.getName()));
        }
    }

    /**
     * Loads all {@link Addon}s (.jar files) in a directory.
     *
     * @param directory The directory containing the {@link Addon} JAR files.
     */
    @Override
    public void loadAddons(@NotNull final File directory) {
        File[] addonFiles = directory.listFiles(file -> file.getName().toLowerCase().endsWith(".jar"));
        if (addonFiles != null) {
            for (File addonFile : addonFiles) {
                this.loadAddon(addonFile);
            }
        }
    }

    /**
     * Enables all currently loaded {@link Addon}s.
     */
    @Override
    public void enableAddons() {
        this.addons.forEach(addon -> this.phoenyx.getServer().getPluginManager().enablePlugin(addon));
    }

    private boolean inject(@NotNull final Addon addon) {
        try {
            Field itemRegisterField = Addon.class.getDeclaredField("itemRegister");
            itemRegisterField.setAccessible(true);
            itemRegisterField.set(addon, this.phoenyx.getItemRegister());
            itemRegisterField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            this.phoenyx.getLogger().severe(String.format("Unable to inject Phoenyx instances into addon '%s'!",
                    addon.getClass().getSimpleName()));
            return false;
        }
        return true;
    }
}

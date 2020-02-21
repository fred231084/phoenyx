package com.omniointeractive.phoenyx;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandCompletions;
import com.omniointeractive.phoenyx.addon.PluginAddonManager;
import com.omniointeractive.phoenyx.api.PhoenyxPlugin;
import com.omniointeractive.phoenyx.api.addon.Addon;
import com.omniointeractive.phoenyx.api.addon.AddonManager;
import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.item.ItemRegister;
import com.omniointeractive.phoenyx.command.PhoenyxCommand;
import com.omniointeractive.phoenyx.command.PhoenyxDebugCommand;
import com.omniointeractive.phoenyx.item.InMemoryItemRegister;
import com.omniointeractive.phoenyx.item.listener.PlaceListener;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main plugin class for Phoenyx. This class acts as the main entry point for Spigot into the Phoenyx.
 */
public class Phoenyx extends PhoenyxPlugin {

    private BukkitCommandManager commandManager;
    private AddonManager addonManager;
    private ItemRegister itemRegister;

    /**
     * Invoked when the server starts.
     */
    @Override
    public void onEnable() {
        this.addonManager = new PluginAddonManager(this);
        this.itemRegister = new InMemoryItemRegister();

        // Commands
        this.setupCommands();

        // Listeners
        this.setupListeners();

        // Addons
        File addonsDir = new File(this.getDataFolder(), "addons");
        addonsDir.mkdirs();
        this.addonManager.loadAddons(addonsDir);
        this.addonManager.enableAddons();
    }

    /**
     * Returns the global {@link AddonManager} for Phoenyx.
     *
     * @return Phoenyx's global {@link AddonManager}.
     */
    @Override
    public AddonManager getAddonManager() {
        return this.addonManager;
    }

    /**
     * Returns the global {@link ItemRegister} for Phoenyx.
     *
     * @return Phoenyx's global {@link ItemRegister}.
     */
    @Override
    public ItemRegister getItemRegister() {
        return this.itemRegister;
    }

    /**
     * Configures the command framework (provided by co.aikar.commands) for Phoenyx.
     */
    private void setupCommands() {
        this.commandManager = new BukkitCommandManager(this);
        this.registerCommandCompletions();
        this.registerCommands();
    }

    /**
     * Registers the command tab completions for Phoenyx's commands.
     */
    private void registerCommandCompletions() {
        CommandCompletions<?> commandCompletions = this.commandManager.getCommandCompletions();
        commandCompletions.registerAsyncCompletion("items", c -> Stream.concat(
                Stream.of(Material.values()).filter(Material::isInteractable)
                        .map(material -> String.format("minecraft:%s", material.name().toLowerCase())),
                this.itemRegister.getItems().map(Item::getId).sorted())
                .collect(Collectors.toList()));
        commandCompletions.registerAsyncCompletion("addons", c -> this.addonManager.getAddons()
                .map(Addon::getAddonID).collect(Collectors.toList()));
    }

    /**
     * Registers Phoenyx's commands.
     */
    private void registerCommands() {
        this.commandManager.registerCommand(new PhoenyxCommand(this));
        this.commandManager.registerCommand(new PhoenyxDebugCommand(this));
    }

    /**
     * Configures the event listeners for Phoenyx.
     */
    private void setupListeners() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlaceListener(this), this);
    }
}

package com.omniointeractive.phoenyx;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandCompletions;
import com.google.gson.JsonParser;
import com.omniointeractive.phoenyx.api.item.ItemRegister;
import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.item.JsonItem;
import com.omniointeractive.phoenyx.command.PhoenyxCommand;
import com.omniointeractive.phoenyx.command.PhoenyxDebugCommand;
import com.omniointeractive.phoenyx.item.InMemoryItemRegister;
import com.omniointeractive.phoenyx.item.listener.NonPlaceableListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Main plugin class for Phoenyx. This class acts as the main entry point for Spigot into the Phoenyx.
 */
public class PhoenyxPlugin extends JavaPlugin {

    private BukkitCommandManager commandManager;
    private ItemRegister itemRegister;

    /**
     * Invoked when the server starts.
     */
    @Override
    public void onEnable() {
        this.itemRegister = new InMemoryItemRegister();
        this.itemRegister.registerItems(JsonItem.parse(new JsonParser().parse(new InputStreamReader(Objects.requireNonNull(this.getResource("items/test.json"))))));

        // Commands
        this.setupCommands();

        // Listeners
        this.setupListeners();
    }

    /**
     * Returns the global {@link ItemRegister} for Phoenyx.
     *
     * @return Phoenyx's global {@link ItemRegister}.
     */
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
        commandCompletions.registerCompletion("item_ids", c -> this.itemRegister.getItems().map(Item::getId)
                .collect(Collectors.toList()));
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
        pm.registerEvents(new NonPlaceableListener(this), this);
    }
}

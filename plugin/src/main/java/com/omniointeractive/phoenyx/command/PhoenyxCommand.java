package com.omniointeractive.phoenyx.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.omniointeractive.phoenyx.PhoenyxPlugin;
import com.omniointeractive.phoenyx.api.item.Item;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * The main command for {@link PhoenyxPlugin}.
 */
@CommandAlias("phoenyx|phoenix|px")
@Description("The main command for Phoenyx.")
public class PhoenyxCommand extends BaseCommand {

    private final PhoenyxPlugin plugin;

    /**
     * Initialises the '/phoenyx' command.
     *
     * @param plugin The parent plugin's main class instance.
     */
    public PhoenyxCommand(PhoenyxPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * The '/phoenyx give' command allows for custom {@link Item}s to be spawned in and given to players.
     *
     * @param sender The sender of the command.
     * @param itemId The unique identifier of the {@link Item} to give.
     */
    @Subcommand("give")
    @CommandPermission("phoenyx.give")
    @CommandCompletion("@players @item_ids @range:1-64")
    @Description("Gives a custom Phoenyx item to a player.")
    public void give(Player sender, OnlinePlayer player, String itemId, @Default("1") int amount) {
        if (player.getPlayer() == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return;
        }

        Optional<Item> item = this.plugin.getItemRegister().getItem(itemId);
        if (item.isPresent()) {
            ItemStack itemStack = item.get().build();
            itemStack.setAmount(amount);
            player.getPlayer().getInventory().addItem(itemStack);
        } else {
            sender.sendMessage(ChatColor.RED + "No item was found with that ID!");
        }
    }
}

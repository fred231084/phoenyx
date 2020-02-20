package com.omniointeractive.phoenyx.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.omniointeractive.phoenyx.Phoenyx;
import com.omniointeractive.phoenyx.api.item.Item;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Debugging command for {@link Phoenyx}.
 */
@CommandAlias("phoenyxdebug|phoenixdebug|pxdebug|pxd")
@Description("Debugging command for Phoenyx.")
public class PhoenyxDebugCommand extends BaseCommand {

    private final Phoenyx phoenyx;

    /**
     * Initialises the '/phoenyx' debug command.
     *
     * @param phoenyx The parent plugin's main class instance.
     */
    public PhoenyxDebugCommand(@NotNull final Phoenyx phoenyx) {
        this.phoenyx = phoenyx;
    }

    /**
     * The '/phoenyxdebug hand' command allows players to gain information about the current item they're holding
     * (assuming it's a {@link Item}).
     *
     * @param sender The sender of the command.
     */
    @Subcommand("hand")
    @CommandPermission("phoenyxdebug.hand")
    @Description("Debugs the custom Phoenyx item held by the sender.")
    public void debugHand(Player sender) {
        ItemStack itemStack = sender.getInventory().getItemInMainHand();

        Optional<Item> optionalItem = this.phoenyx.getItemRegister().getItem(itemStack);
        if (optionalItem.isPresent()) {
            Item phoenyxItem = optionalItem.get();
            sender.sendMessage(String.format("ID: %s", phoenyxItem.getId()));
            sender.sendMessage(String.format("Name: %s", phoenyxItem.getName()));
            sender.sendMessage(String.format("Material: %s", phoenyxItem.getMaterial()));
            sender.sendMessage(String.format("Custom Model Data: %s", phoenyxItem.getCustomModel()));
            sender.sendMessage(String.format("Item Class: %s", phoenyxItem.getClass().getName()));
        } else {
            sender.sendMessage(ChatColor.RED + "The item you are currently holding is not a custom Phoenyx item!");
        }
    }
}

package com.omniointeractive.phoenyx.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.omniointeractive.phoenyx.Phoenyx;
import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.util.messaging.MessageStyle;
import com.omniointeractive.phoenyx.api.util.messaging.Messenger;
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
            Messenger m = Messenger.getPhoenyxMessenger();
            m.create(String.format("ID: %s", phoenyxItem.getId())).send(sender);
            m.create(String.format("Name: %s", phoenyxItem.getName())).send(sender);
            m.create(String.format("Material: %s", phoenyxItem.getMaterial())).send(sender);
            m.create(String.format("Custom Model Data: %s", phoenyxItem.getCustomModel() > -1 ? phoenyxItem.getCustomModel() : "None"))
                    .send(sender);
            m.create(String.format("Addon: %s", phoenyxItem.getAddon().getAddonName())).send(sender);
            m.create(String.format("Java Class: %s", phoenyxItem.getClass().getName())).send(sender);
        } else {
            Messenger.getPhoenyxMessenger().create("The item you're holding is not a custom item!", MessageStyle.ERROR)
                    .send(sender);
        }
    }
}

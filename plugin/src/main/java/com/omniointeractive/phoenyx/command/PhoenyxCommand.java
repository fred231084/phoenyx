package com.omniointeractive.phoenyx.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.omniointeractive.phoenyx.Phoenyx;
import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.util.messaging.MessageStyle;
import com.omniointeractive.phoenyx.api.util.messaging.Messenger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The main command for {@link Phoenyx}.
 */
@CommandAlias("phoenyx|phoenix|px")
@Description("The main command for Phoenyx.")
public class PhoenyxCommand extends BaseCommand {

    private final Phoenyx phoenyx;

    /**
     * Initialises the '/phoenyx' command.
     *
     * @param phoenyx The parent plugin's main class instance.
     */
    public PhoenyxCommand(@NotNull final Phoenyx phoenyx) {
        this.phoenyx = phoenyx;
    }

    /**
     * The '/phoenyx give' command allows for custom {@link Item}s to be spawned in and given to players.
     *
     * @param sender The sender of the command.
     * @param itemID The unique identifier of the {@link Item} to give.
     */
    @Subcommand("give")
    @CommandAlias("give")
    @CommandPermission("phoenyx.give")
    @CommandCompletion("@players @items @range:1-64")
    @Description("Gives a custom Phoenyx item to a player.")
    public void give(CommandSender sender, OnlinePlayer player, String itemID, @Default("1") int amount) {
        if (itemID.split(":")[0].equalsIgnoreCase("minecraft")) {
            this.phoenyx.getServer().dispatchCommand(sender,
                    String.format("minecraft:give %s %s %d", player.getPlayer().getName(), itemID, amount));
            return;
        }

        Optional<Item> item = this.phoenyx.getItemRegister().getItem(itemID);
        if (item.isPresent()) {
            ItemStack itemStack = item.get().build();
            itemStack.setAmount(amount);
            player.getPlayer().getInventory().addItem(itemStack);
            sender.spigot().sendMessage(new ComponentBuilder(String.format("Gave %d [%s] to %s", amount, item.get().getName(),
                    player.getPlayer().getName())).create());
        } else {
            Messenger.getPhoenyxMessenger().create(String.format("No item was found with ID '%s'!", itemID),
                    MessageStyle.ERROR).send(sender);
        }
    }

    /**
     * The '/phoenyx addons' command lists all Phoenyx {@link com.omniointeractive.phoenyx.api.addon.Addon}s currently
     * registered and enabled.
     *
     * @param sender The sender of the command.
     */
    @Subcommand("addons")
    @CommandPermission("phoenyx.addons")
    @Description("Lists all Phoenyx addons currently enabled.")
    public void listAddons(CommandSender sender) {
        ComponentBuilder builder = new ComponentBuilder("Addons: ");
        this.phoenyx.getAddonManager().getAddons().forEach(addon -> {
            builder.append(addon.getAddonName()).color(addon.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
            builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(addon.getAddonName())
                    .append(" (")
                    .append(addon.getAddonVersion()).color(ChatColor.GREEN)
                    .append(")").color(ChatColor.WHITE)
                    .append("\n")
                    .append(addon.getAddonDescription())
                    .create()));
            builder.append(", ");
        });
        builder.removeComponent(builder.getCursor());
        Messenger.getPhoenyxMessenger().create(builder.create()).send(sender);
    }
}

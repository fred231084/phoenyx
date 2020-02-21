package com.omniointeractive.phoenyx.item.crafting;

import com.omniointeractive.phoenyx.Phoenyx;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Listens for crafting-related events to implement the functionality of Phoenyx's
 * {@link com.omniointeractive.phoenyx.api.item.crafting.Recipe} API.
 */
public class RecipeListener implements Listener {

    private final Phoenyx phoenyx;

    /**
     * Instantiates the listener.
     *
     * @param phoenyx The parent plugin's main class instance.
     */

    public RecipeListener(@NotNull final Phoenyx phoenyx) {
        this.phoenyx = phoenyx;
    }

    /**
     * Invoked when a player clicks on a slot in an inventory window.
     *
     * @param event The inventory click event.
     */
    @EventHandler(ignoreCancelled = true)
    public void moveItem(InventoryClickEvent event) {
        if (event.getClickedInventory() instanceof CraftingInventory && event.getWhoClicked() instanceof Player) {
            CraftingInventory inventory = (CraftingInventory) event.getClickedInventory();
            if (event.getSlotType() == InventoryType.SlotType.RESULT
                    && this.phoenyx.getItemRegister().getItem(Objects.requireNonNull(event.getCurrentItem())).isPresent()) {
                event.setResult(Event.Result.DENY);

                Stream.of(inventory.getMatrix()).filter(Objects::nonNull)
                        .forEach(itemStack -> itemStack.setAmount(itemStack.getAmount() - 1));

                ItemStack result = event.getCurrentItem();
                inventory.setResult(null);
                event.getWhoClicked().getInventory().addItem(result);
            } else {
                this.check(inventory, (Player) event.getWhoClicked());
            }
        }
    }

    /**
     * Invoked when a player drags an ItemStack across slots in an inventory window
     *
     * @param event The inventory drag event.
     */
    @EventHandler(ignoreCancelled = true)
    public void dragItem(InventoryDragEvent event) {
        if (event.getInventory() instanceof CraftingInventory && event.getWhoClicked() instanceof Player) {
            this.check((CraftingInventory) event.getInventory(), (Player) event.getWhoClicked());
        }
    }

    /**
     * Checks a crafting inventory for any possible Phoenyx's
     * {@link com.omniointeractive.phoenyx.api.item.crafting.Recipe}'s. If one is found, the output of the crafting
     * inventory is updated accordingly.
     *
     * @param craftingInventory The crafting inventory to check for a
     *                          {@link com.omniointeractive.phoenyx.api.item.crafting.Recipe}.
     * @param viewer            The player viewing the crafting inventory.
     */
    private void check(@NotNull final CraftingInventory craftingInventory, @NotNull final Player viewer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                phoenyx.getRecipeRegister().getRecipe(craftingInventory).ifPresent(recipe -> {
                    craftingInventory.setResult(recipe.getResultAsItemStack());
                    viewer.updateInventory();
                });
            }
        }.runTaskLater(this.phoenyx, 1);
    }
}

package com.omniointeractive.phoenyx.api.item.crafting;

import com.omniointeractive.phoenyx.api.item.Item;
import org.bukkit.inventory.CraftingInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Tracks {@link Recipe}s for {@link Item}s registered by Phoenyx {@link com.omniointeractive.phoenyx.api.addon.Addon}s.
 */
public interface RecipeRegister {

    /**
     * Registers crafting {@link Recipe}s with this register.
     *
     * @param recipes The {@link Recipe}s to register.
     */
    void register(@NotNull final Recipe... recipes);

    /**
     * Returns the corresponding {@link Recipe} for a crafting (table) inventory.
     *
     * @param craftingInventory The crafting (table) inventory to check.
     * @return The {@link Recipe}, if found.
     */
    Optional<Recipe> getRecipe(@NotNull final CraftingInventory craftingInventory);

    /**
     * Returns the {@link Recipe} that results in the provided {@link Item}.
     *
     * @param item The {@link Item} to lookup the {@link Recipe} of.
     * @return The {@link Recipe} for the provided {@link Item}, if found
     */
    Optional<Recipe> getRecipe(@NotNull final Item item);
}

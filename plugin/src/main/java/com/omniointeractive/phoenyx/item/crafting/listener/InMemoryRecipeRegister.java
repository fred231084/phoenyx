package com.omniointeractive.phoenyx.item.crafting.listener;

import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.item.crafting.Recipe;
import com.omniointeractive.phoenyx.api.item.crafting.RecipeRegister;
import org.bukkit.inventory.CraftingInventory;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementation of {@link RecipeRegister} using an in-memory system for storing registered {@link Recipe}s.
 */
public class InMemoryRecipeRegister implements RecipeRegister {

    private final Map<Item, Recipe> recipes;

    /**
     * Initialises a new {@link Recipe} register.
     */
    public InMemoryRecipeRegister() {
        this.recipes = new LinkedHashMap<>();
    }

    /**
     * Registers crafting {@link Recipe}s with this register.
     *
     * @param recipes The {@link Recipe}s to register.
     */
    @Override
    public void register(@NotNull final Recipe... recipes) {
        Stream.of(recipes).forEach(recipe -> this.recipes.putIfAbsent(recipe.getResult(), recipe));
    }

    /**
     * Returns the corresponding {@link Recipe} for a crafting (table) inventory.
     *
     * @param craftingInventory The crafting (table) inventory to check.
     * @return The {@link Recipe}, if found.
     */
    @Override
    public Optional<Recipe> getRecipe(@NotNull final CraftingInventory craftingInventory) {
        return this.recipes.values().stream().filter(recipe -> recipe.validate(craftingInventory)).findFirst();
    }

    /**
     * Returns the {@link Recipe} that results in the provided {@link Item}.
     *
     * @param item The {@link Item} to lookup the {@link Recipe} of.
     * @return The {@link Recipe} for the provided {@link Item}, if found
     */
    @Override
    public Optional<Recipe> getRecipe(@NotNull final Item item) {
        return Optional.ofNullable(this.recipes.get(item));
    }
}

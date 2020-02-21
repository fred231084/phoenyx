package com.omniointeractive.phoenyx.api.item.crafting;

import com.omniointeractive.phoenyx.api.item.Item;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A crafting recipe for an {@link Item}.
 */
public abstract class Recipe {

    private final Item result;
    private int resultAmount = 1;

    /**
     * Initialises a new recipe.
     *
     * @param result The {@link Item} produced by this recipe.
     */
    protected Recipe(Item result) {
        this.result = result;
    }

    /**
     * Initialises a new recipe.
     *
     * @param result       The {@link Item} produced by this recipe.
     * @param resultAmount The amount of the result to produce.
     */
    protected Recipe(Item result, int resultAmount) {
        this.result = result;
        this.resultAmount = resultAmount;
    }

    /**
     * Returns the {@link Item} produced when this recipe is crafted.
     *
     * @return The recipe's result.
     */
    public Item getResult() {
        return this.result;
    }

    /**
     * Returns the amount/quantity of the {@link Item} produced by this recipe.
     *
     * @return The recipe's result amount.
     */
    public int getResultAmount() {
        return this.resultAmount;
    }

    /**
     * Returns the Minecraft ItemStack produced by this recipe.
     *
     * @return The recipe's result as an ItemStack.
     */
    public ItemStack getResultAsItemStack() {
        ItemStack itemStack = this.result.build();
        itemStack.setAmount(this.getResultAmount());
        return itemStack;
    }

    /**
     * Checks if a provided {@link CraftingInventory} matches this recipe.
     *
     * @param craftingInventory The {@link CraftingInventory} to check.
     * @return True if the {@link CraftingInventory} matches this recipe, otherwise false.
     */
    public abstract boolean validate(@NotNull final CraftingInventory craftingInventory);

    /**
     * Different types of recipes that can be defined.
     */
    public enum Type {

        /**
         * A {@link Recipe} that has a specific pattern/shape for its ingredients.
         */
        SHAPED,
        /**
         * A {@link Recipe} that does not require its ingredients to be arranged in any specific pattern/shape.
         */
        SHAPELESS;

    }
}

package com.omniointeractive.phoenyx.api.item.crafting;

import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.item.ItemRegister;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link Recipe} that has a specific ingredient order/shape.
 */
public class ShapedRecipe extends Recipe {

    private final ItemStack[] recipe;

    /**
     * Initialises a new shaped {@link Recipe}.
     *
     * @param itemRegister The {@link ItemRegister} to use when looking up ingredient item IDs.
     * @param recipeIds    The array of item ({@link Item} or vanilla) IDs this {@link Recipe} requires to craft.
     * @param result       The {@link Item} produced by this {@link Recipe}.
     */
    public ShapedRecipe(@NotNull final ItemRegister itemRegister, @NotNull final String[] recipeIds, @NotNull final Item result) {
        super(result);
        this.recipe = new ItemStack[recipeIds.length];
        for (int i = 0; i < recipeIds.length; i++) {
            final String id = recipeIds[i];
            if (id.trim().isEmpty()) {
                this.recipe[i] = null;
            } else if (id.startsWith("minecraft:")) {
                // Vanilla Minecraft ingredient
                this.recipe[i] = new ItemStack(Objects.requireNonNull(Material.getMaterial(id.replaceAll("minecraft:", "").toUpperCase())));
            } else {
                // Phoenyx ingredient
                final Item ingredient = itemRegister.getItem(id).orElse(null);
                this.recipe[i] = Objects.requireNonNull(ingredient).build();
            }
        }
    }

    /**
     * Checks if a provided {@link CraftingInventory} matches this recipe.
     *
     * @param craftingInventory The {@link CraftingInventory} to check.
     * @return True if the {@link CraftingInventory} matches this recipe, otherwise false.
     */
    @Override
    public boolean validate(@NotNull final CraftingInventory craftingInventory) {
        for (int i = 0; i < 9; i++) {
            ItemStack slot = craftingInventory.getMatrix()[i];
            final ItemStack ingredient = this.recipe[i];
            if (slot == null && ingredient == null) {
                continue;
            }
            if (slot == null || ingredient == null) {
                return false;
            }
            slot = slot.clone();
            slot.setAmount(1);
            if (!ingredient.equals(slot)) {
                return false;
            }
        }
        return true;
    }
}

package com.omniointeractive.phoenyx.api.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Responsible for handling the registration and state of {@link Item}s.
 */
public interface ItemRegister {

    /**
     * Returns all {@link Item}s currently registered with this register.
     *
     * @return The stream of registered {@link Item}s.
     */
    Stream<Item> getItems();

    /**
     * Attempts to retrieve an {@link Item} from this register by its unique identifier.
     *
     * @param id The identifier of the {@link Item} to lookup.
     * @return The {@link Item} corresponding to the identifier if found.
     */
    Optional<Item> getItem(@NotNull final String id);

    /**
     * Attempts to retrieve an {@link Item} from this register from a provided Minecraft ItemStack.
     *
     * @param itemStack The Minecraft ItemStack corresponding to the {@link Item} to lookup.
     * @return The {@link Item} corresponding to the ItemStack if found.
     */
    Optional<Item> getItem(@NotNull final ItemStack itemStack);

    /**
     * Registers {@link Item}s with this register.
     *
     * @param items The {@link Item}s to register.
     */
    void registerItems(@NotNull final Item... items);
}

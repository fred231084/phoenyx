package com.omniointeractive.phoenyx.item;

import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.item.ItemRegister;
import com.omniointeractive.phoenyx.api.util.ColorCodeCrypto;
import org.apache.commons.codec.DecoderException;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link ItemRegister} using an in-memory system for storing registered items (LinkedHashMap).
 */
public class InMemoryItemRegister implements ItemRegister {

    private final Map<String, Item> items;

    /**
     * Instantiates the register for {@link Item}s.
     */
    public InMemoryItemRegister() {
        this.items = new LinkedHashMap<>();
    }

    /**
     * Returns all {@link Item}s (and their identifiers as the map's key) currently registered with this register.
     *
     * @return The map of registered {@link Item}s and their identifiers.
     */
    public Map<String, Item> getItemMap() {
        return this.items;
    }

    /**
     * Returns all {@link Item}s currently registered with this register.
     *
     * @return The stream of registered {@link Item}s.
     */
    @Override
    public Stream<Item> getItems() {
        return this.items.values().stream();
    }

    /**
     * Attempts to retrieve an {@link Item} from this register by its unique identifier.
     *
     * @param id The identifier of the {@link Item} to lookup.
     * @return The {@link Item} corresponding to the identifier if found.
     */
    @Override
    public Optional<Item> getItem(@NotNull final String id) {
        return Optional.ofNullable(this.getItemMap().get(id));
    }

    /**
     * Attempts to retrieve an {@link Item} from this register from a provided Minecraft ItemStack.
     *
     * @param itemStack The Minecraft ItemStack corresponding to the {@link Item} to lookup.
     * @return The {@link Item} corresponding to the ItemStack if found.
     */
    @Override
    public Optional<Item> getItem(@NotNull final ItemStack itemStack) {
        if (itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null
                && itemStack.getItemMeta().getLore().size() > 0) {
            String encodedLore = itemStack.getItemMeta().getLore().get(0);
            String encodedId = encodedLore.replaceAll(String.format("%sPhoenix Item", ChatColor.DARK_PURPLE), "");
            try {
                String id = ColorCodeCrypto.decode(encodedId);
                return this.getItem(id);
            } catch (DecoderException exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * Registers {@link Item}s with this register.
     *
     * @param items The {@link Item}s to register.
     */
    public void registerItems(@NotNull final Item... items) {
        this.items.putAll(Stream.of(items).collect(Collectors.toMap(Item::getId, Function.identity())));
    }
}

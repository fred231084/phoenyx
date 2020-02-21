package com.omniointeractive.phoenyx.item;

import com.omniointeractive.phoenyx.Phoenyx;
import com.omniointeractive.phoenyx.api.addon.Addon;
import com.omniointeractive.phoenyx.api.item.Item;
import com.omniointeractive.phoenyx.api.item.ItemRegister;
import com.omniointeractive.phoenyx.api.item.JsonItem;
import com.omniointeractive.phoenyx.api.item.crafting.Recipe;
import com.omniointeractive.phoenyx.api.item.crafting.ShapedRecipe;
import com.omniointeractive.phoenyx.api.item.interfaces.Craftable;
import com.omniointeractive.phoenyx.api.util.ColorCodeCrypto;
import org.apache.commons.codec.DecoderException;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementation of {@link ItemRegister} using an in-memory system for storing registered {@link Item}s.
 */
public class InMemoryItemRegister implements ItemRegister {

    private final Phoenyx phoenyx;
    private final Map<String, Item> items;

    /**
     * Instantiates the register for {@link Item}s.
     *
     * @param phoenyx The parent plugin's main class instance.
     */
    public InMemoryItemRegister(@NotNull final Phoenyx phoenyx) {
        this.phoenyx = phoenyx;
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
                Optional<Item> item = this.getItem(id);
                if (item.isPresent() && item.get().validate(itemStack)) {
                    return item;
                }
            } catch (DecoderException exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * Registers {@link Item}s with this register.
     *
     * @param parent The parent {@link Addon} of the items.
     * @param items  The {@link Item}s to register.
     */
    @Override
    public void registerItems(@NotNull final Addon parent, @NotNull final Item... items) {
        for (Item item : items) {
            try {
                this.setField(item, "addon", parent);
                this.setField(item, "id", String.format("%s:%s", parent.getAddonID(), item.getId()));
                this.invoke(item, "generateEncodedId");
                this.invoke(item, "generateEncodedLoreLine");
                this.items.put(item.getId(), item);
            } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException |
                    InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void setField(@NotNull final Item item, @NotNull final String field, Object object)
            throws NoSuchFieldException, IllegalAccessException {
        Field itemRegisterField = Item.class.getDeclaredField(field);
        itemRegisterField.setAccessible(true);
        itemRegisterField.set(item, object);
        itemRegisterField.setAccessible(false);
    }

    private void invoke(@NotNull final Item item, @NotNull final String methodName) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Method method = Item.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(item);
        method.setAccessible(false);
    }

    /**
     * Registers all {@link com.omniointeractive.phoenyx.api.item.crafting.Recipe}s provided by {@link Item}s in this
     * register.
     */
    @Override
    public void registerRecipes() {
        for (Item item : this.items.values()) {
            Recipe recipe = null;

            if (item instanceof JsonItem) {
                // TODO: Recipes defined in JSON
            } else if (item.getClass().isAnnotationPresent(Craftable.class)) {
                Craftable craftable = item.getClass().getAnnotation(Craftable.class);
                if (craftable.type() == Recipe.Type.SHAPED) {
                    recipe = new ShapedRecipe(this.phoenyx.getItemRegister(), craftable.ingredients(), item);
                } else if (craftable.type() == Recipe.Type.SHAPELESS) {
                    // TODO: Shapeless recipes
                }
            }

            if (recipe != null) {
                phoenyx.getRecipeRegister().register(recipe);
            }
        }
    }
}

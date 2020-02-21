package com.omniointeractive.phoenyx.api.item;

import com.omniointeractive.phoenyx.api.addon.Addon;
import com.omniointeractive.phoenyx.api.item.interfaces.Placeable;
import org.apache.commons.codec.binary.Hex;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Represents a custom item created by Phoenyx (or a Phoenyx addon).
 *
 * @author Luke Carr
 * @since 0.1.0-rc.1
 */
public abstract class Item {

    private Addon addon;

    private final String id, name;
    private final Material material;
    private int customModel = -1;

    private String encodedId, encodedLoreLine;

    /**
     * Creates a new item instance with no custom model data.
     *
     * @param id       The unique identifier of the item.
     * @param name     The 'pretty' display name for the item. This value can contain '&' Minecraft color codes that
     *                 will be converted.
     * @param material The Bukkit material that will visually represent this item.
     */
    protected Item(@NotNull final String id, @NotNull final String name, @NotNull final Material material) {
        this.id = id;
        this.name = ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', name);
        this.material = material;
        this.generateEncodedId();
        this.generateEncodedLoreLine();
    }

    /**
     * Returns the parent {@link Addon} that 'owns' this item.
     *
     * @return The item's {@link Addon}.
     */
    public Addon getAddon() {
        return this.addon;
    }

    /**
     * Returns the unique identifier for this item. This identifier is used when spawning the item directly with
     * '/phoenyx give'.
     *
     * @return The item's identifier.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the unique identifier for this item, encoded with Minecraft's chat color formatting codes.
     *
     * @return The item's encoded identifier.
     */
    public String getEncodedId() {
        return this.encodedId;
    }

    /**
     * Returns the 'pretty' name for this item. This value is used as the display name of any Bukkit ItemStacks for
     * this item.
     *
     * @return The item's pretty name.
     */
    @NotNull
    public String getName() {
        return this.name;
    }

    /**
     * Returns the Bukkit material visually representing this item.
     *
     * @return The item's material.
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Returns the custom model data value for this item. This value is used by resource packs for Minecraft 1.14+ to
     * display a custom texture for this item.
     *
     * @return The item's custom model value.
     */
    public int getCustomModel() {
        return this.customModel;
    }

    /**
     * Sets the custom model data value for this item.
     *
     * @param customModel THe item's new custom model value.
     */
    public void setCustomModel(int customModel) {
        this.customModel = customModel;
    }

    /**
     * Checks if this {@link Item} can be placed in the world.
     *
     * @return True if this {@link Item} can be placed, otherwise false.
     */
    public boolean isPlaceable() {
        return this instanceof Placeable;
    }

    /**
     * Returns this item as a Bukkit ItemStack that can be interpreted by the server (given to players and entities,
     * have enchantments applied to it, etc).
     *
     * @return The item as an ItemStack.
     */
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(this.getMaterial());

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return new ItemStack(Material.AIR);
        }
        meta.setDisplayName(this.getName());
        meta.setLore(Collections.singletonList(this.encodedLoreLine));

        if (this.getCustomModel() > -1) {
            meta.setCustomModelData(this.getCustomModel());
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Checks if a specified ItemStack is an instance of this custom item.
     *
     * @param itemStack The ItemStack to check.
     * @return True if the given ItemStack is an instance of this item, otherwise false.
     */
    public boolean validate(@NotNull final ItemStack itemStack) {
        return itemStack.getType() == this.getMaterial() && itemStack.getItemMeta() != null
                && itemStack.getItemMeta().getLore() != null
                && itemStack.getItemMeta().getLore().get(0).equals(this.encodedLoreLine);
    }

    /**
     * Generates the encoded identifier for this item using Minecraft's chat color formatting codes.
     */
    protected void generateEncodedId() {
        final String idHex = Hex.encodeHexString(this.id.getBytes(StandardCharsets.UTF_8));
        final StringBuilder encodedIdBuilder = new StringBuilder();
        for (final char c : idHex.toCharArray()) {
            encodedIdBuilder.append(String.format("&%s", c));
        }
        this.encodedId = ChatColor.translateAlternateColorCodes('&', encodedIdBuilder.toString());
    }

    /**
     * Generates the encoded lore line for this item.
     */
    protected void generateEncodedLoreLine() {
        this.encodedLoreLine = String.format("%s%sPhoenix Item", this.getEncodedId(), ChatColor.DARK_PURPLE);
    }
}

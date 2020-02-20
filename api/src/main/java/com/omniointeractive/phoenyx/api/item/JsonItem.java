package com.omniointeractive.phoenyx.api.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents an {@link Item} defined using JSON with simple functionality.
 */
public class JsonItem extends Item {

    private boolean placeable = true;

    /**
     * Initialises a new {@link JsonItem}.
     *
     * @param json The JSON object to decode into an {@link Item}.
     * @throws IllegalArgumentException If the provided JSON contains invalid values.
     */
    public JsonItem(@NotNull final JsonObject json) {
        super(json.get("id").getAsString(),
                json.get("name").getAsString(),
                loadNullable("material", Material.getMaterial(json.get("material").getAsString())));
        if (json.has("customModel")) {
            super.setCustomModel(json.get("customModel").getAsInt());
        }
        if (json.has("placeable")) {
            this.placeable = json.get("placeable").getAsBoolean();
        }
    }

    /**
     * Checks if this {@link JsonItem} can be placed in the world.
     *
     * @return True if this {@link JsonItem} can be placed, otherwise false.
     */
    @Override
    public boolean isPlaceable() {
        return this.placeable;
    }

    private static <T> T loadNullable(@NotNull final String name, final T t) {
        return Optional.ofNullable(t)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Provided JSON does not contain a valid %s value!", name)));
    }

    /**
     * Initialises multiple {@link JsonItem}s from an array of JSON objects.
     *
     * @param json The JSON array to parse {@link JsonItem}s from.
     * @return The array of {@link JsonItem}s parsed from the provided JSON array.
     */
    public static JsonItem[] parseArray(@NotNull final JsonArray json) {
        List<JsonItem> items = new ArrayList<>();
        json.forEach(element -> {
            if (element instanceof JsonObject) {
                JsonObject object = (JsonObject) element;
                items.add(new JsonItem(object));
            }
        });
        return items.toArray(new JsonItem[0]);
    }

    /**
     * Parses a provided JSON source and initialises any {@link JsonItem}s found inside.
     *
     * @param json The JSON to parse for {@link JsonItem}s.
     * @return The array of {@link JsonItem}s parsed from the provided JSON.
     * @throws IllegalArgumentException If the provided JSON is not valid.
     */
    public static JsonItem[] parse(@NotNull final JsonElement json) {
        if (json instanceof JsonObject) {
            return new JsonItem[]{new JsonItem((JsonObject) json)};
        } else if (json instanceof JsonArray) {
            return JsonItem.parseArray((JsonArray) json);
        } else {
            throw new IllegalArgumentException("Invalid JSON provided! Please provide an array of objects or one object.");
        }
    }
}

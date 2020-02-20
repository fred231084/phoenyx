package com.omniointeractive.phoenyx.api.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

/**
 * Encodes/decodes a string using Minecraft's chat formatting codes (which are not rendered when attached to e.g. an
 * item stack's lore/name).
 *
 * @author Luke Carr
 * @since 0.1.0-rc.1
 */
public class ColorCodeCrypto {

    /**
     * Encodes a string value using Minecraft's chat formatting codes.
     *
     * @param value The string value to encode.
     * @return The encoded string.
     */
    public static String encode(@NotNull String value) {
        final String hex = Hex.encodeHexString(value.getBytes(StandardCharsets.UTF_8));
        final StringBuilder builder = new StringBuilder();
        for (final char c : hex.toCharArray()) {
            builder.append('&');
            builder.append(c);
        }
        return ChatColor.translateAlternateColorCodes('&', builder.toString());
    }

    /**
     * Decodes a string that has been encoded using the {@link ColorCodeCrypto#encode} function.
     *
     * @param value The encoded string to be decoded.
     * @return The decoded string value.
     */
    public static String decode(@NotNull String value) throws DecoderException {
        final String hex = value.replaceAll("\u00a7", "");
        return new String(Hex.decodeHex(hex), StandardCharsets.UTF_8);
    }
}

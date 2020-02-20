package com.omniointeractive.phoenyx.api.addon;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides additional metadata for an {@link Addon} for Phoenyx.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AddonInfo {

    /**
     * Defines the Bukkit Material of the icon for this {@link Addon}. If no icon is provided then Material.CAMPFIRE is
     * used by default.
     */
    Material icon() default Material.CAMPFIRE;

    /**
     * Overrides the name of this {@link Addon}. By default, the name defined in the {@link Addon}'s Bukkit plugin.yml
     * is used.
     */
    String name() default "";

    /**
     * Overrides the version of this {@link Addon}. By default, the version defined in the {@link Addon}'s Bukkit
     * plugin.yml is used.
     */
    String version() default "";

    /**
     * Overrides the description of this {@link Addon}. By default, the description defined in the {@link Addon}'s
     * Bukkit plugin.yml is used.
     */
    String description() default "";

}

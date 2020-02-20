package com.omniointeractive.phoenyx.api.item.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate an {@link com.omniointeractive.phoenyx.api.item.Item} that cannot be placed in the world.
 * This is useful when an {@link com.omniointeractive.phoenyx.api.item.Item}'s material type is a block that can be
 * naturally placed in Minecraft, or an item that can be placed such as a bucket of water.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NonPlaceable {
}

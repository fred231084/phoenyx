package com.omniointeractive.phoenyx.api.item.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate that an {@link com.omniointeractive.phoenyx.api.item.Item} can be placed in the world.
 * By default any custom {@link com.omniointeractive.phoenyx.api.item.Item} cannot be placed in the world, overriding
 * vanilla behavior.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Placeable {
}

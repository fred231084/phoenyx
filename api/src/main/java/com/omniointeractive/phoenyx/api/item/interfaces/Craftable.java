package com.omniointeractive.phoenyx.api.item.interfaces;

import com.omniointeractive.phoenyx.api.item.crafting.Recipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Craftable {

    String[] ingredients();

    Recipe.Type type();
}

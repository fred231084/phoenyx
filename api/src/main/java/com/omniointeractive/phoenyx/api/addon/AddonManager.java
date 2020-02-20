package com.omniointeractive.phoenyx.api.addon;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.stream.Stream;

/**
 * Responsible for managing {@link Addon}s registered with {@link com.omniointeractive.phoenyx.api.PhoenyxPlugin}.
 */
public interface AddonManager {

    /**
     * Returns all {@link Addon}s currently registered (and enabled) with this manager.
     *
     * @return The stream of registered and enabled {@link Addon}s.
     */
    Stream<Addon> getAddons();

    /**
     * Loads an {@link Addon} from a .jar file.
     *
     * @param file The {@link Addon} JAR file to load.
     */
    void loadAddon(@NotNull final File file);

    /**
     * Loads all {@link Addon}s (.jar files) in a directory.
     *
     * @param directory The directory containing the {@link Addon} JAR files.
     */
    void loadAddons(@NotNull final File directory);

    /**
     * Enables all currently loaded {@link Addon}s.
     */
    void enableAddons();
}

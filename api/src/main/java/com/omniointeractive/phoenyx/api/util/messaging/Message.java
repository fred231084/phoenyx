package com.omniointeractive.phoenyx.api.util.messaging;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Represents a message that is to be sent to a player (or players).
 */
public interface Message {

    /**
     * Sends this message to a player or players.
     *
     * @param recipients The players to send this message to.
     */
    void send(@NotNull final CommandSender... recipients);

    /**
     * Sends this message to all players online matching a condition.
     *
     * @param playerPredicate The condition players must meet to be sent this message.
     */
    default void send(@NotNull final Predicate<Player> playerPredicate) {
        this.send(playerPredicate, Bukkit.getOnlinePlayers());
    }

    /**
     * Sends this message to all players in the provided collection that match a condition.
     *
     * @param playerPredicate The condition players in the collection must meet to be sent this message.
     * @param players         The collection of possible players to send this message to.
     */
    default void send(@NotNull final Predicate<Player> playerPredicate, @NotNull final Collection<? extends Player> players) {
        this.send(players.stream().filter(playerPredicate).toArray(Player[]::new));
    }
}

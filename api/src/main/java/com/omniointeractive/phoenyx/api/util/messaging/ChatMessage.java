package com.omniointeractive.phoenyx.api.util.messaging;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * A {@link Message} that is sent to players in the chat window.
 */
public class ChatMessage implements Message {

    private final BaseComponent[] message;

    /**
     * Initialises a new chat message instance.
     *
     * @param message The contents of the chat message.
     */
    protected ChatMessage(@NotNull final BaseComponent[] message) {
        this.message = message;
    }

    /**
     * Sends this chat message to a collection of players.
     *
     * @param recipients The players to send this chat message to.
     */
    @Override
    public void send(@NotNull final CommandSender... recipients) {
        Stream.of(recipients).forEach(recipient -> recipient.spigot().sendMessage(this.message));
    }
}

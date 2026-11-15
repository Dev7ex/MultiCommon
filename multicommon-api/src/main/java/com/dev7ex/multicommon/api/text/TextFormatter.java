package com.dev7ex.multicommon.api.text;

import com.dev7ex.multicommon.api.MultiCommon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * Utility class for parsing MiniMessage, legacy formatted and plain text messages.
 *
 * @author Dev7ex
 * @since 2025-04-12
 */
public final class TextFormatter {

    private TextFormatter() {
        throw new UnsupportedOperationException("Utility class");
    }

    @NotNull
    public static Component parse(@NotNull final String input) {
        return TextFormatter.parse(input, Map.of());
    }

    @NotNull
    public static Component parse(@NotNull final String input, @NotNull final Map<String, String> placeholders) {
        Objects.requireNonNull(input, "Input cannot be null.");
        Objects.requireNonNull(placeholders, "Placeholders cannot be null.");

        final String message = TextFormatter.replaceLegacyPlaceholders(input, placeholders);

        if (message.contains("§")) {
            return MultiCommon.getLegacyComponentSerializer().deserialize(message);
        }

        if (message.contains("<") && message.contains(">")) {
            return MultiCommon.getMiniMessage().deserialize(message, TextFormatter.toTagResolver(placeholders));
        }

        return Component.text(message);
    }

    @NotNull
    public static Component parseWithPlaceholders(@NotNull final String input, @NotNull final Map<String, String> placeholders) {
        return TextFormatter.parse(input, placeholders);
    }

    @NotNull
    public static TagResolver toTagResolver(@NotNull final Map<String, String> placeholders) {
        final TagResolver.Builder builder = TagResolver.builder();

        for (final Map.Entry<String, String> entry : placeholders.entrySet()) {
            builder.resolver(Placeholder.unparsed(entry.getKey(), entry.getValue()));
        }

        return builder.build();
    }

    @NotNull
    public static String replaceLegacyPlaceholders(@NotNull final String input, @NotNull final Map<String, String> placeholders) {
        String result = input;

        for (final Map.Entry<String, String> entry : placeholders.entrySet()) {
            result = result.replace("%" + entry.getKey() + "%", entry.getValue());
        }

        return result;
    }

}

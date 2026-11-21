package com.dev7ex.multicommon.api.text;

import com.dev7ex.multicommon.api.MultiCommon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author MGG
 * @since 21.11.2026
 */
class MiniMessageFormatter {

    private MiniMessageFormatter() {
        throw new UnsupportedOperationException("Utility class");
    }

    @NotNull
    static Component deserialize(@NotNull final String message, @NotNull final Map<String, String> placeholders) {
        return MultiCommon.getMiniMessage().deserialize(message, MiniMessageFormatter.toTagResolver(placeholders));
    }

    @NotNull
    static TagResolver toTagResolver(@NotNull final Map<String, String> placeholders) {
        final TagResolver.Builder builder = TagResolver.builder();

        for (final Map.Entry<String, String> entry : placeholders.entrySet()) {
            builder.resolver(Placeholder.unparsed(entry.getKey(), entry.getValue()));
        }
        return builder.build();
    }

}

package com.dev7ex.multicommon.api.text;

import com.dev7ex.multicommon.api.MultiCommon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Utility class for parsing MiniMessage, legacy formatted and plain text messages.
 *
 * @author Dev7ex
 * @since 2025-04-12
 */
public final class TextFormatter {

    private static final boolean MINI_MESSAGE_AVAILABLE = MultiCommon.isMiniMessageAvailable();
    private static final Pattern TAG_PATTERN = Pattern.compile("</?(?:#[0-9a-fA-F]{6}|black|dark_[a-z_]+|gray|grey|white|red|gold|yellow|green|aqua|blue|light_purple|color|colour|bold|b|italic|i|em|underlined|u|strikethrough|st|obfuscated|obf|reset|gradient|rainbow|transition|click|hover|insert|font|newline|br|key|lang|translate|selector|score|nbt|pride|shadow)(?::[^<>]*)?>");

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

        if ((message.contains("<")) && (message.contains(">"))) {
            return (TextFormatter.MINI_MESSAGE_AVAILABLE)
                    ? MiniMessageFormatter.deserialize(message, placeholders)
                    : Component.text(TextFormatter.TAG_PATTERN.matcher(message).replaceAll(""));
        }

        return Component.text(message);
    }

    @NotNull
    public static Component parseWithPlaceholders(@NotNull final String input, @NotNull final Map<String, String> placeholders) {
        return TextFormatter.parse(input, placeholders);
    }

    @NotNull
    public static TagResolver toTagResolver(@NotNull final Map<String, String> placeholders) {
        return MiniMessageFormatter.toTagResolver(placeholders);
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

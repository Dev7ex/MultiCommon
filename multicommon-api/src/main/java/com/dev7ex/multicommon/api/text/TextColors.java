
package com.dev7ex.multicommon.api.text;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Dev7ex
 * @since 27.07.2025
 */
public final class TextColors {

    private TextColors() {
        throw new UnsupportedOperationException("Utility class");
    }

    @NotNull
    public static TextColor parse(@Nullable final String hexColor) {
        return TextColors.parse(hexColor, NamedTextColor.GRAY);
    }

    @NotNull
    public static TextColor parse(@Nullable final String hexColor, @NotNull final TextColor defaultColor) {
        if (hexColor == null || hexColor.isBlank()) {
            return defaultColor;
        }

        final TextColor color = TextColor.fromHexString(hexColor);
        return color != null ? color : defaultColor;
    }

    @NotNull
    public static TextColor fromRGB(final int red, final int green, final int blue) {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException("RGB values must be between 0 and 255.");
        }

        return TextColor.color(red, green, blue);
    }

    @NotNull
    public static String toHex(@NotNull final TextColor color) {
        return color.asHexString();
    }

    public static boolean isValidHex(@Nullable final String hexColor) {
        return hexColor != null && TextColor.fromHexString(hexColor) != null;
    }

    @NotNull
    public static TextColor interpolate(@NotNull final TextColor start, @NotNull final TextColor end, final float progress) {
        return TextColor.lerp(Math.max(0.0F, Math.min(1.0F, progress)), start, end);
    }

}

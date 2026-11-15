
package com.dev7ex.multicommon.api.plugin.configuration;

import com.dev7ex.multicommon.api.configuration.Configuration;
import com.dev7ex.multicommon.api.configuration.ConfigurationHolder;
import com.dev7ex.multicommon.api.text.TextFormatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Dev7ex
 * @since 03.02.2025
 */
public abstract class DefaultPluginConfiguration extends Configuration implements PluginConfiguration {

    public DefaultPluginConfiguration(@NotNull final ConfigurationHolder configurationHolder) {
        super(configurationHolder);
    }

    @Override
    @NotNull
    public Component getComponent(@NotNull final String path) {
        return this.getComponent(path, (TextReplacementConfig) null);
    }

    @Override
    @NotNull
    public Component getComponent(@NotNull final String path, @NotNull final Consumer<TextReplacementConfig.Builder> consumer) {
        return this.getComponent(path, this.createReplacementConfig(consumer));
    }

    @NotNull
    private Component getComponent(@NotNull final String path, @Nullable final TextReplacementConfig replacementConfig) {
        final String text = super.getFileConfiguration().getString(path + ".text");
        final String fallback = super.getFileConfiguration().getString(path);

        if (text == null || text.isEmpty()) {
            return TextFormatter.parse(fallback != null ? fallback : "");
        }

        return this.createComponent(path, text, replacementConfig);
    }

    @Override
    @NotNull
    public List<Component> getComponents(@NotNull final String path) {
        return this.getComponents(path, (TextReplacementConfig) null);
    }

    @Override
    @NotNull
    public List<Component> getComponents(@NotNull final String path, @NotNull final Consumer<TextReplacementConfig.Builder> consumer) {
        return this.getComponents(path, this.createReplacementConfig(consumer));
    }

    @NotNull
    private List<Component> getComponents(@NotNull final String path, @Nullable final TextReplacementConfig replacementConfig) {
        final List<Component> components = new ArrayList<>();

        for (final String text : super.getFileConfiguration().getStringList(path + ".text")) {
            if (text != null && !text.isEmpty()) {
                components.add(this.createComponent(path, text, replacementConfig));
            }
        }

        return components;
    }

    @NotNull
    private Component createComponent(@NotNull final String path, @NotNull final String text, @Nullable final TextReplacementConfig replacementConfig) {
        Component component = TextFormatter.parse(text);

        if (replacementConfig != null) {
            component = component.replaceText(replacementConfig);
        }

        final String hoverText = super.getFileConfiguration().getString(path + ".hover");

        if (hoverText != null && !hoverText.isEmpty()) {
            component = component.hoverEvent(HoverEvent.showText(TextFormatter.parse(hoverText)));
        }

        final ClickEvent clickEvent = this.createClickEvent(path);

        if (clickEvent != null) {
            component = component.clickEvent(clickEvent);
        }

        return component;
    }

    @Nullable
    private ClickEvent createClickEvent(@NotNull final String path) {
        final Object clickObject = super.getFileConfiguration().get(path + ".click");

        if (clickObject instanceof String) {
            final String value = (String) clickObject;
            return value.isEmpty() ? null : ClickEvent.suggestCommand(value);
        }

        if (!(clickObject instanceof Map<?, ?>)) {
            return null;
        }

        final Map<?, ?> clickMap = (Map<?, ?>) clickObject;
        final Object actionObject = clickMap.get("action");
        final Object valueObject = clickMap.get("text");

        if (!(actionObject instanceof String) || !(valueObject instanceof String)) {
            return null;
        }

        final String actionName = (String) actionObject;
        final String value = (String) valueObject;

        if (actionName.isEmpty() || value.isEmpty()) {
            return null;
        }

        try {
            final ClickEvent.Action action = ClickEvent.Action.valueOf(actionName.toUpperCase(Locale.ROOT));
            return ClickEvent.clickEvent(action, value);
        } catch (final IllegalArgumentException exception) {
            return null;
        }
    }

    @NotNull
    private TextReplacementConfig createReplacementConfig(@NotNull final Consumer<TextReplacementConfig.Builder> consumer) {
        final TextReplacementConfig.Builder builder = TextReplacementConfig.builder();
        consumer.accept(builder);
        return builder.build();
    }

    @Override
    public String getString(@NotNull final String path) {
        return super.getFileConfiguration().getString(path);
    }

    @Override
    public long getLong(@NotNull final String path) {
        return super.getFileConfiguration().getLong(path);
    }

    @Override
    public int getInteger(@NotNull final String path) {
        return super.getFileConfiguration().getInt(path);
    }

    @Override
    public short getShort(@NotNull final String path) {
        return (short) super.getFileConfiguration().getInt(path);
    }

    @Override
    public byte getByte(@NotNull final String path) {
        return (byte) super.getFileConfiguration().getInt(path);
    }

    @Override
    public double getDouble(@NotNull final String path) {
        return super.getFileConfiguration().getDouble(path);
    }

    @Override
    public float getFloat(@NotNull final String path) {
        return (float) super.getFileConfiguration().getDouble(path);
    }

    @Override
    public boolean getBoolean(@NotNull final String path) {
        return super.getFileConfiguration().getBoolean(path);
    }

    @Override
    public char getCharacter(@NotNull final String path) {
        final String value = super.getFileConfiguration().getString(path);

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Missing character at configuration path: " + path);
        }

        return value.charAt(0);
    }

    @Override
    public List<String> getStringList(@NotNull final String path) {
        return super.getFileConfiguration().getStringList(path);
    }

    @Override
    public List<Long> getLongList(@NotNull final String path) {
        return super.getFileConfiguration().getLongList(path);
    }

    @Override
    public List<Integer> getIntegerList(@NotNull final String path) {
        return super.getFileConfiguration().getIntegerList(path);
    }

    @Override
    public List<Short> getShortList(@NotNull final String path) {
        return super.getFileConfiguration().getShortList(path);
    }

    @Override
    public List<Byte> getByteList(@NotNull final String path) {
        return super.getFileConfiguration().getByteList(path);
    }

    @Override
    public List<Double> getDoubleList(@NotNull final String path) {
        return super.getFileConfiguration().getDoubleList(path);
    }

    @Override
    public List<Float> getFloatList(@NotNull final String path) {
        return super.getFileConfiguration().getFloatList(path);
    }

    @Override
    public List<Boolean> getBooleanList(@NotNull final String path) {
        return super.getFileConfiguration().getBooleanList(path);
    }

    @Override
    public List<Character> getCharacterList(@NotNull final String path) {
        return super.getFileConfiguration().getCharList(path);
    }

}

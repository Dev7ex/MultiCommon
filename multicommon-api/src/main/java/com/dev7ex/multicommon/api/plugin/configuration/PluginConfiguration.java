package com.dev7ex.multicommon.api.plugin.configuration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Dev7ex
 * @since 03.02.2025
 */
public interface PluginConfiguration {

    Component getComponent(@NotNull final String path);

    Component getComponent(@NotNull final String path, @NotNull final Consumer<TextReplacementConfig.Builder> consumer);

    List<Component> getComponents(@NotNull final String path);

    List<Component> getComponents(@NotNull final String path, @NotNull final Consumer<TextReplacementConfig.Builder> consumer);

    String getString(@NotNull final String path);

    long getLong(@NotNull final String path);

    int getInteger(@NotNull final String path);

    short getShort(@NotNull final String path);

    byte getByte(@NotNull final String path);

    double getDouble(@NotNull final String path);

    float getFloat(@NotNull final String path);

    boolean getBoolean(@NotNull final String path);

    char getCharacter(@NotNull final String path);

    List<String> getStringList(@NotNull final String path);

    List<Long> getLongList(@NotNull final String path);

    List<Integer> getIntegerList(@NotNull final String path);

    List<Short> getShortList(@NotNull final String path);

    List<Byte> getByteList(@NotNull final String path);

    List<Double> getDoubleList(@NotNull final String path);

    List<Float> getFloatList(@NotNull final String path);

    List<Boolean> getBooleanList(@NotNull final String path);

    List<Character> getCharacterList(@NotNull final String path);

}

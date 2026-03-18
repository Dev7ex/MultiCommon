package com.dev7ex.multicommon.api.io.file.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class provides methods to handle YAML file configuration loading and saving.
 * It utilizes SnakeYAML's engine to process YAML data efficiently.
 *
 * <p>The YamlConfiguration class supports reading and writing YAML configurations to and from various sources,
 * including {@link File}, {@link InputStream}, {@link Reader}, and {@link String}.
 * The data is internally converted into a {@link FileConfiguration} object, which can then be utilized within the application.
 * It ensures thread safety using {@link ThreadLocal} for both dumping and loading operations.</p>
 *
 * <p>This class is part of the Dev7ex MultiBlueprint package and is intended for internal use to manage YAML-based configurations.</p>
 *
 * @see FileConfiguration
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class YamlConfiguration extends ConfigurationProvider {

    /**
     * Thread-local instance of {@link Dump} used to write YAML data.
     */
    private final ThreadLocal<Dump> dump = ThreadLocal.withInitial(() -> {
        final DumpSettings settings = DumpSettings.builder()
                .setDefaultFlowStyle(org.snakeyaml.engine.v2.common.FlowStyle.BLOCK)
                .build();
        return new Dump(settings);
    });

    /**
     * Thread-local instance of {@link Load} used to load YAML data.
     */
    private final ThreadLocal<Load> load = ThreadLocal.withInitial(() -> {
        final LoadSettings settings = LoadSettings.builder().build();
        return new Load(settings);
    });

    /**
     * Saves the configuration data to a specified file.
     *
     * @param config The configuration to be saved.
     * @param file The file to which the configuration will be saved.
     * @throws IOException If an error occurs while writing the file.
     */
    @Override
    public void save(@NotNull final FileConfiguration config, @NotNull final File file) throws IOException {
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            this.save(config, writer);
        }
    }

    /**
     * Saves the configuration data to a specified writer.
     *
     * @param config The configuration to be saved.
     * @param writer The writer to which the configuration will be saved.
     */
    @Override
    public void save(@NotNull final FileConfiguration config, @NotNull final Writer writer) {
        final String output = this.dump.get().dumpToString(config.self);
        try {
            writer.write(output);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Loads the configuration from a file.
     *
     * @param file The file from which to load the configuration.
     * @return The loaded {@link FileConfiguration}.
     * @throws IOException If an error occurs while reading the file.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final File file) throws IOException {
        return this.load(file, null);
    }

    /**
     * Loads the configuration from a file with optional default values.
     *
     * @param file The file from which to load the configuration.
     * @param defaults The default configuration to apply if the file is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     * @throws IOException If an error occurs while reading the file.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final File file, @Nullable final FileConfiguration defaults) throws IOException {
        try (final FileInputStream is = new FileInputStream(file)) {
            return this.load(is, defaults);
        }
    }

    /**
     * Loads the configuration from a reader.
     *
     * @param reader The reader from which to load the configuration.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final Reader reader) {
        return this.load(reader, null);
    }

    /**
     * Loads the configuration from a reader with optional default values.
     *
     * @param reader The reader from which to load the configuration.
     * @param defaults The default configuration to apply if the reader data is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final Reader reader, @Nullable final FileConfiguration defaults) {
        final Object data = this.load.get().loadFromReader(reader);
        return new FileConfiguration(this.castToMap(data), defaults);
    }

    /**
     * Loads the configuration from an input stream.
     *
     * @param is The input stream from which to load the configuration.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final InputStream is) {
        return this.load(is, null);
    }

    /**
     * Loads the configuration from an input stream with optional default values.
     *
     * @param is The input stream from which to load the configuration.
     * @param defaults The default configuration to apply if the input stream data is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final InputStream is, @Nullable final FileConfiguration defaults) {
        final Object data = this.load.get().loadFromInputStream(is);
        return new FileConfiguration(this.castToMap(data), defaults);
    }

    /**
     * Loads the configuration from a string.
     *
     * @param string The string from which to load the configuration.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final String string) {
        return this.load(string, null);
    }

    /**
     * Loads the configuration from a string with optional default values.
     *
     * @param string The string from which to load the configuration.
     * @param defaults The default configuration to apply if the string data is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public @NotNull FileConfiguration load(@NotNull final String string, @Nullable final FileConfiguration defaults) {
        final Object data = this.load.get().loadFromString(string);
        return new FileConfiguration(this.castToMap(data), defaults);
    }

    /**
     * Casts the provided data to a {@link Map<String, Object>}. If the data is not an instance of Map, returns an empty map.
     *
     * @param data The data to cast.
     * @return The casted map, or an empty map if the data is not a Map.
     */
    @SuppressWarnings("unchecked")
    private @NotNull Map<String, Object> castToMap(@Nullable final Object data) {
        if (data instanceof Map) {
            return (Map<String, Object>) data;
        }
        return new LinkedHashMap<>();
    }

}

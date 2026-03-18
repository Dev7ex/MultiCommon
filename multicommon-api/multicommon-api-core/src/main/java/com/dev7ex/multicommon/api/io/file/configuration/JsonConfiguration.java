package com.dev7ex.multicommon.api.io.file.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of {@link ConfigurationProvider} for handling JSON-based configurations.
 * This class provides functionality to load and save configurations from and to JSON files,
 * using {@link Gson} for serialization and deserialization.
 *
 * <p>The class supports reading and writing configurations from/to various sources including
 * {@link File}, {@link InputStream}, {@link Reader}, and {@link String}. It also provides
 * the ability to handle configurations with null values and formats the JSON with pretty printing.</p>
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class JsonConfiguration extends ConfigurationProvider {

    /**
     * Gson instance used for serializing and deserializing JSON data.
     * It is configured to serialize null values and to pretty-print the output.
     */
    private final Gson json = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(FileConfiguration.class, (JsonSerializer<FileConfiguration>) (configuration, type, context)
                    -> context.serialize(configuration.self)).create();

    /**
     * Saves the provided configuration to a specified file.
     *
     * @param config The configuration to be saved.
     * @param file   The file to which the configuration will be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    @Override
    public void save(final FileConfiguration config, final File file) throws IOException {
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            this.save(config, writer);
        }
    }

    /**
     * Saves the provided configuration to a specified writer.
     *
     * @param config The configuration to be saved.
     * @param writer The writer to which the configuration will be saved.
     */
    @Override
    public void save(final FileConfiguration config, final Writer writer) {
        this.json.toJson(config.self, writer);
    }

    /**
     * Loads the configuration from the specified file.
     *
     * @param file The file from which the configuration will be loaded.
     * @return The loaded {@link FileConfiguration}.
     * @throws IOException If an error occurs while reading from the file.
     */
    @Override
    public FileConfiguration load(final File file) throws IOException {
        return this.load(file, null);
    }

    /**
     * Loads the configuration from the specified file with optional defaults.
     *
     * @param file     The file from which the configuration will be loaded.
     * @param defaults The default configuration to use if the file is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     * @throws IOException If an error occurs while reading from the file.
     */
    @Override
    public FileConfiguration load(final File file, final FileConfiguration defaults) throws IOException {
        try (final FileInputStream is = new FileInputStream(file)) {
            return this.load(is, defaults);
        }
    }

    /**
     * Loads the configuration from the specified reader.
     *
     * @param reader The reader from which the configuration will be loaded.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public FileConfiguration load(final Reader reader) {
        return this.load(reader, null);
    }

    /**
     * Loads the configuration from the specified reader with optional defaults.
     *
     * @param reader   The reader from which the configuration will be loaded.
     * @param defaults The default configuration to use if the reader is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    @SuppressWarnings("unchecked")
    public FileConfiguration load(final Reader reader, final FileConfiguration defaults) {
        Map<String, Object> map = this.json.fromJson(reader, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        return new FileConfiguration(map, defaults);
    }

    /**
     * Loads the configuration from the specified input stream.
     *
     * @param is The input stream from which the configuration will be loaded.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public FileConfiguration load(final InputStream is) {
        return this.load(is, null);
    }

    /**
     * Loads the configuration from the specified input stream with optional defaults.
     *
     * @param is       The input stream from which the configuration will be loaded.
     * @param defaults The default configuration to use if the input stream is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public FileConfiguration load(final InputStream is, final FileConfiguration defaults) {
        return this.load(new InputStreamReader(is, StandardCharsets.UTF_8), defaults);
    }

    /**
     * Loads the configuration from the specified string.
     *
     * @param string The string from which the configuration will be loaded.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    public FileConfiguration load(final String string) {
        return this.load(string, null);
    }

    /**
     * Loads the configuration from the specified string with optional defaults.
     *
     * @param string   The string from which the configuration will be loaded.
     * @param defaults The default configuration to use if the string is empty or invalid.
     * @return The loaded {@link FileConfiguration}.
     */
    @Override
    @SuppressWarnings("unchecked")
    public FileConfiguration load(final String string, final FileConfiguration defaults) {
        Map<String, Object> map = this.json.fromJson(string, LinkedHashMap.class);
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        return new FileConfiguration(map, defaults);
    }

}

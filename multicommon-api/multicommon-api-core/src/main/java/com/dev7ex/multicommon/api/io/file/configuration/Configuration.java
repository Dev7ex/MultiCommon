package com.dev7ex.multicommon.api.io.file.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * A utility class to manage file-based configurations.
 */
@Getter(AccessLevel.PUBLIC)
public class Configuration implements ConfigurationBase {

    private File configurationFile;
    private FileConfiguration fileConfiguration;
    private final ConfigurationHolder configurationHolder;

    /**
     * Constructs a new Configuration instance.
     *
     * @param configurationHolder The ConfigurationHolder providing data folder information.
     */
    @SneakyThrows
    public Configuration(@NotNull final ConfigurationHolder configurationHolder) {
        this.configurationHolder = configurationHolder;

        if (!this.configurationHolder.getDataDirectory().exists()) {
            this.configurationHolder.getDataDirectory().mkdirs();
        }
        this.configurationFile = new File(this.configurationHolder.getDataDirectory() + File.separator + this.getFileName());
    }

    /**
     * Creates a new configuration file if it does not exist.
     */
    @Override
    @SneakyThrows
    public void createFile() {
        this.configurationFile.createNewFile();
    }

    /**
     * Deletes the configuration file.
     */
    @Override
    public void deleteFile() {
        this.configurationFile.delete();
    }

    /**
     * Copies the default configuration file from resources to the data folder if it does not exist.
     */
    @Override
    @SneakyThrows
    public void copyFile() {
        this.configurationFile = new File(this.configurationHolder.getDataDirectory(), this.getFileName());
        if (this.configurationFile.exists()) {
            return;
        }
        try (final InputStream inputStream = this.configurationHolder.getClass().getClassLoader().getResourceAsStream(this.getFileName())) {
            Files.copy(inputStream, this.configurationFile.toPath());
        }
    }

    /**
     * Loads the configuration file.
     */
    @Override
    @SneakyThrows
    public void loadFile() {
        this.fileConfiguration = ConfigurationProvider.getProvider(this.getProvider()).load(this.configurationFile);
    }

    /**
     * Saves the configuration file.
     */
    @Override
    @SneakyThrows
    public void saveFile() {
        ConfigurationProvider.getProvider(this.getProvider()).save(this.fileConfiguration, this.configurationFile);
    }

    /**
     * Gets the file name of the configuration.
     *
     * @return The file name.
     */
    @Override
    public final String getFileName() {
        return this.getClass().getAnnotation(ConfigurationProperties.class).fileName();
    }

    /**
     * Gets the configuration provider class.
     *
     * @return The configuration provider class.
     */
    @Override
    public final Class<? extends ConfigurationProvider> getProvider() {
        return this.getClass().getAnnotation(ConfigurationProperties.class).provider();
    }

    @Override
    public File getDataDirectory() {
        return this.configurationHolder.getDataDirectory();
    }

}

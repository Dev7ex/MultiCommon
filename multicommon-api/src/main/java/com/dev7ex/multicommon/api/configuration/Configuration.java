package com.dev7ex.multicommon.api.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
            if (inputStream == null) {
                throw new IllegalStateException(String.format("Resource not found: %s", this.getFileName()));
            }
            final File temporaryFile = this.getTemporaryFile();

            try {
                Files.copy(inputStream, temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                this.moveFile(temporaryFile);
            } finally {
                Files.deleteIfExists(temporaryFile.toPath());
            }
        }
    }

    /**
     * Loads the configuration file.
     */
    @Override
    @SneakyThrows
    public void loadFile() {
        try {
            this.fileConfiguration = ConfigurationProvider.getProvider(this.getProvider()).load(this.configurationFile);
        } catch (final RuntimeException exception) {
            throw new IllegalStateException(String.format("Can't load File %s: %s", this.configurationFile.getName(), exception.getMessage()), exception);
        }
    }

    /**
     * Saves the configuration file.
     */
    @Override
    @SneakyThrows
    public synchronized void saveFile() {
        final File temporaryFile = this.getTemporaryFile();

        try {
            ConfigurationProvider.getProvider(this.getProvider()).save(this.fileConfiguration, temporaryFile);
            this.moveFile(temporaryFile);
        } finally {
            Files.deleteIfExists(temporaryFile.toPath());
        }
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

    private @NotNull File getTemporaryFile() {
        return new File(this.configurationFile.getParentFile(), this.configurationFile.getName() + ".tmp");
    }

    private void moveFile(@NotNull final File temporaryFile) throws IOException {
        try {
            Files.move(temporaryFile.toPath(), this.configurationFile.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (final AtomicMoveNotSupportedException exception) {
            Files.move(temporaryFile.toPath(), this.configurationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public File getDataDirectory() {
        return this.configurationHolder.getDataDirectory();
    }

}

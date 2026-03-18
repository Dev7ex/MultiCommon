package com.dev7ex.multicommon.api.io.file.configuration;

import java.io.File;

/**
 * @author Dev7ex
 * @since 13.04.2025
 */
public interface ConfigurationBase {

    /**
     * Creates a new configuration file if it does not exist.
     */
    void createFile();

    /**
     * Deletes the configuration file.
     */
    void deleteFile();

    /**
     * Copies the default configuration file from resources to the data folder if it does not exist.
     */
    void copyFile();

    /**
     * Loads the configuration file.
     */
    void loadFile();

    /**
     * Saves the configuration file.
     */
    void saveFile();

    /**
     * Gets the file name of the configuration.
     *
     * @return The file name.
     */
    String getFileName();

    /**
     * Gets the configuration provider class.
     *
     * @return The configuration provider class.
     */
    Class<? extends ConfigurationProvider> getProvider();

    /**
     * Get the data folder for the configuration.
     *
     * @return The data folder where configuration files are stored.
     */
    File getDataDirectory();

}


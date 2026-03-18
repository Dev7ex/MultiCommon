package com.dev7ex.multicommon.api.io.file.configuration;

import java.io.File;

/**
 * A contract for objects that provide the data folder location.
 */
public interface ConfigurationHolder {

    /**
     * Gets the data folder.
     *
     * @return The data folder.
     */
    File getDataDirectory();

}

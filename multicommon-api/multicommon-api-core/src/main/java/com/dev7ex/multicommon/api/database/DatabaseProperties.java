package com.dev7ex.multicommon.api.database;

/**
 * @author Dev7ex
 * @since 17.02.2024
 */
public interface DatabaseProperties {

    String getHostName();

    int getPort();

    String getUserName();

    String getPassword();

    String getDatabase();

    boolean isConnectionAllowed();

}

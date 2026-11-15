package com.dev7ex.multicommon.api.plugin;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A utility class to monitor file changes inside the plugin's data folder.
 * It uses Apache Commons IO's FileAlterationMonitor and runs asynchronously.
 * <p>
 * All logs are routed through the plugin logger with clear context.
 * </p>
 *
 * @author Dev7ex
 * @since 2025-07-16
 */
@Getter(AccessLevel.PUBLIC)
public class PluginFileMonitor {

    private final Plugin plugin;
    private final FileAlterationListener alterationListener;
    private long interval = 5000L; // Default interval in milliseconds

    private FileAlterationObserver alterationObserver;
    private FileAlterationMonitor alterationMonitor;

    /**
     * Creates a new file monitor with default polling interval (5000ms).
     *
     * @param plugin             The plugin instance
     * @param alterationListener The listener that handles file events
     */
    public PluginFileMonitor(@NotNull final Plugin plugin,
                             @NotNull final FileAlterationListener alterationListener) {
        this.plugin = plugin;
        this.alterationListener = alterationListener;
    }

    /**
     * Creates a new file monitor with a custom polling interval.
     *
     * @param plugin             The plugin instance
     * @param alterationListener The listener that handles file events
     * @param interval           Polling interval in milliseconds
     */
    public PluginFileMonitor(@NotNull final Plugin plugin,
                             @NotNull final FileAlterationListener alterationListener,
                             final long interval) {
        this(plugin, alterationListener);
        this.interval = interval;
    }

    /**
     * Starts monitoring the plugin's data folder for file changes.
     */
    public void start() {
        final File dataFolder = this.plugin.getDataFolder();
        this.alterationObserver = new FileAlterationObserver(dataFolder);
        this.alterationObserver.addListener(this.alterationListener);
        this.alterationMonitor = new FileAlterationMonitor(this.interval, this.alterationObserver);

        try {
            this.alterationMonitor.start();
            this.plugin.getLogger().info("File monitoring started (interval: " + interval + "ms) at: " + dataFolder.getAbsolutePath());

        } catch (final Exception exception) {
            this.plugin.getLogger().severe("Failed to start file monitoring: " + exception.getMessage());
        }
    }

    /**
     * Stops monitoring file changes.
     */
    public void stop() {
        if (this.alterationMonitor == null) {
            this.plugin.getLogger().warning("Tried to stop file monitoring, but it was never started");
            return;
        }

        try {
            this.alterationMonitor.stop();
            this.plugin.getLogger().info("File monitoring stopped");
        } catch (final Exception exception) {

            this.plugin.getLogger().severe("Failed to stop file monitoring: " + exception.getMessage());
        }
    }

}

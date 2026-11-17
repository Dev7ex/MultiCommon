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
    public synchronized void start() {
        if (this.alterationMonitor != null) {
            this.plugin.getLogger().warning("Tried to start file monitoring, but it is already running");
            return;
        }
        final File dataFolder = this.plugin.getDataFolder();
        final FileAlterationObserver alterationObserver = new FileAlterationObserver(dataFolder);
        alterationObserver.addListener(this.alterationListener);

        final FileAlterationMonitor alterationMonitor = new FileAlterationMonitor(this.interval, alterationObserver);
        alterationMonitor.setThreadFactory(runnable -> {
            final Thread thread = new Thread(runnable, this.plugin.getName() + "-FileMonitor");
            thread.setDaemon(true);
            return thread;
        });

        try {
            alterationMonitor.start();
            this.alterationObserver = alterationObserver;
            this.alterationMonitor = alterationMonitor;
            this.plugin.getLogger().info("File monitoring started (interval: " + interval + "ms) at: " + dataFolder.getAbsolutePath());

        } catch (final Exception exception) {
            this.plugin.getLogger().severe("Failed to start file monitoring: " + exception.getMessage());
        }
    }

    /**
     * Stops monitoring file changes.
     */
    public synchronized void stop() {
        if (this.alterationMonitor == null) {
            this.plugin.getLogger().warning("Tried to stop file monitoring, but it was never started");
            return;
        }

        try {
            this.alterationMonitor.stop(1000L);
            this.plugin.getLogger().info("File monitoring stopped");
        } catch (final Exception exception) {

            this.plugin.getLogger().severe("Failed to stop file monitoring: " + exception.getMessage());
        } finally {
            this.alterationMonitor = null;
            this.alterationObserver = null;
        }
    }

}

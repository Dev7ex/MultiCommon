package com.dev7ex.multicommon.api.command;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author MGG
 * @since 26.11.2026
 */
final class FoliaCommandScheduler {

    private FoliaCommandScheduler() {
        throw new UnsupportedOperationException("Utility class");
    }

    static void execute(@NotNull final Plugin plugin, @NotNull final Runnable runnable) {
        Bukkit.getGlobalRegionScheduler().execute(plugin, runnable);
    }

}

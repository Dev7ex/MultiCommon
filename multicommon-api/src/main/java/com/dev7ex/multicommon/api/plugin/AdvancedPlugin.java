
package com.dev7ex.multicommon.api.plugin;

import com.dev7ex.multicommon.api.command.PaperCommand;
import com.dev7ex.multicommon.api.command.PaperCommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * @author Dev7ex
 */
public abstract class AdvancedPlugin extends JavaPlugin {

    public void registerCommands() {
    }

    public void registerListeners() {
    }

    public void registerManagers() {
    }

    public void registerModules() {
    }

    public void registerTasks() {
    }

    public void registerFactories() {

    }

    public void registerRepositories() {

    }

    public void registerCommand(@NotNull final PaperCommand command) {
        final PluginCommand pluginCommand = Objects.requireNonNull(super.getCommand(command.getName()), "Command is not registered in plugin.yml: " + command.getName());
        pluginCommand.setExecutor(new PaperCommandExecutor(command));

        if (command.getAliases() != null && command.getAliases().length > 0) {
            pluginCommand.setAliases(Arrays.asList(command.getAliases()));
        }

        if (command instanceof TabCompleter tabCompleter) {
            pluginCommand.setTabCompleter(tabCompleter);
        }
    }

    public void registerCommandIf(@NotNull final PaperCommand command, @NotNull final BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            this.registerCommand(command);
        }
    }

    public void registerListener(@NotNull final Listener listener) {
        super.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerListenerIf(@NotNull final Listener listener, @NotNull final BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            this.registerListener(listener);
        }
    }

    @NotNull
    public File createDataDirectory() {
        return this.createDirectory(super.getDataFolder());
    }

    @NotNull
    public File createSubDirectory(@NotNull final String directoryName) {
        return this.createDirectory(this.getSubDirectory(directoryName));
    }

    @NotNull
    private File createDirectory(@NotNull final File directory) {
        if (!directory.isDirectory() && !directory.mkdirs()) {
            throw new IllegalStateException("Failed to create directory: " + directory.getAbsolutePath());
        }

        return directory;
    }

    @NotNull
    public File getSubDirectory(@NotNull final String directoryName) {
        return new File(super.getDataFolder(), directoryName);
    }

    @NotNull
    public File getDataDirectory() {
        return super.getDataFolder();
    }

}

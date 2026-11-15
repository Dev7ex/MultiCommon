
package com.dev7ex.multicommon.api.command;

import com.dev7ex.multicommon.api.bukkit.command.PaperCommandArgument;
import com.dev7ex.multicommon.api.plugin.AdvancedPlugin;
import com.dev7ex.multicommon.api.plugin.ConfigurablePlugin;
import com.dev7ex.multicommon.api.plugin.configuration.PluginConfiguration;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents a base class for plugin commands with support for
 * subcommands, permissions, aliases and plugin configurations.
 *
 * @author Dev7ex
 * @since 05.12.2024
 */
@Getter(AccessLevel.PUBLIC)
public abstract class PaperCommand {

    protected final AdvancedPlugin plugin;
    private final Map<String, PaperCommand> subCommands = new HashMap<>();

    public PaperCommand(@NotNull final AdvancedPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "Plugin cannot be null.");
    }

    public abstract void execute(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments);

    public void registerSubCommand(@NotNull final PaperCommand subCommand) {
        Objects.requireNonNull(subCommand, "Subcommand cannot be null.");

        this.subCommands.put(subCommand.getName().toLowerCase(Locale.ROOT), subCommand);

        for (final String alias : subCommand.getAliases()) {
            if (!alias.isBlank()) {
                this.subCommands.put(alias.toLowerCase(Locale.ROOT), subCommand);
            }
        }
    }

    public void unregisterSubCommand(@NotNull final String commandName) {
        final PaperCommand subCommand = this.subCommands.get(commandName.toLowerCase(Locale.ROOT));

        if (subCommand != null) {
            this.subCommands.values().removeIf(command -> command == subCommand);
        }
    }

    @NotNull
    public Optional<PaperCommand> findSubCommand(@NotNull final String name) {
        return Optional.ofNullable(this.subCommands.get(name.toLowerCase(Locale.ROOT)));
    }

    @Nullable
    public PaperCommand findSubCommand(@NotNull final Class<? extends PaperCommand> commandClass) {
        return this.subCommands.values().stream()
                .filter(command -> command.getClass() == commandClass)
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <T extends PluginConfiguration> T getConfiguration() {
        if (!(this.plugin instanceof ConfigurablePlugin)) {
            throw new IllegalStateException("Plugin does not implement ConfigurablePlugin.");
        }

        return (T) ((ConfigurablePlugin) this.plugin).getConfiguration();
    }

    @NotNull
    public String getCommandProperty(@NotNull final PaperCommandProperty property) {
        switch (property) {
            case NAME:
                return this.getName();
            case PERMISSION:
                return this.getPermission();
            case ALIASES:
                return String.join(", ", this.getAliases());
            default:
                throw new IllegalArgumentException("Unknown command property: " + property);
        }
    }

    public boolean isSubCommandRegistered(@NotNull final String commandName) {
        return this.subCommands.containsKey(commandName.toLowerCase(Locale.ROOT));
    }

    @NotNull
    public Collection<PaperCommand> getAllSubCommands() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(this.subCommands.values()));
    }

    @NotNull
    private PaperCommandProperties getProperties() {
        final PaperCommandProperties properties = this.getClass().getAnnotation(PaperCommandProperties.class);

        if (properties == null) {
            throw new IllegalStateException("Missing @PaperCommandProperties annotation on " + this.getClass().getName());
        }

        return properties;
    }

    @NotNull
    public String getName() {
        return this.getProperties().name();
    }

    @NotNull
    public String getPermission() {
        return this.getProperties().permission();
    }

    @NotNull
    public String[] getAliases() {
        return this.getProperties().aliases();
    }

    public boolean hasPermission() {
        return !this.getPermission().isBlank();
    }

    public boolean hasPermission(@NotNull final CommandSender commandSender) {
        return !this.hasPermission() || commandSender.hasPermission(this.getPermission());
    }

}

package com.dev7ex.multicommon.api.command;

import com.dev7ex.multicommon.api.MultiCommon;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Dev7ex
 * @since 05.12.2024
 */
public class PaperCommandExecutor implements CommandExecutor {

    private static final boolean FOLIA = MultiCommon.isFolia();

    private final PaperCommand paperCommand;

    /**
     * Constructs a new PaperCommandExecutor for the specified PaperCommand.
     *
     * @param PaperCommand the command to be executed.
     */
    public PaperCommandExecutor(@NotNull final PaperCommand PaperCommand) {
        this.paperCommand = PaperCommand;
    }

    /**
     * Executes the given command, returning its success.
     * If false is returned, the "usage" string defined in the plugin.yml file will be sent to the player.
     *
     * @param commandSender the source of the command.
     * @param command       the command that was executed.
     * @param commandLabel  the alias of the command which was used.
     * @param arguments     the arguments passed to the command.
     * @return true if the command was successful, otherwise false.
     */
    @Override
    public boolean onCommand(@NotNull final CommandSender commandSender, @NotNull final Command command,
                             @NotNull final String commandLabel, @NotNull final String[] arguments) {
        if ((!this.paperCommand.getPermission().isBlank()) && (!commandSender.hasPermission(this.paperCommand.getPermission()))) {
            return true;
        }
        final PaperCommandArgument[] commandArguments = Arrays.stream(arguments)
                .map(PaperCommandArgument::new)
                .toArray(PaperCommandArgument[]::new);

        if (Bukkit.isPrimaryThread()) {
            this.paperCommand.execute(commandSender, commandArguments);
            return true;
        }
        if (!this.paperCommand.getPlugin().isEnabled()) {
            return true;
        }
        final Runnable task = () -> this.paperCommand.execute(commandSender, commandArguments);

        if (PaperCommandExecutor.FOLIA) {
            FoliaCommandScheduler.execute(this.paperCommand.getPlugin(), task);
            return true;
        }
        Bukkit.getScheduler().runTask(this.paperCommand.getPlugin(), task);
        return true;
    }

}

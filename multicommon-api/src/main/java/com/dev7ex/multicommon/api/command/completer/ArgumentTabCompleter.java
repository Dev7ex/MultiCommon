
package com.dev7ex.multicommon.api.command.completer;

import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * @author Dev7ex
 * @since 05.12.2024
 */
@FunctionalInterface
public interface ArgumentTabCompleter extends TabCompleter {

    @Nullable
    @Override
    default List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final Command command, @NotNull final String commandLabel, @NotNull final String[] arguments) {
        return this.onTabComplete(commandSender, Arrays.stream(arguments)
                .map(PaperCommandArgument::new)
                .toArray(PaperCommandArgument[]::new));
    }

    @Nullable
    List<String> onTabComplete(@NotNull final CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments);

    static @NotNull List<String> filterByLastArgument(@NotNull final Stream<String> completions, @NotNull final PaperCommandArgument[] arguments) {
        final String prefix = (arguments.length == 0) ? "" : arguments[arguments.length - 1].asString().toLowerCase(Locale.ROOT);

        return completions.filter(completion -> completion.toLowerCase(Locale.ROOT).startsWith(prefix))
                .toList();
    }

}

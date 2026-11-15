package com.dev7ex.multicommon.api.command.completer;

import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dev7ex
 * @since 29.08.2024
 */
public interface MaterialTabCompleter extends ArgumentTabCompleter {

    @Override
    default List<String> onTabComplete(final @NotNull CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        return Arrays.stream(Material.values())
                .map(Enum::name)
                .toList();
    }

}

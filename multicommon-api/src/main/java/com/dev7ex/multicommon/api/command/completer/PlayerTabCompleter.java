package com.dev7ex.multicommon.api.command.completer;

import com.dev7ex.multicommon.api.bukkit.command.PaperCommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Dev7ex
 * @since 05.12.2024
 */
public interface PlayerTabCompleter extends ArgumentTabCompleter {

    @Override
    default List<String> onTabComplete(final @NotNull CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .toList();
    }

}

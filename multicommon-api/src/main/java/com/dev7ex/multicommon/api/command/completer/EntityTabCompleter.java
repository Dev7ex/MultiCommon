package com.dev7ex.multicommon.api.command.completer;

import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dev7ex
 * @since 25.05.2025
 */
public interface EntityTabCompleter extends ArgumentTabCompleter {

    List<String> ENTITY_NAMES = Arrays.stream(EntityType.values())
            .filter(entityType -> entityType != EntityType.UNKNOWN)
            .map(EntityType::name)
            .toList();

    @Override
    default List<String> onTabComplete(final @NotNull CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        return ArgumentTabCompleter.filterByLastArgument(EntityTabCompleter.ENTITY_NAMES.stream(), arguments);
    }

}

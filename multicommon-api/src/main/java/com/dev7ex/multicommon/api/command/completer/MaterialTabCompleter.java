package com.dev7ex.multicommon.api.command.completer;

import com.dev7ex.multicommon.api.bukkit.command.PaperCommandArgument;
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

    List<String> MATERIAL_NAMES = Arrays.stream(Material.values())
            .filter(material -> !material.isLegacy())
            .map(Enum::name)
            .toList();

    @Override
    default List<String> onTabComplete(final @NotNull CommandSender commandSender, @NotNull final PaperCommandArgument[] arguments) {
        return ArgumentTabCompleter.filterByLastArgument(MaterialTabCompleter.MATERIAL_NAMES.stream(), arguments);
    }

}

package com.dev7ex.multicommon.api.command.completer;

import com.dev7ex.multicommon.api.command.PaperCommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Dev7ex
 * @since 05.12.2024
 */
public interface SelectorTabCompleter extends ArgumentTabCompleter {

    @Override
    default List<String> onTabComplete(final @NotNull CommandSender commandSender, final @NotNull PaperCommandArgument[] arguments) {
        return Stream.concat(
                        Stream.of("@a", "@p", "@r", "@s", "@e"),
                        Bukkit.getOnlinePlayers()
                                .stream()
                                .map(Player::getName))
                .toList();
    }

}

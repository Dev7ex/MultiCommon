package com.dev7ex.multicommon.api;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MultiCommon {

    /**
     * @return The Minecraft version of the server (Example: 1.19.0)
     */
    public static String getMinecraftVersion() {
        final String bukkitVersion = Bukkit.getBukkitVersion();
        final int dash = bukkitVersion.indexOf('-');
        return bukkitVersion.substring(0, dash);
    }

    /**
     * @return The NMS version of the server (Example: v1_19_R1)
     */
    public static String getMinecraftServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static boolean isForge() {
        try {
            Class.forName("net.minecraftforge.fml.common.Mod");
            return true;

        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    public static boolean isFabric() {
        try {
            Class.forName("net.fabricmc.api.ModInitializer");
            return true;

        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    public static boolean isPaper() {
        try {
            Class.forName("io.papermc.paper.event.player.AsyncChatEvent");
            return true;

        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;

        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    public static boolean isMiniMessageAvailable() {
        try {
            Class.forName("net.kyori.adventure.text.minimessage.MiniMessage");
            return true;

        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    public static boolean isSpigot() {
        try {
            Class.forName("org.spigotmc.event.player.PlayerSpawnLocationEvent");
            return true;

        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }

    public static CommandSender getCommandSender(@NotNull final String name) {
        final Player player = Bukkit.getPlayerExact(name);
        return (player != null) ? player : Bukkit.getConsoleSender();
    }

    public static LegacyComponentSerializer getLegacyComponentSerializer() {
        return LegacyComponentSerializer.legacySection();
    }

    public static MiniMessage getMiniMessage() {
        return MiniMessage.miniMessage();
    }

}

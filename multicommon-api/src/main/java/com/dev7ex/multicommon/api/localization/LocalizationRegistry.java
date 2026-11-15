package com.dev7ex.multicommon.api.localization;

import com.dev7ex.multicommon.api.configuration.FileConfiguration;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public interface LocalizationRegistry {

    void register(@NotNull final Locale locale, @NotNull final File file);

    void unregister(@NotNull final Locale locale);

    void unregister(@NotNull final String localeCode);

    boolean isRegistered(@NotNull final Locale locale);

    boolean isRegistered(@NotNull final String localeCode);

    Optional<LocalizationMeta> findMeta(@NotNull final Locale locale);

    Locale getDefaultLocale();

    Locale getLocale(@NotNull final CommandSender commandSender);

    Map<Locale, FileConfiguration> getTranslationConfigurations();

}

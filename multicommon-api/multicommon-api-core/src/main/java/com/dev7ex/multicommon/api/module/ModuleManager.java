package com.dev7ex.multicommon.api.module;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dev7ex
 * @since 13.04.2025
 */
public interface ModuleManager<T extends Module> {

    void register(@NotNull final T module);

    void unregister(@NotNull final T module);

    void unregister(@NotNull final Class<? extends T> moduleClazz);

    Optional<T> find(@NotNull final Class<? extends T> moduleClazz);

    void disable(@NotNull final T module);

    void disable(@NotNull final Class<? extends T> moduleClazz);

    void enableAll();

    void disableAll();

    LinkedList<T> getRegisteredModules();

    Map<UUID, T> getModules();

}

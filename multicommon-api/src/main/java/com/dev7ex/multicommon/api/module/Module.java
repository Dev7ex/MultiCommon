package com.dev7ex.multicommon.api.module;

import java.util.UUID;

/**
 * @author Dev7ex
 * @since 12.07.2022
 */
public interface Module {

    void onEnable();

    void onDisable();

    default UUID getUniqueId() {
        return UUID.randomUUID();
    }

    default String getModuleName() {
        return this.getClass().getSimpleName();
    }

}
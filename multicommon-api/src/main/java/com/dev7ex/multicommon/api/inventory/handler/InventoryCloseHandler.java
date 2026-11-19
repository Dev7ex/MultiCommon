package com.dev7ex.multicommon.api.inventory.handler;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
public interface InventoryCloseHandler {

    void onClose(@NotNull final InventoryCloseEvent event);

}

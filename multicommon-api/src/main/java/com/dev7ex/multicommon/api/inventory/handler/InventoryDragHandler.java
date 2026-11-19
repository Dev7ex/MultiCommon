package com.dev7ex.multicommon.api.inventory.handler;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
public interface InventoryDragHandler {

    void onDrag(@NotNull final InventoryDragEvent event);

}

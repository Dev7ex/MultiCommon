package com.dev7ex.multicommon.api.inventory.handler;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
public interface InventoryEventHandler extends InventoryClickHandler,
        InventoryCloseHandler, InventoryDragHandler,
        InventoryOpenHandler, InventoryUpdateHandler {
}

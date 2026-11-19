package com.dev7ex.multicommon.api.inventory.slot;

/**
 * @author Dev7ex
 * @since 30.05.2025
 */
public interface InventorySlotProperty {

    default String getName() {
        return this.getClass().getSimpleName();
    }

}

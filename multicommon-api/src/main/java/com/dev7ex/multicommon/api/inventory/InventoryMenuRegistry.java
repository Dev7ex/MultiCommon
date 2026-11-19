package com.dev7ex.multicommon.api.inventory;

import com.dev7ex.multicommon.api.module.Module;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * @author Dev7ex
 * @since 14.06.2025
 */
public interface InventoryMenuRegistry<T extends InventoryHolder> extends Module {

    Map<Class<? extends T>, List<T>> getInventories();

    <I extends T> void register(@NotNull final I inventory);

    <I extends T> void unregister(@NotNull final I inventory);

    void updateInventories(@NotNull final Class<? extends T> clazz);

    List<T> getInventories(@NotNull final Class<? extends T> inventoryClazz);


}

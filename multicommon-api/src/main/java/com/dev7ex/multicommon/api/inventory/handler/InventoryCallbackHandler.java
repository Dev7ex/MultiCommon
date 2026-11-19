package com.dev7ex.multicommon.api.inventory.handler;

import com.dev7ex.multicommon.api.event.EventCallback;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
public interface InventoryCallbackHandler {

    EventCallback<InventoryClickEvent> getClickCallback();

    void setClickCallback(@NotNull final EventCallback<InventoryClickEvent> clickCallback);

    EventCallback<InventoryCloseEvent> getCloseCallback();

    void setCloseCallback(@NotNull final EventCallback<InventoryCloseEvent> closeCallback);

    EventCallback<InventoryDragEvent> getDragCallback();

    void setDragCallback(@NotNull final EventCallback<InventoryDragEvent> dragCallback);

    EventCallback<InventoryOpenEvent> getOpenCallback();

    void setOpenCallback(@NotNull final EventCallback<InventoryOpenEvent> openCallback);

}

package com.dev7ex.multicommon.api.inventory.slot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Dev7ex
 * @since 30.05.2025
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class InventorySlot {

    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> clickConsumer;
    private final Map<Class<? extends InventorySlotProperty>, Object> properties = new HashMap<>();

    public InventorySlot(@NotNull final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public InventorySlot(@NotNull final ItemStack itemStack, @NotNull final Consumer<InventoryClickEvent> clickConsumer) {
        this.itemStack = itemStack;
        this.clickConsumer = clickConsumer;
    }

    public <T> void setProperty(@NotNull final Class<? extends InventorySlotProperty> propertyClazz, @NotNull final T value) {
        this.properties.put(propertyClazz, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(@NotNull final Class<? extends InventorySlotProperty> propertyClazz) {
        return (T) this.properties.get(propertyClazz);
    }

}

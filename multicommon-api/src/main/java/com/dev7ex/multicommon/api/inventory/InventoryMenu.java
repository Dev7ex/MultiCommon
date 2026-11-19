package com.dev7ex.multicommon.api.inventory;

import com.dev7ex.multicommon.api.inventory.handler.InventoryUpdateHandler;
import com.dev7ex.multicommon.api.inventory.slot.EmptyInventorySlot;
import com.dev7ex.multicommon.api.inventory.slot.InventorySlot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class InventoryMenu implements InventoryMenuContext {

    @NotNull
    private Inventory inventory;

    @NotNull
    private InventorySlot[] slots;

    @NotNull
    private final Player player;

    @NotNull
    private Component title;

    private int rows;

    @Nullable
    private InventoryMenuContext parent;

    @Nullable
    private InventoryMenuContext child;

    public InventoryMenu(@NotNull final Player player, @NotNull final Component title, final int rows) {
        this.player = player;
        this.initializeInventory(title, rows);
    }

    public InventoryMenu(@NotNull final Player player, @NotNull final Component title, final int rows, @Nullable final InventoryMenuContext parent) {
        this.player = player;
        this.parent = parent;
        this.initializeInventory(title, rows);
    }

    @Override
    public void initializeInventory(@NotNull final Component title, final int rows) {
        this.title = title;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
        this.slots = new InventorySlot[this.inventory.getSize()];
    }

    @Override
    public void updateParent() {
        if ((this.parent == null) || (!(this.parent instanceof final InventoryUpdateHandler updateHandler))) {
            return;
        }
        updateHandler.onUpdate();
    }

    @Override
    public void updateChild() {
        if ((this.child == null) || (!(this.child instanceof final InventoryUpdateHandler updateHandler))) {
            return;
        }
        updateHandler.onUpdate();
    }

    @Override
    public @NotNull InventorySlot getSlot(final int id) {
        if ((id) < 0 || (id >= this.slots.length)) {
            return new EmptyInventorySlot();
        }

        if (this.slots[id] == null) {
            return new EmptyInventorySlot();
        }

        return this.slots[id];
    }

    @Override
    public void setSlot(final int id, @NotNull final InventorySlot slot) {
        this.slots[id] = slot;
        this.inventory.setItem(id, slot.getItemStack());
    }

    @Override
    public void setSlot(final int id, @NotNull final ItemStack itemStack) {
        this.slots[id] = new InventorySlot(itemStack);
        this.inventory.setItem(id, itemStack);
    }

    @Override
    public void setSlots(@NotNull final InventorySlot[] slots) {
        System.arraycopy(slots, 0, this.slots, 0, slots.length);

        final ItemStack[] items = Arrays.stream(slots)
                .map(InventorySlot::getItemStack)
                .toArray(ItemStack[]::new);

        this.inventory.setContents(items);
    }

    @Override
    public void clearSlots() {
        Arrays.fill(this.slots, null);
        this.inventory.clear();
    }

    @Override
    public void fillBorder(@NotNull final ItemStack itemStack) {
        Inventories.fillBorder(this.inventory, itemStack);
    }

    @Override
    public int getSize() {
        return this.slots.length;
    }

}

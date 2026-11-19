package com.dev7ex.multicommon.api.inventory;

import com.dev7ex.multicommon.api.inventory.slot.InventorySlot;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
public interface InventoryMenuContext extends InventoryHolder {

    @Override
    @NotNull
    Inventory getInventory();

    @NotNull
    InventorySlot[] getSlots();

    @NotNull
    Player getPlayer();

    Component getTitle();

    int getRows();

    @Nullable
    InventoryMenuContext getParent();

    void setParent(@NotNull final InventoryMenuContext parent);

    void updateParent();

    @Nullable
    InventoryMenuContext getChild();

    void setChild(@NotNull final InventoryMenuContext child);

    void updateChild();

    @NotNull
    InventorySlot getSlot(final int id);

    void setSlot(final int id, @NotNull final InventorySlot slot);

    void setSlot(final int id, @NotNull final ItemStack itemStack);

    void setSlots(@NotNull final InventorySlot[] slots);

    void clearSlots();

    int getSize();

    void initializeInventory(@NotNull final Component title, final int rows);

    void fillBorder(@NotNull final ItemStack itemStack);

}

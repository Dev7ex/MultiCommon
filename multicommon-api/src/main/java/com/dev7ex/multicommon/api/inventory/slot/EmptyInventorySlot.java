package com.dev7ex.multicommon.api.inventory.slot;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
public class EmptyInventorySlot extends InventorySlot {

    public EmptyInventorySlot() {
        super(new ItemStack(Material.AIR));
    }

}

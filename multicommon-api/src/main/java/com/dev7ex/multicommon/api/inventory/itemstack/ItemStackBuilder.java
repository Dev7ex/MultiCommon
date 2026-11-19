package com.dev7ex.multicommon.api.inventory.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author Dev7ex
 * @since 03.02.2025
 */
public class ItemStackBuilder {

    private final ItemStack itemStack;

    public ItemStackBuilder() {
        this.itemStack = new ItemStack(Material.DIRT);
    }

    public ItemStackBuilder(@NotNull final Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(@NotNull final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder(@NotNull final Material material, final int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public <T extends ItemMeta> ItemStackBuilder editMeta(@NotNull final Class<T> metaClass, @NotNull final Consumer<T> consumer) {
        final ItemMeta meta = this.itemStack.getItemMeta();

        if (metaClass.isInstance(meta)) {
            final T specificMeta = metaClass.cast(meta);
            consumer.accept(specificMeta);
            this.itemStack.setItemMeta(specificMeta);

        } else {
            throw new IllegalArgumentException("ItemMeta is not of type: " + metaClass.getName());
        }
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

}

package com.dev7ex.multicommon.api.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Dev7ex
 * @since 03.02.2025
 */
public class Inventories {

    public static int countItem(@NotNull final Inventory inventory, @NotNull final ItemStack itemStack) {
        int count = 0;
        for (final ItemStack item : inventory.getContents()) {
            if ((item != null) && (item.isSimilar(itemStack))) {
                count += item.getAmount();
            }
        }
        return count;
    }

    public static void setInventoryBorder(@NotNull final Inventory inventory, @NotNull final ItemStack borderItem) {
        final int size = inventory.getSize();
        final int rowSize = 9;

        for (int i = rowSize; i < size - rowSize; i += rowSize) {
            inventory.setItem(i, borderItem);
            inventory.setItem(i + rowSize - 1, borderItem);
        }
    }

    public static void setLeftAndRightBorder(Inventory inventory, ItemStack borderItem) {
        final int size = inventory.getSize();
        final int rowSize = 9;

        if ((size % rowSize) != 0) {
            return;
        }


        for (int i = rowSize; i < size - rowSize; i += rowSize) {
            inventory.setItem(i, borderItem);
            inventory.setItem(i + rowSize - 1, borderItem);
        }
    }

    public static void fillBorder(@NotNull final Inventory inventory, @NotNull final ItemStack itemStack) {
        final int size = inventory.getSize();

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, itemStack);
        }

        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, itemStack);
        }

        for (int i = 9; i < size; i += 9) {
            inventory.setItem(i, itemStack);
        }

        for (int i = 17; i < size; i += 9) {
            inventory.setItem(i, itemStack);
        }
    }

    public static String toBase64(@NotNull final Inventory inventory) {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try (final BukkitObjectOutputStream bukkitOutputStream = new BukkitObjectOutputStream(outputStream)) {
                bukkitOutputStream.writeInt(inventory.getSize());

                for (int i = 0; i < inventory.getSize(); i++) {
                    bukkitOutputStream.writeObject(inventory.getItem(i));
                }
                return Base64Coder.encodeLines(outputStream.toByteArray());
            }

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        throw new IllegalStateException("Could not convert inventory to Base64");
    }

    public static Inventory fromBase64(@NotNull final String s) {
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(s))) {
            try (final BukkitObjectInputStream bukkitInputStream = new BukkitObjectInputStream(inputStream)) {
                final Inventory inventory = Bukkit.createInventory(null, bukkitInputStream.readInt());

                for (int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, (ItemStack) bukkitInputStream.readObject());
                }
                return inventory;

            } catch (final ClassNotFoundException exception) {
                exception.printStackTrace();
            }

        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        throw new RuntimeException("Unable to convert Inventory from Base64");
    }

}

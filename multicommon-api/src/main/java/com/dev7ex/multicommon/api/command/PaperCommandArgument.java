package com.dev7ex.multicommon.api.bukkit.command;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * A utility class for parsing and converting a single command argument
 * into various data types.
 * <p>
 * Supports both direct conversion and functional-style conversion with
 * {@link Consumer} callbacks and error handling.
 * </p>
 *
 * @author Dev7ex
 * @since 03.02.2025
 */
public class PaperCommandArgument implements Cloneable, Comparable<PaperCommandArgument> {

    private final String argument;

    /**
     * Constructs a new argument wrapper.
     *
     * @param argument the raw argument string
     */
    public PaperCommandArgument(@NotNull final String argument) {
        this.argument = argument;
    }

    /**
     * Creates a new instance of {@link PaperCommandArgument}.
     *
     * @param argument the raw argument string
     * @return a new PaperCommandArgument instance
     */
    public static PaperCommandArgument of(@NotNull final String argument) {
        return new PaperCommandArgument(argument);
    }

    /**
     * Parses the argument as a byte.
     *
     * @return the byte value
     */
    public byte asByte() {
        return Byte.parseByte(this.argument);
    }

    /**
     * Parses the argument as a byte and provides it to the given consumer.
     *
     * @param byteConsumer       consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asByte(@NotNull final Consumer<Byte> byteConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            byteConsumer.accept(Byte.parseByte(this.argument));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Parses the argument as a short.
     *
     * @return the short value
     */
    public short asShort() {
        return Short.parseShort(this.argument);
    }

    /**
     * Parses the argument as a short and provides it to the given consumer.
     *
     * @param shortConsumer      consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asShort(@NotNull final Consumer<Short> shortConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            shortConsumer.accept(Short.parseShort(this.argument));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Parses the argument as an integer.
     *
     * @return the integer value
     */
    public int asInteger() {
        return Integer.parseInt(this.argument);
    }

    /**
     * Parses the argument as an integer and provides it to the given consumer.
     *
     * @param intConsumer        consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asInteger(@NotNull final Consumer<Integer> intConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            intConsumer.accept(Integer.parseInt(this.argument));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Parses the argument as a long.
     *
     * @return the long value
     */
    public long asLong() {
        return Long.parseLong(this.argument);
    }

    /**
     * Parses the argument as a long and provides it to the given consumer.
     *
     * @param longConsumer       consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asLong(@NotNull final Consumer<Long> longConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            longConsumer.accept(Long.parseLong(this.argument));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Parses the argument as a float.
     *
     * @return the float value
     */
    public float asFloat() {
        return Float.parseFloat(this.argument);
    }

    /**
     * Parses the argument as a float and provides it to the given consumer.
     *
     * @param floatConsumer      consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asFloat(@NotNull final Consumer<Float> floatConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            floatConsumer.accept(Float.parseFloat(this.argument));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Parses the argument as a double.
     *
     * @return the double value
     */
    public double asDouble() {
        return Double.parseDouble(this.argument);
    }

    /**
     * Parses the argument as a double and provides it to the given consumer.
     *
     * @param doubleConsumer     consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asDouble(@NotNull final Consumer<Double> doubleConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            doubleConsumer.accept(Double.parseDouble(this.argument));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Parses the argument as a boolean.
     *
     * @return the boolean value
     */
    public boolean asBoolean() {
        return Boolean.parseBoolean(this.argument);
    }

    /**
     * Parses the argument as a boolean and provides it to the given consumer.
     *
     * @param booleanConsumer    consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asBoolean(@NotNull final Consumer<Boolean> booleanConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            if ("true".equalsIgnoreCase(this.argument) || "false".equalsIgnoreCase(this.argument)) {
                booleanConsumer.accept(Boolean.parseBoolean(this.argument));
            } else {
                throw new IllegalArgumentException("Invalid boolean value: " + this.argument);
            }
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Returns the raw string representation of the argument.
     *
     * @return the argument as string
     */
    public String asString() {
        return this.argument;
    }

    /**
     * Parses the argument as a specific enum constant.
     *
     * @param enumClass the enum class to parse into
     * @param <T>       the enum type
     * @return the parsed enum constant
     */
    public <T extends Enum<T>> T asEnum(@NotNull final Class<T> enumClass) {
        return Enum.valueOf(enumClass, this.argument.toUpperCase());
    }

    /**
     * Parses the argument as an enum constant and provides it to the given consumer.
     *
     * @param enumClass          the enum class
     * @param enumConsumer       consumer for the parsed value
     * @param exceptionConsumer  consumer for any thrown exception
     * @param <T>                the enum type
     */
    public <T extends Enum<T>> void asEnum(
            @NotNull final Class<T> enumClass,
            @NotNull final Consumer<T> enumConsumer,
            @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            enumConsumer.accept(Enum.valueOf(enumClass, this.argument.toUpperCase()));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Parses the argument as a UUID.
     *
     * @return the UUID
     * @throws IllegalArgumentException if the argument is not a valid UUID
     */
    public UUID asUniqueId() {
        return UUID.fromString(this.argument);
    }

    /**
     * Parses the argument as a UUID and provides it to the given consumer.
     *
     * @param uuidConsumer       consumer for the parsed UUID
     * @param exceptionConsumer  consumer for any thrown exception
     */
    public void asUniqueId(@NotNull final Consumer<UUID> uuidConsumer, @NotNull final Consumer<Exception> exceptionConsumer) {
        try {
            uuidConsumer.accept(UUID.fromString(this.argument));
        } catch (final Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

    /**
     * Compares this argument to another string, ignoring case.
     *
     * @param other the other string
     * @return true if equal ignoring case
     */
    public boolean equalsIgnoreCase(@NotNull final String other) {
        return this.argument.equalsIgnoreCase(other);
    }

    /**
     * Creates a copy of this argument object.
     * <p>
     * Useful if you want to preserve the original state
     * while transforming or modifying the argument elsewhere.
     * </p>
     *
     * @return a cloned instance of this argument
     */
    @Override
    public PaperCommandArgument clone() {
        return new PaperCommandArgument(this.argument);
    }

    /**
     * Compares this argument to another {@link PaperCommandArgument}
     * using case-insensitive string comparison.
     * <p>
     * Useful for sorting arguments or auto-complete matching.
     * </p>
     *
     * @param other the other argument to compare to
     * @return comparison result: negative if less, zero if equal, positive if greater
     */
    @Override
    public int compareTo(@NotNull final PaperCommandArgument other) {
        return this.argument.compareToIgnoreCase(other.argument);
    }

}

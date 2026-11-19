package com.dev7ex.multicommon.api.event;

import org.bukkit.event.Event;

import java.util.function.Consumer;

/**
 * @author Dev7ex
 * @since 11.06.2025
 */
public interface EventCallback<T extends Event> extends Consumer<T> {}

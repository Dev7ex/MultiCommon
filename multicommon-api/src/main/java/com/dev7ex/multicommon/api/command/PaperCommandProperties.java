package com.dev7ex.multicommon.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dev7ex
 * @since 03.02.2025
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PaperCommandProperties {

    /**
     * The name of the command.
     *
     * @return the name of the command.
     */
    String name();

    /**
     * Aliases for the command.
     * These are alternative names that can be used to execute the command.
     *
     * @return an array of aliases for the command.
     */
    String[] aliases() default {};

    /**
     * The permission required to execute the command.
     * If the permission is not specified, the command can be executed by anyone.
     *
     * @return the permission required to execute the command.
     */
    String permission() default "";

}

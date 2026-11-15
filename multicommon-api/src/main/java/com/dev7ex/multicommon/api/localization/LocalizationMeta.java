
package com.dev7ex.multicommon.api.localization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents the metadata of a localization file.
 *
 * @author Dev7ex
 * @since 13.11.2026
 */
public interface LocalizationMeta {

    @NotNull String getAuthor();

    @Nullable String getWebsite();

    @Nullable String getContact();

    @NotNull List<String> getContributors();

    @Nullable String getNotes();

    @NotNull String getFileVersion();

    @NotNull SupportedVersion getSupportedPluginVersion();

    long getLastUpdate();

    /**
     * Represents the supported plugin version range.
     */
    interface SupportedVersion {

        @NotNull String getMin();

        @NotNull String getMax();

    }

}

package com.dev7ex.multicommon.api.plugin;

import com.dev7ex.multicommon.api.configuration.ConfigurationHolder;
import com.dev7ex.multicommon.api.plugin.configuration.PluginConfiguration;

/**
 * @author Dev7ex
 * @since 03.02.2025
 */
public interface ConfigurablePlugin extends ConfigurationHolder {

    <T extends PluginConfiguration> T getConfiguration();

}

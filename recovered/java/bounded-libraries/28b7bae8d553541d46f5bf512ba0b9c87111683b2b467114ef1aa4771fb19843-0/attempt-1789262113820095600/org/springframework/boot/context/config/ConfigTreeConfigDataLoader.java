/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.context.config.ConfigTreeConfigDataResource;
import org.springframework.boot.env.ConfigTreePropertySource;

public class ConfigTreeConfigDataLoader
implements ConfigDataLoader<ConfigTreeConfigDataResource> {
    @Override
    public ConfigData load(ConfigDataLoaderContext context, ConfigTreeConfigDataResource resource) throws IOException, ConfigDataResourceNotFoundException {
        Path path = resource.getPath();
        ConfigDataResourceNotFoundException.throwIfDoesNotExist((ConfigDataResource)resource, path);
        String name = "Config tree '" + path + "'";
        ConfigTreePropertySource source = new ConfigTreePropertySource(name, path, ConfigTreePropertySource.Option.AUTO_TRIM_TRAILING_NEW_LINE);
        return new ConfigData(Collections.singletonList(source), new ConfigData.Option[0]);
    }
}


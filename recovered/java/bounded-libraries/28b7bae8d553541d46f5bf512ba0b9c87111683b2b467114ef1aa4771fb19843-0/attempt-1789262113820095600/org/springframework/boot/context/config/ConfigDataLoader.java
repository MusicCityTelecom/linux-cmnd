/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import java.io.IOException;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

public interface ConfigDataLoader<R extends ConfigDataResource> {
    default public boolean isLoadable(ConfigDataLoaderContext context, R resource) {
        return true;
    }

    public ConfigData load(ConfigDataLoaderContext var1, R var2) throws IOException, ConfigDataResourceNotFoundException;
}


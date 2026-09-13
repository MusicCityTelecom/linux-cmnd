/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.context.config;

import java.io.IOException;
import java.util.List;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.context.config.StandardConfigDataReference;
import org.springframework.boot.context.config.StandardConfigDataResource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedResource;
import org.springframework.core.env.PropertySource;

public class StandardConfigDataLoader
implements ConfigDataLoader<StandardConfigDataResource> {
    private static final ConfigData.PropertySourceOptions PROFILE_SPECIFIC = ConfigData.PropertySourceOptions.always(ConfigData.Option.PROFILE_SPECIFIC);
    private static final ConfigData.PropertySourceOptions NON_PROFILE_SPECIFIC = ConfigData.PropertySourceOptions.ALWAYS_NONE;

    @Override
    public ConfigData load(ConfigDataLoaderContext context, StandardConfigDataResource resource) throws IOException, ConfigDataNotFoundException {
        if (resource.isEmptyDirectory()) {
            return ConfigData.EMPTY;
        }
        ConfigDataResourceNotFoundException.throwIfDoesNotExist((ConfigDataResource)resource, resource.getResource());
        StandardConfigDataReference reference = resource.getReference();
        OriginTrackedResource originTrackedResource = OriginTrackedResource.of(resource.getResource(), Origin.from(reference.getConfigDataLocation()));
        String name = String.format("Config resource '%s' via location '%s'", resource, reference.getConfigDataLocation());
        List<PropertySource<?>> propertySources = reference.getPropertySourceLoader().load(name, originTrackedResource);
        ConfigData.PropertySourceOptions options = resource.getProfile() != null ? PROFILE_SPECIFIC : NON_PROFILE_SPECIFIC;
        return new ConfigData(propertySources, options);
    }
}


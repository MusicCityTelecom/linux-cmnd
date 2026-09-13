/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.boot.context.config.ConfigDataLocationResolver;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigTreeConfigDataResource;
import org.springframework.boot.context.config.LocationResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

public class ConfigTreeConfigDataLocationResolver
implements ConfigDataLocationResolver<ConfigTreeConfigDataResource> {
    private static final String PREFIX = "configtree:";
    private final LocationResourceLoader resourceLoader;

    public ConfigTreeConfigDataLocationResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = new LocationResourceLoader(resourceLoader);
    }

    @Override
    public boolean isResolvable(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
        return location.hasPrefix(PREFIX);
    }

    @Override
    public List<ConfigTreeConfigDataResource> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location) {
        try {
            return this.resolve(location.getNonPrefixedValue(PREFIX));
        }
        catch (IOException ex) {
            throw new ConfigDataLocationNotFoundException(location, (Throwable)ex);
        }
    }

    private List<ConfigTreeConfigDataResource> resolve(String location) throws IOException {
        Assert.isTrue((boolean)location.endsWith("/"), () -> String.format("Config tree location '%s' must end with '/'", location));
        if (!this.resourceLoader.isPattern(location)) {
            return Collections.singletonList(new ConfigTreeConfigDataResource(location));
        }
        Resource[] resources = this.resourceLoader.getResources(location, LocationResourceLoader.ResourceType.DIRECTORY);
        ArrayList<ConfigTreeConfigDataResource> resolved = new ArrayList<ConfigTreeConfigDataResource>(resources.length);
        for (Resource resource : resources) {
            resolved.add(new ConfigTreeConfigDataResource(resource.getFile().toPath()));
        }
        return resolved;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import java.util.Collections;
import java.util.List;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.context.config.Profiles;

public interface ConfigDataLocationResolver<R extends ConfigDataResource> {
    public boolean isResolvable(ConfigDataLocationResolverContext var1, ConfigDataLocation var2);

    public List<R> resolve(ConfigDataLocationResolverContext var1, ConfigDataLocation var2) throws ConfigDataLocationNotFoundException, ConfigDataResourceNotFoundException;

    default public List<R> resolveProfileSpecific(ConfigDataLocationResolverContext context, ConfigDataLocation location, Profiles profiles) throws ConfigDataLocationNotFoundException {
        return Collections.emptyList();
    }
}


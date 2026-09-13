/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.springframework.boot.context.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataActivationContext;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataLoaders;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigDataLocationResolvers;
import org.springframework.boot.context.config.ConfigDataNotFoundAction;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.boot.context.config.ConfigDataResolutionResult;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.context.config.Profiles;
import org.springframework.boot.logging.DeferredLogFactory;

class ConfigDataImporter {
    private final Log logger;
    private final ConfigDataLocationResolvers resolvers;
    private final ConfigDataLoaders loaders;
    private final ConfigDataNotFoundAction notFoundAction;
    private final Set<ConfigDataResource> loaded = new HashSet<ConfigDataResource>();
    private final Set<ConfigDataLocation> loadedLocations = new HashSet<ConfigDataLocation>();
    private final Set<ConfigDataLocation> optionalLocations = new HashSet<ConfigDataLocation>();

    ConfigDataImporter(DeferredLogFactory logFactory, ConfigDataNotFoundAction notFoundAction, ConfigDataLocationResolvers resolvers, ConfigDataLoaders loaders) {
        this.logger = logFactory.getLog(this.getClass());
        this.resolvers = resolvers;
        this.loaders = loaders;
        this.notFoundAction = notFoundAction;
    }

    Map<ConfigDataResolutionResult, ConfigData> resolveAndLoad(ConfigDataActivationContext activationContext, ConfigDataLocationResolverContext locationResolverContext, ConfigDataLoaderContext loaderContext, List<ConfigDataLocation> locations) {
        try {
            Profiles profiles = activationContext != null ? activationContext.getProfiles() : null;
            List<ConfigDataResolutionResult> resolved = this.resolve(locationResolverContext, profiles, locations);
            return this.load(loaderContext, resolved);
        }
        catch (IOException ex) {
            throw new IllegalStateException("IO error on loading imports from " + locations, ex);
        }
    }

    private List<ConfigDataResolutionResult> resolve(ConfigDataLocationResolverContext locationResolverContext, Profiles profiles, List<ConfigDataLocation> locations) {
        ArrayList<ConfigDataResolutionResult> resolved = new ArrayList<ConfigDataResolutionResult>(locations.size());
        for (ConfigDataLocation location : locations) {
            resolved.addAll(this.resolve(locationResolverContext, profiles, location));
        }
        return Collections.unmodifiableList(resolved);
    }

    private List<ConfigDataResolutionResult> resolve(ConfigDataLocationResolverContext locationResolverContext, Profiles profiles, ConfigDataLocation location) {
        try {
            return this.resolvers.resolve(locationResolverContext, location, profiles);
        }
        catch (ConfigDataNotFoundException ex) {
            this.handle(ex, location, null);
            return Collections.emptyList();
        }
    }

    private Map<ConfigDataResolutionResult, ConfigData> load(ConfigDataLoaderContext loaderContext, List<ConfigDataResolutionResult> candidates) throws IOException {
        LinkedHashMap<ConfigDataResolutionResult, ConfigData> result = new LinkedHashMap<ConfigDataResolutionResult, ConfigData>();
        for (int i = candidates.size() - 1; i >= 0; --i) {
            ConfigDataResolutionResult candidate = candidates.get(i);
            ConfigDataLocation location = candidate.getLocation();
            ConfigDataResource resource = candidate.getResource();
            if (resource.isOptional()) {
                this.optionalLocations.add(location);
            }
            if (this.loaded.contains(resource)) {
                this.loadedLocations.add(location);
                continue;
            }
            try {
                ConfigData loaded = this.loaders.load(loaderContext, resource);
                if (loaded == null) continue;
                this.loaded.add(resource);
                this.loadedLocations.add(location);
                result.put(candidate, loaded);
                continue;
            }
            catch (ConfigDataNotFoundException ex) {
                this.handle(ex, location, resource);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    private void handle(ConfigDataNotFoundException ex, ConfigDataLocation location, ConfigDataResource resource) {
        if (ex instanceof ConfigDataResourceNotFoundException) {
            ex = ((ConfigDataResourceNotFoundException)ex).withLocation(location);
        }
        this.getNotFoundAction(location, resource).handle(this.logger, ex);
    }

    private ConfigDataNotFoundAction getNotFoundAction(ConfigDataLocation location, ConfigDataResource resource) {
        if (location.isOptional() || resource != null && resource.isOptional()) {
            return ConfigDataNotFoundAction.IGNORE;
        }
        return this.notFoundAction;
    }

    Set<ConfigDataLocation> getLoadedLocations() {
        return this.loadedLocations;
    }

    Set<ConfigDataLocation> getOptionalLocations() {
        return this.optionalLocations;
    }
}


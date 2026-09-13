/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.SpringFactoriesLoader
 */
package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.logging.Log;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.boot.context.config.ConfigDataLocationResolver;
import org.springframework.boot.context.config.ConfigDataLocationResolverContext;
import org.springframework.boot.context.config.ConfigDataResolutionResult;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.context.config.Profiles;
import org.springframework.boot.context.config.StandardConfigDataLocationResolver;
import org.springframework.boot.context.config.UnsupportedConfigDataLocationException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.boot.util.Instantiator;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;

class ConfigDataLocationResolvers {
    private final List<ConfigDataLocationResolver<?>> resolvers;

    ConfigDataLocationResolvers(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, Binder binder, ResourceLoader resourceLoader) {
        this(logFactory, bootstrapContext, binder, resourceLoader, SpringFactoriesLoader.loadFactoryNames(ConfigDataLocationResolver.class, (ClassLoader)resourceLoader.getClassLoader()));
    }

    ConfigDataLocationResolvers(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, Binder binder, ResourceLoader resourceLoader, List<String> names) {
        Instantiator instantiator = new Instantiator(ConfigDataLocationResolver.class, availableParameters -> {
            availableParameters.add(Log.class, logFactory::getLog);
            availableParameters.add(DeferredLogFactory.class, logFactory);
            availableParameters.add(Binder.class, binder);
            availableParameters.add(ResourceLoader.class, resourceLoader);
            availableParameters.add(ConfigurableBootstrapContext.class, bootstrapContext);
            availableParameters.add(BootstrapContext.class, bootstrapContext);
            availableParameters.add(BootstrapRegistry.class, bootstrapContext);
        });
        this.resolvers = this.reorder(instantiator.instantiate(resourceLoader.getClassLoader(), names));
    }

    private List<ConfigDataLocationResolver<?>> reorder(List<ConfigDataLocationResolver<?>> resolvers) {
        ArrayList reordered = new ArrayList(resolvers.size());
        StandardConfigDataLocationResolver resourceResolver = null;
        for (ConfigDataLocationResolver<?> resolver : resolvers) {
            if (resolver instanceof StandardConfigDataLocationResolver) {
                resourceResolver = (StandardConfigDataLocationResolver)resolver;
                continue;
            }
            reordered.add(resolver);
        }
        if (resourceResolver != null) {
            reordered.add(resourceResolver);
        }
        return Collections.unmodifiableList(reordered);
    }

    List<ConfigDataResolutionResult> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location, Profiles profiles) {
        if (location == null) {
            return Collections.emptyList();
        }
        for (ConfigDataLocationResolver<?> resolver : this.getResolvers()) {
            if (!resolver.isResolvable(context, location)) continue;
            return this.resolve(resolver, context, location, profiles);
        }
        throw new UnsupportedConfigDataLocationException(location);
    }

    private List<ConfigDataResolutionResult> resolve(ConfigDataLocationResolver<?> resolver, ConfigDataLocationResolverContext context, ConfigDataLocation location, Profiles profiles) {
        List<ConfigDataResolutionResult> resolved = this.resolve(location, false, () -> resolver.resolve(context, location));
        if (profiles == null) {
            return resolved;
        }
        List<ConfigDataResolutionResult> profileSpecific = this.resolve(location, true, () -> resolver.resolveProfileSpecific(context, location, profiles));
        return this.merge(resolved, profileSpecific);
    }

    private List<ConfigDataResolutionResult> resolve(ConfigDataLocation location, boolean profileSpecific, Supplier<List<? extends ConfigDataResource>> resolveAction) {
        List<? extends ConfigDataResource> resources = this.nonNullList(resolveAction.get());
        ArrayList<ConfigDataResolutionResult> resolved = new ArrayList<ConfigDataResolutionResult>(resources.size());
        for (ConfigDataResource configDataResource : resources) {
            resolved.add(new ConfigDataResolutionResult(location, configDataResource, profileSpecific));
        }
        return resolved;
    }

    private <T> List<T> nonNullList(List<? extends T> list) {
        return list != null ? list : Collections.emptyList();
    }

    private <T> List<T> merge(List<T> list1, List<T> list2) {
        ArrayList<T> merged = new ArrayList<T>(list1.size() + list2.size());
        merged.addAll(list1);
        merged.addAll(list2);
        return merged;
    }

    List<ConfigDataLocationResolver<?>> getResolvers() {
        return this.resolvers;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResource;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.boot.util.Instantiator;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;

class ConfigDataLoaders {
    private final Log logger;
    private final List<ConfigDataLoader<?>> loaders;
    private final List<Class<?>> resourceTypes;

    ConfigDataLoaders(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, ClassLoader classLoader) {
        this(logFactory, bootstrapContext, classLoader, SpringFactoriesLoader.loadFactoryNames(ConfigDataLoader.class, (ClassLoader)classLoader));
    }

    ConfigDataLoaders(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext, ClassLoader classLoader, List<String> names) {
        this.logger = logFactory.getLog(this.getClass());
        Instantiator instantiator = new Instantiator(ConfigDataLoader.class, availableParameters -> {
            availableParameters.add(Log.class, logFactory::getLog);
            availableParameters.add(DeferredLogFactory.class, logFactory);
            availableParameters.add(ConfigurableBootstrapContext.class, bootstrapContext);
            availableParameters.add(BootstrapContext.class, bootstrapContext);
            availableParameters.add(BootstrapRegistry.class, bootstrapContext);
        });
        this.loaders = instantiator.instantiate(classLoader, names);
        this.resourceTypes = this.getResourceTypes(this.loaders);
    }

    private List<Class<?>> getResourceTypes(List<ConfigDataLoader<?>> loaders) {
        ArrayList resourceTypes = new ArrayList(loaders.size());
        for (ConfigDataLoader<?> loader : loaders) {
            resourceTypes.add(this.getResourceType(loader));
        }
        return Collections.unmodifiableList(resourceTypes);
    }

    private Class<?> getResourceType(ConfigDataLoader<?> loader) {
        return ResolvableType.forClass(loader.getClass()).as(ConfigDataLoader.class).resolveGeneric(new int[0]);
    }

    <R extends ConfigDataResource> ConfigData load(ConfigDataLoaderContext context, R resource) throws IOException {
        ConfigDataLoader loader = this.getLoader(context, resource);
        this.logger.trace((Object)LogMessage.of(() -> "Loading " + resource + " using loader " + loader.getClass().getName()));
        return loader.load(context, resource);
    }

    private <R extends ConfigDataResource> ConfigDataLoader<R> getLoader(ConfigDataLoaderContext context, R resource) {
        ConfigDataLoader<?> result = null;
        for (int i = 0; i < this.loaders.size(); ++i) {
            ConfigDataLoader<?> loader;
            ConfigDataLoader<?> candidate = this.loaders.get(i);
            if (!this.resourceTypes.get(i).isInstance(resource) || !(loader = candidate).isLoadable(context, resource)) continue;
            if (result != null) {
                throw new IllegalStateException("Multiple loaders found for resource '" + resource + "' [" + candidate.getClass().getName() + "," + result.getClass().getName() + "]");
            }
            result = loader;
        }
        Assert.state((result != null ? 1 : 0) != 0, () -> "No loader found for resource '" + resource + "'");
        return result;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy
 *  org.apereo.cas.services.resource.AbstractResourceBasedServiceRegistry
 *  org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy
 *  org.apereo.cas.services.util.RegisteredServiceJsonSerializer
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.io.WatcherService
 *  org.apereo.cas.util.serialization.StringSerializer
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.services;

import java.util.Collection;
import org.apereo.cas.services.ServiceRegistryListener;
import org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy;
import org.apereo.cas.services.resource.AbstractResourceBasedServiceRegistry;
import org.apereo.cas.services.resource.RegisteredServiceResourceNamingStrategy;
import org.apereo.cas.services.util.RegisteredServiceJsonSerializer;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.io.WatcherService;
import org.apereo.cas.util.serialization.StringSerializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

public class JsonServiceRegistry
extends AbstractResourceBasedServiceRegistry {
    private static final String FILE_EXTENSION = "json";

    public JsonServiceRegistry(Resource configDirectory, WatcherService serviceRegistryConfigWatcher, ConfigurableApplicationContext applicationContext, RegisteredServiceReplicationStrategy registeredServiceReplicationStrategy, RegisteredServiceResourceNamingStrategy resourceNamingStrategy, Collection<ServiceRegistryListener> serviceRegistryListeners) throws Exception {
        super(configDirectory, (Collection)CollectionUtils.wrapList((Object[])new StringSerializer[]{new RegisteredServiceJsonSerializer(applicationContext)}), applicationContext, registeredServiceReplicationStrategy, resourceNamingStrategy, serviceRegistryListeners, serviceRegistryConfigWatcher);
    }

    protected String[] getExtensions() {
        return new String[]{FILE_EXTENSION};
    }
}


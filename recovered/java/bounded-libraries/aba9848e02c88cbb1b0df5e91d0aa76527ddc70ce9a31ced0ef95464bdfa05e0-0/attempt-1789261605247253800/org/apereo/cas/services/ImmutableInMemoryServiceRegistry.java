/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.services.ImmutableServiceRegistry
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.services;

import java.util.Collection;
import java.util.List;
import org.apereo.cas.services.ImmutableServiceRegistry;
import org.apereo.cas.services.InMemoryServiceRegistry;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistryListener;
import org.springframework.context.ConfigurableApplicationContext;

public class ImmutableInMemoryServiceRegistry
extends InMemoryServiceRegistry
implements ImmutableServiceRegistry {
    public ImmutableInMemoryServiceRegistry(List<RegisteredService> registeredServices, ConfigurableApplicationContext applicationContext, Collection<ServiceRegistryListener> serviceRegistryListeners) {
        super(applicationContext, registeredServices, serviceRegistryListeners);
    }

    @Override
    public RegisteredService save(RegisteredService registeredService) {
        return registeredService;
    }
}


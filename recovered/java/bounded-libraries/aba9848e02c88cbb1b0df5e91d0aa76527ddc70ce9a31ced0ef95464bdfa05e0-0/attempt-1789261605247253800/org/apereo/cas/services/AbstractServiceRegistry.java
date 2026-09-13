/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.services;

import java.util.Collection;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractServiceRegistry
implements ServiceRegistry {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceRegistry.class);
    private final transient ConfigurableApplicationContext applicationContext;
    private final transient Collection<ServiceRegistryListener> serviceRegistryListeners;

    public void publishEvent(ApplicationEvent event) {
        if (this.applicationContext != null) {
            LOGGER.trace("Publishing event [{}]", (Object)event);
            this.applicationContext.publishEvent(event);
        }
    }

    protected RegisteredService invokeServiceRegistryListenerPreSave(RegisteredService registeredService) {
        if (this.serviceRegistryListeners != null) {
            this.serviceRegistryListeners.forEach(listener -> listener.preSave(registeredService));
        }
        return registeredService;
    }

    protected RegisteredService invokeServiceRegistryListenerPostLoad(RegisteredService registeredService) {
        if (this.serviceRegistryListeners != null) {
            this.serviceRegistryListeners.forEach(listener -> listener.postLoad(registeredService));
        }
        return registeredService;
    }

    @Generated
    protected AbstractServiceRegistry(ConfigurableApplicationContext applicationContext, Collection<ServiceRegistryListener> serviceRegistryListeners) {
        this.applicationContext = applicationContext;
        this.serviceRegistryListeners = serviceRegistryListeners;
    }

    @Generated
    public ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public Collection<ServiceRegistryListener> getServiceRegistryListeners() {
        return this.serviceRegistryListeners;
    }
}


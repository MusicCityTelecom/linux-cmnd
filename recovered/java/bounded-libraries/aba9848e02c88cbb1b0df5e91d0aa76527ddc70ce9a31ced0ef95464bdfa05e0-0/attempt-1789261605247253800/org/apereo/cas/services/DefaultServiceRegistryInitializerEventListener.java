/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.support.events.config.CasConfigurationModifiedEvent
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.cloud.context.environment.EnvironmentChangeEvent
 */
package org.apereo.cas.services;

import lombok.Generated;
import org.apereo.cas.services.ServiceRegistryInitializer;
import org.apereo.cas.services.ServiceRegistryInitializerEventListener;
import org.apereo.cas.support.events.config.CasConfigurationModifiedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;

public class DefaultServiceRegistryInitializerEventListener
implements ServiceRegistryInitializerEventListener {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceRegistryInitializerEventListener.class);
    private final ObjectProvider<ServiceRegistryInitializer> serviceRegistryInitializer;

    @Override
    public void handleRefreshEvent(EnvironmentChangeEvent event) {
        LOGGER.trace("Received event [{}]", (Object)event);
        this.rebind();
    }

    @Override
    public void handleEnvironmentChangeEvent(EnvironmentChangeEvent event) {
        LOGGER.trace("Received event [{}]", (Object)event);
        this.rebind();
    }

    @Override
    public void handleConfigurationModifiedEvent(CasConfigurationModifiedEvent event) {
        if (event.isEligibleForContextRefresh()) {
            this.rebind();
        }
    }

    private void rebind() {
        LOGGER.info("Refreshing CAS service registry configuration. Stand by...");
        ((ServiceRegistryInitializer)this.serviceRegistryInitializer.getObject()).initialize();
    }

    @Generated
    public DefaultServiceRegistryInitializerEventListener(ObjectProvider<ServiceRegistryInitializer> serviceRegistryInitializer) {
        this.serviceRegistryInitializer = serviceRegistryInitializer;
    }
}


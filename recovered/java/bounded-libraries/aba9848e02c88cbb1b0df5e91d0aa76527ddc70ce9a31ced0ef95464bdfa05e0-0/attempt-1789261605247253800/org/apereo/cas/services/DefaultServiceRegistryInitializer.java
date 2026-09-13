/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.ChainingServiceRegistry
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServicesManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.services.ChainingServiceRegistry;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryInitializer;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServiceRegistryInitializer
implements ServiceRegistryInitializer {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceRegistryInitializer.class);
    private final ServiceRegistry jsonServiceRegistry;
    private final ChainingServiceRegistry serviceRegistry;
    private final ServicesManager servicesManager;

    @Override
    public void initialize() {
        LOGGER.info("Attempting to initialize the service registry [{}]", (Object)this.serviceRegistry.getName());
        LOGGER.debug("Total count of service registries is [{}] which contain [{}] service definition(s)", (Object)this.serviceRegistry.countServiceRegistries(), (Object)this.serviceRegistry.size());
        LOGGER.info("Service registries [{}] will be auto-initialized from JSON service definitions. You can turn off this behavior via the setting [cas.service-registry.core.init-from-json=false] and explicitly register definitions in the services registry.", (Object)this.serviceRegistry.getName());
        Collection servicesLoaded = this.jsonServiceRegistry.load();
        if (LOGGER.isDebugEnabled()) {
            String servicesList = servicesLoaded.stream().map(RegisteredService::getName).collect(Collectors.joining(","));
            LOGGER.debug("Loaded JSON service definitions are [{}]", (Object)servicesList);
        }
        servicesLoaded.stream().sorted(Comparator.naturalOrder()).forEach(arg_0 -> ((ChainingServiceRegistry)this.serviceRegistry).synchronize(arg_0));
        this.servicesManager.load();
        LOGGER.info("Service registry [{}] contains [{}] service definitions", (Object)this.serviceRegistry.getName(), (Object)this.servicesManager.count());
    }

    @Generated
    public DefaultServiceRegistryInitializer(ServiceRegistry jsonServiceRegistry, ChainingServiceRegistry serviceRegistry, ServicesManager servicesManager) {
        this.jsonServiceRegistry = jsonServiceRegistry;
        this.serviceRegistry = serviceRegistry;
        this.servicesManager = servicesManager;
    }
}


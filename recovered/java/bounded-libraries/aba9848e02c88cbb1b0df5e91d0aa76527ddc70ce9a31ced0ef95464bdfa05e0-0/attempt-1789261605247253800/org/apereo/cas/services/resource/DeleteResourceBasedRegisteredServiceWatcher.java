/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicePreDeleteEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServicesLoadedEvent
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationEvent
 */
package org.apereo.cas.services.resource;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.resource.AbstractResourceBasedServiceRegistry;
import org.apereo.cas.services.resource.BaseResourceBasedRegisteredServiceWatcher;
import org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicePreDeleteEvent;
import org.apereo.cas.support.events.service.CasRegisteredServicesLoadedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

public class DeleteResourceBasedRegisteredServiceWatcher
extends BaseResourceBasedRegisteredServiceWatcher {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteResourceBasedRegisteredServiceWatcher.class);

    public DeleteResourceBasedRegisteredServiceWatcher(AbstractResourceBasedServiceRegistry serviceRegistryDao) {
        super(serviceRegistryDao);
    }

    @Override
    public void accept(File file) {
        String fileName = file.getName();
        if (!fileName.startsWith(".")) {
            if (Arrays.stream(this.serviceRegistryDao.getExtensions()).anyMatch(fileName::endsWith)) {
                LOGGER.debug("Service definition [{}] was deleted. Reloading cache...", (Object)file);
                RegisteredService service = this.serviceRegistryDao.getRegisteredServiceFromFile(file);
                if (service != null) {
                    this.serviceRegistryDao.publishEvent((ApplicationEvent)new CasRegisteredServicePreDeleteEvent((Object)this, service));
                    this.serviceRegistryDao.removeRegisteredService(service);
                    LOGGER.debug("Successfully deleted service definition [{}]", (Object)service.getName());
                    this.serviceRegistryDao.publishEvent((ApplicationEvent)new CasRegisteredServiceDeletedEvent((Object)this, service));
                } else {
                    LOGGER.warn("Unable to locate a matching service definition from file [{}]. Reloading cache...", (Object)file);
                    Collection<RegisteredService> results = this.serviceRegistryDao.load();
                    this.serviceRegistryDao.publishEvent((ApplicationEvent)new CasRegisteredServicesLoadedEvent((Object)this, results));
                }
            }
        }
    }
}


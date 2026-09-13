/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.support.events.service.CasRegisteredServicePreSaveEvent
 *  org.apereo.cas.support.events.service.CasRegisteredServiceSavedEvent
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationEvent
 */
package org.apereo.cas.services.resource;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.resource.AbstractResourceBasedServiceRegistry;
import org.apereo.cas.services.resource.BaseResourceBasedRegisteredServiceWatcher;
import org.apereo.cas.support.events.service.CasRegisteredServicePreSaveEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceSavedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

public class CreateResourceBasedRegisteredServiceWatcher
extends BaseResourceBasedRegisteredServiceWatcher {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateResourceBasedRegisteredServiceWatcher.class);

    public CreateResourceBasedRegisteredServiceWatcher(AbstractResourceBasedServiceRegistry serviceRegistryDao) {
        super(serviceRegistryDao);
    }

    @Override
    public void accept(File file) {
        String fileName = file.getName();
        if (!fileName.startsWith(".")) {
            if (Arrays.stream(this.serviceRegistryDao.getExtensions()).anyMatch(fileName::endsWith)) {
                LOGGER.debug("New service definition [{}] was created. Locating service entry from cache...", (Object)file);
                Collection<RegisteredService> services = this.serviceRegistryDao.load(file);
                services.stream().filter(Objects::nonNull).forEach(service -> {
                    if (this.serviceRegistryDao.findServiceById(service.getId()) != null) {
                        LOG_SERVICE_DUPLICATE.accept(service);
                    }
                    LOGGER.trace("Updating service definitions with [{}]", service);
                    this.serviceRegistryDao.publishEvent((ApplicationEvent)new CasRegisteredServicePreSaveEvent((Object)this, service));
                    this.serviceRegistryDao.update((RegisteredService)service);
                    this.serviceRegistryDao.publishEvent((ApplicationEvent)new CasRegisteredServiceSavedEvent((Object)this, service));
                });
            }
        }
    }
}


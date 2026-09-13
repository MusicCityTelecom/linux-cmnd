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

public class ModifyResourceBasedRegisteredServiceWatcher
extends BaseResourceBasedRegisteredServiceWatcher {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ModifyResourceBasedRegisteredServiceWatcher.class);

    public ModifyResourceBasedRegisteredServiceWatcher(AbstractResourceBasedServiceRegistry registry) {
        super(registry);
    }

    @Override
    public void accept(File file) {
        String fileName = file.getName();
        if (!fileName.startsWith(".")) {
            if (Arrays.stream(this.serviceRegistryDao.getExtensions()).anyMatch(fileName::endsWith)) {
                LOGGER.debug("New service definition [{}] was modified. Locating service entry from cache...", (Object)file);
                Collection<RegisteredService> newServices = this.serviceRegistryDao.load(file);
                newServices.stream().filter(Objects::nonNull).forEach(newService -> {
                    RegisteredService oldService = this.serviceRegistryDao.findServiceById(newService.getId());
                    if (!newService.equals(oldService)) {
                        LOGGER.debug("Updating service definitions with [{}]", newService);
                        this.serviceRegistryDao.publishEvent((ApplicationEvent)new CasRegisteredServicePreSaveEvent((Object)this, newService));
                        this.serviceRegistryDao.update((RegisteredService)newService);
                        this.serviceRegistryDao.publishEvent((ApplicationEvent)new CasRegisteredServiceSavedEvent((Object)this, newService));
                    } else {
                        LOGGER.debug("Service [{}] loaded from [{}] is identical to the existing entry. Entry may have already been saved in the event processing pipeline", (Object)newService.getId(), (Object)file.getName());
                    }
                });
            }
        }
    }
}


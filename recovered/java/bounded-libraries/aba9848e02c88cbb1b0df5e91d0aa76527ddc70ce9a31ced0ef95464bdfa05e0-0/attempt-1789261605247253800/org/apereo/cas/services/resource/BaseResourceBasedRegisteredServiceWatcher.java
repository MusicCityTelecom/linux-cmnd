/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services.resource;

import java.io.File;
import java.util.function.Consumer;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.resource.AbstractResourceBasedServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseResourceBasedRegisteredServiceWatcher
implements Consumer<File> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseResourceBasedRegisteredServiceWatcher.class);
    static final Consumer<RegisteredService> LOG_SERVICE_DUPLICATE = service -> LOGGER.warn("Found a service definition [{}] with a duplicate id [{}]. This will overwrite previous service definitions and is likely a configuration problem. Make sure all services have a unique id and try again.", (Object)service.getServiceId(), (Object)service.getId());
    protected final AbstractResourceBasedServiceRegistry serviceRegistryDao;

    @Override
    public void accept(File file) {
    }

    @Generated
    protected BaseResourceBasedRegisteredServiceWatcher(AbstractResourceBasedServiceRegistry serviceRegistryDao) {
        this.serviceRegistryDao = serviceRegistryDao;
    }
}


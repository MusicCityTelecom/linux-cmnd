/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.util.cache.DistributedCacheObject
 */
package org.apereo.cas.services.replication;

import java.util.List;
import java.util.function.Predicate;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.util.cache.DistributedCacheObject;

public interface RegisteredServiceReplicationStrategy {
    default public RegisteredService getRegisteredServiceFromCacheIfAny(RegisteredService service, String id, ServiceRegistry serviceRegistry) {
        return service;
    }

    default public RegisteredService getRegisteredServiceFromCacheIfAny(RegisteredService service, long id, ServiceRegistry serviceRegistry) {
        return service;
    }

    default public RegisteredService getRegisteredServiceFromCacheByPredicate(RegisteredService service, Predicate<DistributedCacheObject<RegisteredService>> predicate, ServiceRegistry serviceRegistry) {
        return service;
    }

    default public List<RegisteredService> updateLoadedRegisteredServicesFromCache(List<RegisteredService> services, ServiceRegistry serviceRegistry) {
        return services;
    }
}


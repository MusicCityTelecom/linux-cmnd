/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.configuration.model.support.services.stream.StreamingServiceRegistryProperties
 *  org.apereo.cas.configuration.model.support.services.stream.StreamingServicesCoreProperties$ReplicationModes
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent
 *  org.apereo.cas.util.PublisherIdentifier
 *  org.apereo.cas.util.cache.DistributedCacheManager
 *  org.apereo.cas.util.cache.DistributedCacheObject
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 */
package org.apereo.cas.services.replication;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.services.stream.StreamingServiceRegistryProperties;
import org.apereo.cas.configuration.model.support.services.stream.StreamingServicesCoreProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.replication.RegisteredServiceReplicationStrategy;
import org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent;
import org.apereo.cas.util.PublisherIdentifier;
import org.apereo.cas.util.cache.DistributedCacheManager;
import org.apereo.cas.util.cache.DistributedCacheObject;
import org.apereo.cas.util.function.FunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class DefaultRegisteredServiceReplicationStrategy
implements RegisteredServiceReplicationStrategy,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisteredServiceReplicationStrategy.class);
    private final DistributedCacheManager<RegisteredService, DistributedCacheObject<RegisteredService>, PublisherIdentifier> distributedCacheManager;
    private final StreamingServiceRegistryProperties properties;
    private final PublisherIdentifier publisherIdentifier;

    private static boolean isRegisteredServiceMarkedAsDeletedInCache(DistributedCacheObject<RegisteredService> item) {
        if (item.containsProperty("event")) {
            String event = (String)item.getProperty("event", String.class);
            return event.equalsIgnoreCase(CasRegisteredServiceDeletedEvent.class.getSimpleName());
        }
        return false;
    }

    public void destroy() {
        FunctionUtils.doIfNotNull(this.distributedCacheManager, DistributedCacheManager::close);
    }

    @Override
    public RegisteredService getRegisteredServiceFromCacheIfAny(RegisteredService service, String id, ServiceRegistry serviceRegistry) {
        return this.getRegisteredServiceFromCacheByPredicate(service, value -> ((RegisteredService)value.getValue()).matches(id), serviceRegistry);
    }

    @Override
    public RegisteredService getRegisteredServiceFromCacheIfAny(RegisteredService service, long id, ServiceRegistry serviceRegistry) {
        return this.getRegisteredServiceFromCacheByPredicate(service, value -> ((RegisteredService)value.getValue()).getId() == id, serviceRegistry);
    }

    @Override
    public RegisteredService getRegisteredServiceFromCacheByPredicate(RegisteredService service, Predicate<DistributedCacheObject<RegisteredService>> predicate, ServiceRegistry serviceRegistry) {
        Optional result = this.distributedCacheManager.find(predicate);
        if (result.isPresent()) {
            DistributedCacheObject item = (DistributedCacheObject)result.get();
            RegisteredService value = (RegisteredService)item.getValue();
            LOGGER.debug("Located cache entry [{}] in service registry cache [{}]", (Object)item, (Object)this.distributedCacheManager.getName());
            if (DefaultRegisteredServiceReplicationStrategy.isRegisteredServiceMarkedAsDeletedInCache((DistributedCacheObject<RegisteredService>)item)) {
                LOGGER.debug("Service found in the cache [{}] is marked as a deleted service. CAS will update the service registry of this CAS node to remove the local service, if found", (Object)value);
                serviceRegistry.delete(value);
                this.distributedCacheManager.remove((Serializable)value, (Serializable)item, true);
                return service;
            }
            if (service == null) {
                LOGGER.debug("Service is in not found in the local service registry for this CAS node. CAS will use the cache entry [{}] instead and will update the service registry of this CAS node with the cache entry for future look-ups", (Object)value);
                this.saveRegisteredServiceIfNecessary(serviceRegistry, value);
                return value;
            }
            LOGGER.debug("Service definition cache entry [{}] carries the timestamp [{}]", (Object)value, (Object)item.getTimestamp());
            if (value.equals(service)) {
                LOGGER.debug("Service definition cache entry is the same as service definition found locally");
                return service;
            }
            LOGGER.debug("Service definition found in the cache [{}] is more recent than its counterpart on this CAS node. CAS will use the cache entry and update the service registry of this CAS node with the cache entry for future look-ups", (Object)value);
            this.saveRegisteredServiceIfNecessary(serviceRegistry, value);
            return value;
        }
        LOGGER.debug("Requested service definition is not found in the replication cache");
        if (service != null) {
            LOGGER.debug("Attempting to update replication cache with service [{}]", (Object)service);
            DistributedCacheObject item = DistributedCacheObject.builder().value((Serializable)service).publisherIdentifier(this.publisherIdentifier).build();
            this.distributedCacheManager.set((Serializable)service, (Serializable)item, true);
        }
        return service;
    }

    @Override
    public List<RegisteredService> updateLoadedRegisteredServicesFromCache(List<RegisteredService> services, ServiceRegistry serviceRegistry) {
        Collection cachedServices = this.distributedCacheManager.getAll();
        for (DistributedCacheObject entry : cachedServices) {
            RegisteredService cachedService = (RegisteredService)entry.getValue();
            LOGGER.debug("Found cached service definition [{}] in the replication cache [{}]", (Object)cachedService, (Object)this.distributedCacheManager.getName());
            if (DefaultRegisteredServiceReplicationStrategy.isRegisteredServiceMarkedAsDeletedInCache((DistributedCacheObject<RegisteredService>)entry)) {
                LOGGER.debug("Service found in the cache [{}] is marked as a deleted service. CAS will update the service registry of this CAS node to remove the local service, if found.", (Object)cachedService);
                serviceRegistry.delete(cachedService);
                this.distributedCacheManager.remove((Serializable)cachedService, (Serializable)entry, true);
                continue;
            }
            RegisteredService matchingService = services.stream().filter(s -> s.getId() == cachedService.getId()).findFirst().orElse(null);
            if (matchingService != null) {
                this.updateServiceRegistryWithMatchingService(services, cachedService, matchingService, serviceRegistry);
                continue;
            }
            this.updateServiceRegistryWithNoMatchingService(services, cachedService, serviceRegistry);
        }
        return services;
    }

    private void saveRegisteredServiceIfNecessary(ServiceRegistry serviceRegistry, RegisteredService value) {
        if (this.properties.getCore().getReplicationMode() == StreamingServicesCoreProperties.ReplicationModes.ACTIVE) {
            serviceRegistry.save(value);
        }
    }

    private void updateServiceRegistryWithNoMatchingService(List<RegisteredService> services, RegisteredService cachedService, ServiceRegistry serviceRegistry) {
        LOGGER.debug("No corresponding service definition could be matched against cache entry [{}] locally. CAS will update the service registry of this CAS node with the cache entry for future look-ups", (Object)cachedService);
        this.updateServiceRegistryWithRegisteredService(services, cachedService, serviceRegistry);
    }

    private void updateServiceRegistryWithMatchingService(List<RegisteredService> services, RegisteredService cachedService, RegisteredService matchingService, ServiceRegistry serviceRegistry) {
        LOGGER.debug("Found corresponding service definition [{}] locally via cache manager [{}]", (Object)matchingService, (Object)this.distributedCacheManager.getName());
        if (matchingService.equals(cachedService)) {
            LOGGER.debug("Service definition cache entry [{}] is the same as service definition found locally [{}]", (Object)cachedService, (Object)matchingService);
        } else {
            LOGGER.debug("Service definition found in the cache [{}] is more recent than its counterpart on this CAS node. CAS will update the service registry of this CAS node with the cache entry for future look-ups", (Object)cachedService);
            this.updateServiceRegistryWithRegisteredService(services, cachedService, serviceRegistry);
        }
    }

    private void updateServiceRegistryWithRegisteredService(List<RegisteredService> services, RegisteredService cachedService, ServiceRegistry serviceRegistry) {
        this.saveRegisteredServiceIfNecessary(serviceRegistry, cachedService);
        services.add(cachedService);
    }

    @Generated
    public DefaultRegisteredServiceReplicationStrategy(DistributedCacheManager<RegisteredService, DistributedCacheObject<RegisteredService>, PublisherIdentifier> distributedCacheManager, StreamingServiceRegistryProperties properties, PublisherIdentifier publisherIdentifier) {
        this.distributedCacheManager = distributedCacheManager;
        this.properties = properties;
        this.publisherIdentifier = publisherIdentifier;
    }
}


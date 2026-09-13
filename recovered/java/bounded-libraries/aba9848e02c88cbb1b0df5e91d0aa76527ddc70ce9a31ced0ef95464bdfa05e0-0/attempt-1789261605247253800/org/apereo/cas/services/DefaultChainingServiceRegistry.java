/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.services.ChainingServiceRegistry
 *  org.apereo.cas.services.ImmutableServiceRegistry
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.services;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.AbstractServiceRegistry;
import org.apereo.cas.services.ChainingServiceRegistry;
import org.apereo.cas.services.ImmutableServiceRegistry;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

public class DefaultChainingServiceRegistry
extends AbstractServiceRegistry
implements ChainingServiceRegistry {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultChainingServiceRegistry.class);
    private final List<ServiceRegistry> serviceRegistries;

    public DefaultChainingServiceRegistry(ConfigurableApplicationContext applicationContext) {
        this(applicationContext, (List<ServiceRegistry>)new ArrayList<ServiceRegistry>(0));
    }

    public DefaultChainingServiceRegistry(ConfigurableApplicationContext applicationContext, List<ServiceRegistry> serviceRegistries) {
        super(applicationContext, new ArrayList<ServiceRegistryListener>(0));
        this.serviceRegistries = serviceRegistries;
    }

    public void addServiceRegistries(Collection<ServiceRegistry> registries) {
        this.serviceRegistries.addAll(registries);
    }

    public RegisteredService save(RegisteredService registeredService) {
        RegisteredService savedService = null;
        for (ServiceRegistry serviceRegistry : this.serviceRegistries) {
            RegisteredService toSave = savedService == null ? registeredService : savedService;
            savedService = serviceRegistry.save(toSave);
        }
        return savedService;
    }

    public boolean delete(RegisteredService registeredService) {
        return this.serviceRegistries.stream().allMatch(registry -> registry.delete(registeredService));
    }

    public void deleteAll() {
        this.serviceRegistries.forEach(ServiceRegistry::deleteAll);
    }

    public Collection<RegisteredService> load() {
        return this.serviceRegistries.stream().map(ServiceRegistry::load).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public RegisteredService findServiceById(long id) {
        return this.serviceRegistries.stream().map(registry -> registry.findServiceById(id)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public RegisteredService findServiceByExactServiceId(String id) {
        return this.serviceRegistries.stream().map(registry -> registry.findServiceByExactServiceId(id)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public RegisteredService findServiceByExactServiceName(String name) {
        return this.serviceRegistries.stream().map(registry -> registry.findServiceByExactServiceName(name)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public long size() {
        Predicate filter = Predicates.not((Predicate)Predicates.instanceOf(ImmutableServiceRegistry.class));
        return this.serviceRegistries.stream().filter(filter).map(ServiceRegistry::size).mapToLong(Long::longValue).sum();
    }

    public String getName() {
        Predicate filter = Predicates.not((Predicate)Predicates.instanceOf(ImmutableServiceRegistry.class));
        String name = this.serviceRegistries.stream().filter(filter).map(ServiceRegistry::getName).collect(Collectors.joining(","));
        return (String)StringUtils.defaultIfBlank((CharSequence)name, (CharSequence)this.getClass().getSimpleName());
    }

    public long countServiceRegistries() {
        return this.serviceRegistries.size();
    }

    public void synchronize(RegisteredService service) {
        this.serviceRegistries.stream().filter(serviceRegistry -> {
            RegisteredService match;
            if (StringUtils.isNotBlank((CharSequence)service.getServiceId()) && (match = serviceRegistry.findServiceByExactServiceId(service.getServiceId())) != null) {
                LOGGER.debug("Skipping [{}] JSON service definition in [{}] as a matching service [{}] is found in the registry", new Object[]{service.getName(), serviceRegistry.getName(), match.getName()});
                return false;
            }
            match = serviceRegistry.findServiceById(service.getId());
            if (match != null) {
                LOGGER.debug("Skipping [{}] JSON service definition in [{}] as a matching id [{}] is found in the registry", new Object[]{service.getName(), serviceRegistry.getName(), match.getId()});
                return false;
            }
            return true;
        }).forEach(serviceRegistry -> serviceRegistry.save(service));
    }

    public RegisteredService findServiceBy(String id) {
        return this.serviceRegistries.stream().map(registry -> registry.findServiceBy(id)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Generated
    public List<ServiceRegistry> getServiceRegistries() {
        return this.serviceRegistries;
    }
}


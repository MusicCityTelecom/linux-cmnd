/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServiceRegistryListener
 *  org.apereo.cas.support.events.service.CasRegisteredServiceLoadedEvent
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.apereo.cas.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.services.AbstractServiceRegistry;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistryListener;
import org.apereo.cas.support.events.service.CasRegisteredServiceLoadedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class InMemoryServiceRegistry
extends AbstractServiceRegistry {
    private final List<RegisteredService> registeredServices;

    public InMemoryServiceRegistry(ConfigurableApplicationContext applicationContext) {
        this(applicationContext, new ArrayList<RegisteredService>(0), new ArrayList<ServiceRegistryListener>(0));
    }

    public InMemoryServiceRegistry(ConfigurableApplicationContext applicationContext, List<RegisteredService> registeredServices, Collection<ServiceRegistryListener> serviceRegistryListeners) {
        super(applicationContext, serviceRegistryListeners);
        this.registeredServices = registeredServices;
    }

    public boolean delete(RegisteredService registeredService) {
        return !this.registeredServices.contains(registeredService) || this.registeredServices.remove(registeredService);
    }

    public void deleteAll() {
        this.registeredServices.clear();
    }

    public RegisteredService findServiceById(long id) {
        return this.registeredServices.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public Collection<RegisteredService> load() {
        ArrayList<RegisteredService> services = new ArrayList<RegisteredService>(this.registeredServices.size());
        this.registeredServices.stream().map(this::invokeServiceRegistryListenerPostLoad).filter(Objects::nonNull).forEach(s -> {
            this.publishEvent((ApplicationEvent)new CasRegisteredServiceLoadedEvent((Object)this, s));
            services.add((RegisteredService)s);
        });
        return services;
    }

    public RegisteredService save(RegisteredService registeredService) {
        if (registeredService.getId() == -1L) {
            registeredService.setId(this.findHighestId() + 1L);
        }
        this.invokeServiceRegistryListenerPreSave(registeredService);
        RegisteredService svc = this.findServiceById(registeredService.getId());
        if (svc != null) {
            this.registeredServices.remove(svc);
        }
        this.registeredServices.add(registeredService);
        return registeredService;
    }

    public long size() {
        return this.registeredServices.size();
    }

    public Stream<? extends RegisteredService> getServicesStream() {
        return this.registeredServices.stream();
    }

    private long findHighestId() {
        return this.registeredServices.stream().map(RegisteredService::getId).max(Comparator.naturalOrder()).orElse(0L);
    }

    @Generated
    public String toString() {
        return "InMemoryServiceRegistry(registeredServices=" + this.registeredServices + ")";
    }
}


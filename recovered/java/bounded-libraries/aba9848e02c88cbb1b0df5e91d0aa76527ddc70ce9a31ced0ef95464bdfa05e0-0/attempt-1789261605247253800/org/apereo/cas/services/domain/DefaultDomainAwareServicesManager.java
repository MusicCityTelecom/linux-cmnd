/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.services.AbstractServicesManager;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManagerConfigurationContext;
import org.apereo.cas.services.domain.RegisteredServiceDomainExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDomainAwareServicesManager
extends AbstractServicesManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDomainAwareServicesManager.class);
    private final Map<String, TreeSet<RegisteredService>> domains = new ConcurrentHashMap<String, TreeSet<RegisteredService>>();
    private final RegisteredServiceDomainExtractor registeredServiceDomainExtractor;

    public DefaultDomainAwareServicesManager(ServicesManagerConfigurationContext context, RegisteredServiceDomainExtractor registeredServiceDomainExtractor) {
        super(context);
        this.registeredServiceDomainExtractor = registeredServiceDomainExtractor;
    }

    public Stream<String> getDomains() {
        return this.domains.keySet().stream().sorted();
    }

    public Collection<RegisteredService> getServicesForDomain(String domain) {
        return this.domains.containsKey(domain) ? (Collection)this.domains.get(domain) : new ArrayList(0);
    }

    @Override
    protected void deleteInternal(RegisteredService service) {
        String domain = this.registeredServiceDomainExtractor.extract(service.getServiceId());
        TreeSet<RegisteredService> entries = this.domains.get(domain);
        entries.remove(service);
        if (entries.isEmpty()) {
            this.domains.remove(domain);
        }
    }

    @Override
    protected Collection<RegisteredService> getCandidateServicesToMatch(String serviceId) {
        String mappedDomain = StringUtils.isNotBlank((CharSequence)serviceId) ? this.registeredServiceDomainExtractor.extract(serviceId) : "";
        LOGGER.trace("Domain mapped to the service identifier is [{}]", (Object)mappedDomain);
        String domain = this.domains.containsKey(mappedDomain) ? mappedDomain : "default";
        LOGGER.trace("Looking up services under domain [{}] for service identifier [{}]", (Object)domain, (Object)serviceId);
        Collection<RegisteredService> registeredServices = this.getServicesForDomain(domain);
        if (registeredServices == null || registeredServices.isEmpty()) {
            LOGGER.debug("No services could be located for domain [{}]", (Object)domain);
            return new ArrayList<RegisteredService>(0);
        }
        return registeredServices;
    }

    @Override
    protected void saveInternal(RegisteredService service) {
        this.domains.entrySet().stream().filter(entry -> ((TreeSet)entry.getValue()).stream().anyMatch(s -> s.getId() == service.getId())).map(Map.Entry::getKey).findFirst().ifPresent(key -> {
            TreeSet<RegisteredService> servicesForDomain = this.domains.get(key);
            servicesForDomain.removeIf(s -> s.getId() == service.getId());
            if (servicesForDomain.isEmpty()) {
                this.domains.remove(key);
            }
        });
        this.addToDomain(service);
    }

    @Override
    protected void loadInternal(RegisteredService service) {
        this.addToDomain(service);
    }

    private void addToDomain(RegisteredService service) {
        String domain = this.registeredServiceDomainExtractor.extract(service.getServiceId());
        TreeSet<RegisteredService> services = this.domains.containsKey(domain) ? this.domains.get(domain) : new TreeSet<RegisteredService>();
        LOGGER.debug("Added service [{}] mapped to domain definition [{}]", (Object)service, (Object)domain);
        services.removeIf(s -> s.getId() == service.getId());
        services.add(service);
        this.domains.put(domain, services);
    }
}


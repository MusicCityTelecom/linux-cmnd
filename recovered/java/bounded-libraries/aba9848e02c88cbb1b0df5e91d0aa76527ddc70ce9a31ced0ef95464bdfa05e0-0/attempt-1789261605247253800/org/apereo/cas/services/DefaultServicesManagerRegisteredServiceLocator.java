/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.CasRegisteredService
 *  org.apereo.cas.services.RegexRegisteredService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManagerRegisteredServiceLocator
 */
package org.apereo.cas.services;

import java.util.Collection;
import java.util.function.BiPredicate;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.CasRegisteredService;
import org.apereo.cas.services.RegexRegisteredService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManagerRegisteredServiceLocator;

public class DefaultServicesManagerRegisteredServiceLocator
implements ServicesManagerRegisteredServiceLocator {
    private int order = Integer.MAX_VALUE;
    private BiPredicate<RegisteredService, Service> registeredServiceFilter = (registeredService, service) -> {
        boolean supportedType = this.supports((RegisteredService)registeredService, (Service)service);
        return supportedType && registeredService.matches(service.getId());
    };

    public RegisteredService locate(Collection<RegisteredService> candidates, Service service) {
        return candidates.stream().filter(registeredService -> this.supports((RegisteredService)registeredService, service)).filter(registeredService -> this.registeredServiceFilter.test((RegisteredService)registeredService, service)).findFirst().orElse(null);
    }

    public boolean supports(RegisteredService registeredService, Service service) {
        return CasRegisteredService.class.isAssignableFrom(registeredService.getClass()) && registeredService.getFriendlyName().equalsIgnoreCase("CAS Client") || RegexRegisteredService.class.isAssignableFrom(registeredService.getClass()) && registeredService.getFriendlyName().equalsIgnoreCase("CAS Client");
    }

    @Generated
    public DefaultServicesManagerRegisteredServiceLocator(int order, BiPredicate<RegisteredService, Service> registeredServiceFilter) {
        this.order = order;
        this.registeredServiceFilter = registeredServiceFilter;
    }

    @Generated
    public DefaultServicesManagerRegisteredServiceLocator() {
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public void setRegisteredServiceFilter(BiPredicate<RegisteredService, Service> registeredServiceFilter) {
        this.registeredServiceFilter = registeredServiceFilter;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public BiPredicate<RegisteredService, Service> getRegisteredServiceFilter() {
        return this.registeredServiceFilter;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.services;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.apereo.cas.services.AbstractServicesManager;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManagerConfigurationContext;

public class DefaultServicesManager
extends AbstractServicesManager {
    public DefaultServicesManager(ServicesManagerConfigurationContext context) {
        super(context);
    }

    public Collection<RegisteredService> getServicesForDomain(String domain) {
        return this.getCacheableServicesStream().get().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Override
    protected Collection<RegisteredService> getCandidateServicesToMatch(String serviceId) {
        return this.getCacheableServicesStream().get().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }
}


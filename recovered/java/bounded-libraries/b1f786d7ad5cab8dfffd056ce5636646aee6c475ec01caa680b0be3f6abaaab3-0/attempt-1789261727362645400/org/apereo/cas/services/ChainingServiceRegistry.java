/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import java.util.Collection;
import java.util.List;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServiceRegistry;

public interface ChainingServiceRegistry
extends ServiceRegistry {
    public long countServiceRegistries();

    default public void addServiceRegistry(ServiceRegistry registry) {
        this.addServiceRegistries(List.of(registry));
    }

    public void addServiceRegistries(Collection<ServiceRegistry> var1);

    public List<ServiceRegistry> getServiceRegistries();

    public void synchronize(RegisteredService var1);
}


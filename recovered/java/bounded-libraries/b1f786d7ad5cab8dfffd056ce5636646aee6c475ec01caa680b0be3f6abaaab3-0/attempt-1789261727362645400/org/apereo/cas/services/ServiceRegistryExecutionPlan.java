/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import java.util.Collection;
import java.util.function.Predicate;
import org.apereo.cas.services.ServiceRegistry;

public interface ServiceRegistryExecutionPlan {
    public ServiceRegistryExecutionPlan registerServiceRegistry(ServiceRegistry var1);

    public Collection<ServiceRegistry> getServiceRegistries();

    public Collection<ServiceRegistry> find(Predicate<ServiceRegistry> var1);
}


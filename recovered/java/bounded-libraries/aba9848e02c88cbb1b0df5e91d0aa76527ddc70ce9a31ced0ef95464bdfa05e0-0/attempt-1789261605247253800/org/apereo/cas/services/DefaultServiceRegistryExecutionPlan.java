/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.apereo.cas.services.ServiceRegistry
 *  org.apereo.cas.services.ServiceRegistryExecutionPlan
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.services.ServiceRegistry;
import org.apereo.cas.services.ServiceRegistryExecutionPlan;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServiceRegistryExecutionPlan
implements ServiceRegistryExecutionPlan {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceRegistryExecutionPlan.class);
    private final Collection<ServiceRegistry> serviceRegistries = new ArrayList<ServiceRegistry>(0);

    @CanIgnoreReturnValue
    public ServiceRegistryExecutionPlan registerServiceRegistry(ServiceRegistry registry) {
        if (BeanSupplier.isNotProxy((Object)registry)) {
            LOGGER.trace("Registering service registry [{}] into the execution plan", (Object)registry.getName());
            this.serviceRegistries.add(registry);
        }
        return this;
    }

    public Collection<ServiceRegistry> find(Predicate<ServiceRegistry> typeFilter) {
        return this.serviceRegistries.stream().filter(BeanSupplier::isNotProxy).filter(typeFilter).collect(Collectors.toList());
    }

    @Generated
    public Collection<ServiceRegistry> getServiceRegistries() {
        return this.serviceRegistries;
    }
}


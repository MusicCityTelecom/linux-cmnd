/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import org.apereo.cas.services.ServiceRegistryExecutionPlan;

@FunctionalInterface
public interface ServiceRegistryExecutionPlanConfigurer {
    public void configureServiceRegistry(ServiceRegistryExecutionPlan var1) throws Exception;

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.rest.plan;

import org.apereo.cas.rest.plan.ServiceTicketResourceEntityResponseFactoryPlan;

@FunctionalInterface
public interface ServiceTicketResourceEntityResponseFactoryConfigurer {
    public void configureEntityResponseFactory(ServiceTicketResourceEntityResponseFactoryPlan var1);
}


/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.rest.plan;

import java.util.Collection;
import org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory;

public interface ServiceTicketResourceEntityResponseFactoryPlan {
    public void registerFactory(ServiceTicketResourceEntityResponseFactory var1);

    public Collection<ServiceTicketResourceEntityResponseFactory> getFactories();
}


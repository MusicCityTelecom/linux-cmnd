/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 */
package org.apereo.cas.rest.plan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apereo.cas.rest.factory.ServiceTicketResourceEntityResponseFactory;
import org.apereo.cas.rest.plan.ServiceTicketResourceEntityResponseFactoryPlan;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public class DefaultServiceTicketResourceEntityResponseFactoryPlan
implements ServiceTicketResourceEntityResponseFactoryPlan {
    private final List<ServiceTicketResourceEntityResponseFactory> factories = new ArrayList<ServiceTicketResourceEntityResponseFactory>(0);

    @Override
    public void registerFactory(ServiceTicketResourceEntityResponseFactory factory) {
        this.factories.add(factory);
    }

    @Override
    public Collection<ServiceTicketResourceEntityResponseFactory> getFactories() {
        AnnotationAwareOrderComparator.sort(this.factories);
        return this.factories;
    }
}


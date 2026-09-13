/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.principal;

import java.util.Collection;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;

@FunctionalInterface
public interface ServiceFactoryConfigurer {
    public Collection<ServiceFactory<? extends WebApplicationService>> buildServiceFactories();
}


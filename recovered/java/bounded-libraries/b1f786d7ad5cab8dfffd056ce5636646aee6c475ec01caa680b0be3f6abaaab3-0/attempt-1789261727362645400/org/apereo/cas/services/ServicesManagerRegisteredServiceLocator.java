/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Service
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.services;

import java.util.Collection;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

public interface ServicesManagerRegisteredServiceLocator
extends Ordered {
    public RegisteredService locate(Collection<RegisteredService> var1, Service var2);

    public boolean supports(RegisteredService var1, Service var2);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}


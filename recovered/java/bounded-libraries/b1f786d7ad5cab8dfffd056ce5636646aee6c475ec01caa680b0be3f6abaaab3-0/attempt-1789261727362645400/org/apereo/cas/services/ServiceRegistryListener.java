/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.services;

import java.io.Serializable;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

public interface ServiceRegistryListener
extends Serializable,
Ordered {
    default public RegisteredService preSave(RegisteredService registeredService) {
        return registeredService;
    }

    default public RegisteredService postLoad(RegisteredService registeredService) {
        return registeredService;
    }

    default public int getOrder() {
        return 0;
    }

    public static ServiceRegistryListener noOp() {
        return new ServiceRegistryListener(){
            private static final long serialVersionUID = -8064239596498367543L;
        };
    }
}


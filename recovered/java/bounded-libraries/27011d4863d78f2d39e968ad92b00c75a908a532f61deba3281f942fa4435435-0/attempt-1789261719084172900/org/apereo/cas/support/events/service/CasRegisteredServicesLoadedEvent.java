/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.support.events.service;

import java.util.Collection;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.support.events.service.BaseCasRegisteredServiceEvent;

public class CasRegisteredServicesLoadedEvent
extends BaseCasRegisteredServiceEvent {
    private static final long serialVersionUID = 291168299712263298L;
    private final Collection<RegisteredService> services;

    public CasRegisteredServicesLoadedEvent(Object source, Collection<RegisteredService> services) {
        super(source);
        this.services = services;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRegisteredServicesLoadedEvent(super=" + super.toString() + ", services=" + this.services + ")";
    }

    @Generated
    public Collection<RegisteredService> getServices() {
        return this.services;
    }
}


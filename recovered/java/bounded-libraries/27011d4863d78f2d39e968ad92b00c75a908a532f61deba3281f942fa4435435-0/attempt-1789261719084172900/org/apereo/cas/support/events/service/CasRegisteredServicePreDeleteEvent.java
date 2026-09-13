/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.support.events.service;

import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.support.events.service.BaseCasRegisteredServiceEvent;

public class CasRegisteredServicePreDeleteEvent
extends BaseCasRegisteredServiceEvent {
    private static final long serialVersionUID = -8964760046458085393L;
    private final RegisteredService registeredService;

    public CasRegisteredServicePreDeleteEvent(Object source, RegisteredService registeredService) {
        super(source);
        this.registeredService = registeredService;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRegisteredServicePreDeleteEvent(super=" + super.toString() + ", registeredService=" + this.registeredService + ")";
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }
}


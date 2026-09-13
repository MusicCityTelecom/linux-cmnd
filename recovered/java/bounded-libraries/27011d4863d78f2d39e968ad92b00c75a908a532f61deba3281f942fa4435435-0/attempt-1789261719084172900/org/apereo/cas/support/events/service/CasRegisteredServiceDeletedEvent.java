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

public class CasRegisteredServiceDeletedEvent
extends BaseCasRegisteredServiceEvent {
    private static final long serialVersionUID = -8963214046458085393L;
    private final RegisteredService registeredService;

    public CasRegisteredServiceDeletedEvent(Object source, RegisteredService registeredService) {
        super(source);
        this.registeredService = registeredService;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRegisteredServiceDeletedEvent(super=" + super.toString() + ", registeredService=" + this.registeredService + ")";
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }
}


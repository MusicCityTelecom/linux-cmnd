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

public class CasRegisteredServiceExpiredEvent
extends BaseCasRegisteredServiceEvent {
    private static final long serialVersionUID = 291168299766263298L;
    private final RegisteredService registeredService;
    private final boolean deleted;

    public CasRegisteredServiceExpiredEvent(Object source, RegisteredService registeredService, boolean deleted) {
        super(source);
        this.registeredService = registeredService;
        this.deleted = deleted;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRegisteredServiceExpiredEvent(super=" + super.toString() + ", registeredService=" + this.registeredService + ", deleted=" + this.deleted + ")";
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }

    @Generated
    public boolean isDeleted() {
        return this.deleted;
    }
}


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

public class CasRegisteredServicePreSaveEvent
extends BaseCasRegisteredServiceEvent {
    private static final long serialVersionUID = 291988299766263298L;
    private final RegisteredService registeredService;

    public CasRegisteredServicePreSaveEvent(Object source, RegisteredService registeredService) {
        super(source);
        this.registeredService = registeredService;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRegisteredServicePreSaveEvent(super=" + super.toString() + ", registeredService=" + this.registeredService + ")";
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }
}


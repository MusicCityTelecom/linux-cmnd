/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.support.events.service;

import lombok.Generated;
import org.apereo.cas.support.events.service.BaseCasRegisteredServiceEvent;

public class CasRegisteredServicesRefreshEvent
extends BaseCasRegisteredServiceEvent {
    private static final long serialVersionUID = 291168299766263298L;

    public CasRegisteredServicesRefreshEvent(Object source) {
        super(source);
    }

    @Override
    @Generated
    public String toString() {
        return "CasRegisteredServicesRefreshEvent(super=" + super.toString() + ")";
    }
}


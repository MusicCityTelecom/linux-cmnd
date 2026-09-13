/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.support.events.service;

import lombok.Generated;
import org.apereo.cas.support.events.service.BaseCasRegisteredServiceEvent;

public class CasRegisteredServicesDeletedEvent
extends BaseCasRegisteredServiceEvent {
    private static final long serialVersionUID = -8963214046458085393L;

    public CasRegisteredServicesDeletedEvent(Object source) {
        super(source);
    }

    @Override
    @Generated
    public String toString() {
        return "CasRegisteredServicesDeletedEvent(super=" + super.toString() + ")";
    }
}


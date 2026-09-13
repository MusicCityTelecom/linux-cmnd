/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.support.events.authentication.adaptive;

import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasRiskyAuthenticationMitigatedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 291198069766263578L;
    private final Authentication authentication;
    private final RegisteredService service;
    private final Object response;

    public CasRiskyAuthenticationMitigatedEvent(Object source, Authentication authentication, RegisteredService service, Object response) {
        super(source);
        this.authentication = authentication;
        this.service = service;
        this.response = response;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRiskyAuthenticationMitigatedEvent(super=" + super.toString() + ", authentication=" + this.authentication + ", service=" + this.service + ", response=" + this.response + ")";
    }

    @Generated
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Generated
    public RegisteredService getService() {
        return this.service;
    }

    @Generated
    public Object getResponse() {
        return this.response;
    }
}


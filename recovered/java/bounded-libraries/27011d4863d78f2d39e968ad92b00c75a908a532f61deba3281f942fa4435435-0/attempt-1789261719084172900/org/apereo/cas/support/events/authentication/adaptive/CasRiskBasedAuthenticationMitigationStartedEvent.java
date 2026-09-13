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

public class CasRiskBasedAuthenticationMitigationStartedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 123568299766263298L;
    private final Authentication authentication;
    private final RegisteredService service;
    private final Object score;

    public CasRiskBasedAuthenticationMitigationStartedEvent(Object source, Authentication authentication, RegisteredService service, Object score) {
        super(source);
        this.authentication = authentication;
        this.service = service;
        this.score = score;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRiskBasedAuthenticationMitigationStartedEvent(super=" + super.toString() + ", authentication=" + this.authentication + ", service=" + this.service + ", score=" + this.score + ")";
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
    public Object getScore() {
        return this.score;
    }
}


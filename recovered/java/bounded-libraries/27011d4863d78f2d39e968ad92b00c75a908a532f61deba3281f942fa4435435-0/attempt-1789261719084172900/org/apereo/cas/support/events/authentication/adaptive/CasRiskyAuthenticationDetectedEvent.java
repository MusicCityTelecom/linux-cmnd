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

public class CasRiskyAuthenticationDetectedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 291168297497263298L;
    private final Authentication authentication;
    private final RegisteredService service;
    private final Object score;

    public CasRiskyAuthenticationDetectedEvent(Object source, Authentication authentication, RegisteredService service, Object riskScore) {
        super(source);
        this.authentication = authentication;
        this.service = service;
        this.score = riskScore;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRiskyAuthenticationDetectedEvent(super=" + super.toString() + ", authentication=" + this.authentication + ", service=" + this.service + ", score=" + this.score + ")";
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


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

public class CasRiskBasedAuthenticationEvaluationStartedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 748568299766263298L;
    private final Authentication authentication;
    private final RegisteredService service;

    public CasRiskBasedAuthenticationEvaluationStartedEvent(Object source, Authentication authentication, RegisteredService service) {
        super(source);
        this.authentication = authentication;
        this.service = service;
    }

    @Override
    @Generated
    public String toString() {
        return "CasRiskBasedAuthenticationEvaluationStartedEvent(super=" + super.toString() + ", authentication=" + this.authentication + ", service=" + this.service + ")";
    }

    @Generated
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Generated
    public RegisteredService getService() {
        return this.service;
    }
}


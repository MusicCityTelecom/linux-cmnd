/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 */
package org.apereo.cas.support.events.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasAuthenticationTransactionCompletedEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = -1862538693590213844L;
    private final Authentication authentication;

    public CasAuthenticationTransactionCompletedEvent(Object source, Authentication authentication) {
        super(source);
        this.authentication = authentication;
    }

    @Override
    @Generated
    public String toString() {
        return "CasAuthenticationTransactionCompletedEvent(super=" + super.toString() + ", authentication=" + this.authentication + ")";
    }

    @Generated
    public Authentication getAuthentication() {
        return this.authentication;
    }
}


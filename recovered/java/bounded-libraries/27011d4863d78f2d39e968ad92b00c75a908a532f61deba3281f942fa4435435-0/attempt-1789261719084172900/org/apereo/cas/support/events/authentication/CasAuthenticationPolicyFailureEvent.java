/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationTransaction
 */
package org.apereo.cas.support.events.authentication;

import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.support.events.authentication.CasAuthenticationTransactionFailureEvent;

public class CasAuthenticationPolicyFailureEvent
extends CasAuthenticationTransactionFailureEvent {
    private static final long serialVersionUID = 2208076621158767073L;
    private final Authentication authentication;

    public CasAuthenticationPolicyFailureEvent(Object source, Map<String, Throwable> failures, AuthenticationTransaction transaction, Authentication authentication) {
        super(source, failures, transaction.getCredentials());
        this.authentication = authentication;
    }

    @Override
    @Generated
    public String toString() {
        return "CasAuthenticationPolicyFailureEvent(super=" + super.toString() + ", authentication=" + this.authentication + ")";
    }

    @Generated
    public Authentication getAuthentication() {
        return this.authentication;
    }
}


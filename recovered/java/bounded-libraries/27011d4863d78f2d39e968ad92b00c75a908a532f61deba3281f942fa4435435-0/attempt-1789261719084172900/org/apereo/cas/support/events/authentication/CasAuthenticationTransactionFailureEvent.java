/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Credential
 */
package org.apereo.cas.support.events.authentication;

import java.util.Collection;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasAuthenticationTransactionFailureEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 8059647975948452375L;
    private final Map<String, Throwable> failures;
    private final Collection<Credential> credential;

    public CasAuthenticationTransactionFailureEvent(Object source, Map<String, Throwable> failures, Collection<Credential> credential) {
        super(source);
        this.failures = failures;
        this.credential = credential;
    }

    public Credential getCredential() {
        return this.credential.iterator().next();
    }

    @Generated
    public Map<String, Throwable> getFailures() {
        return this.failures;
    }
}


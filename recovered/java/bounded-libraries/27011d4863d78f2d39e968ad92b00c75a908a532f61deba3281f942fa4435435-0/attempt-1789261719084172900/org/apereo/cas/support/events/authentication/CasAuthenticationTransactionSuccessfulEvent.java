/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Credential
 */
package org.apereo.cas.support.events.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.support.events.AbstractCasEvent;

public class CasAuthenticationTransactionSuccessfulEvent
extends AbstractCasEvent {
    private static final long serialVersionUID = 8059647975948452375L;
    private final Credential credential;

    public CasAuthenticationTransactionSuccessfulEvent(Object source, Credential c) {
        super(source);
        this.credential = c;
    }

    @Override
    @Generated
    public String toString() {
        return "CasAuthenticationTransactionSuccessfulEvent(super=" + super.toString() + ", credential=" + this.credential + ")";
    }

    @Generated
    public Credential getCredential() {
        return this.credential;
    }
}


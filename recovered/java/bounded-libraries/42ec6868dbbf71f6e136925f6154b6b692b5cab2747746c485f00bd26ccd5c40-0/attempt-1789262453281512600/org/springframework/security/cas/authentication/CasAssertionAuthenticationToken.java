/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jasig.cas.client.validation.Assertion
 *  org.springframework.security.authentication.AbstractAuthenticationToken
 */
package org.springframework.security.cas.authentication;

import java.util.ArrayList;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public final class CasAssertionAuthenticationToken
extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 570L;
    private final Assertion assertion;
    private final String ticket;

    public CasAssertionAuthenticationToken(Assertion assertion, String ticket) {
        super(new ArrayList());
        this.assertion = assertion;
        this.ticket = ticket;
    }

    public Object getPrincipal() {
        return this.assertion.getPrincipal().getName();
    }

    public Object getCredentials() {
        return this.ticket;
    }

    public Assertion getAssertion() {
        return this.assertion;
    }
}


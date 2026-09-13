/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jasig.cas.client.validation.Assertion
 *  org.springframework.security.core.userdetails.AuthenticationUserDetailsService
 *  org.springframework.security.core.userdetails.UserDetails
 */
package org.springframework.security.cas.userdetails;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractCasAssertionUserDetailsService
implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
    public final UserDetails loadUserDetails(CasAssertionAuthenticationToken token) {
        return this.loadUserDetails(token.getAssertion());
    }

    protected abstract UserDetails loadUserDetails(Assertion var1);
}


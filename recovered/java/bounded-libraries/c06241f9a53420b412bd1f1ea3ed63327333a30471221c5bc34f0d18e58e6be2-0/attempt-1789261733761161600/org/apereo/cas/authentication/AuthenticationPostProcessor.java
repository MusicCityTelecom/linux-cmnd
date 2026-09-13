/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationBuilder;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface AuthenticationPostProcessor
extends Ordered {
    default public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public void process(AuthenticationBuilder var1, AuthenticationTransaction var2) throws AuthenticationException;

    default public boolean supports(Credential credential) {
        return true;
    }
}


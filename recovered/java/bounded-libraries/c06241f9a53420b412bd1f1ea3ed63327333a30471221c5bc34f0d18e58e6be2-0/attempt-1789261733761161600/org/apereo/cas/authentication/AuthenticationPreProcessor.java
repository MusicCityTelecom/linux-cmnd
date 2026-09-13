/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface AuthenticationPreProcessor
extends Ordered {
    default public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public boolean process(AuthenticationTransaction var1) throws AuthenticationException;

    default public boolean supports(Credential credential) {
        return true;
    }
}


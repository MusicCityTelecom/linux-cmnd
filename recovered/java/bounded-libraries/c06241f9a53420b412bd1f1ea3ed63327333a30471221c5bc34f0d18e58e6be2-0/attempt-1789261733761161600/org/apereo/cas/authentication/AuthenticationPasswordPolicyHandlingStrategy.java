/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationAccountStateHandler;

@FunctionalInterface
public interface AuthenticationPasswordPolicyHandlingStrategy<AuthnResponse, Configuration>
extends AuthenticationAccountStateHandler<AuthnResponse, Configuration> {
    default public boolean supports(AuthnResponse response) {
        return response != null;
    }
}


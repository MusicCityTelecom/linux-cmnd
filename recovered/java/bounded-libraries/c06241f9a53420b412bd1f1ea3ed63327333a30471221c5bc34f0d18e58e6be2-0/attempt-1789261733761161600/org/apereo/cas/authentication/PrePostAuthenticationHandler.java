/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;

@FunctionalInterface
public interface PrePostAuthenticationHandler
extends AuthenticationHandler {
    default public boolean preAuthenticate(Credential credential) {
        return true;
    }

    default public AuthenticationHandlerExecutionResult postAuthenticate(Credential credential, AuthenticationHandlerExecutionResult result) {
        return result;
    }
}


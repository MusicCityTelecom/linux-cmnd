/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.AuthenticationException
 */
package org.springframework.security.web.authentication.preauth;

import org.springframework.security.core.AuthenticationException;

public class PreAuthenticatedCredentialsNotFoundException
extends AuthenticationException {
    public PreAuthenticatedCredentialsNotFoundException(String msg) {
        super(msg);
    }

    public PreAuthenticatedCredentialsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


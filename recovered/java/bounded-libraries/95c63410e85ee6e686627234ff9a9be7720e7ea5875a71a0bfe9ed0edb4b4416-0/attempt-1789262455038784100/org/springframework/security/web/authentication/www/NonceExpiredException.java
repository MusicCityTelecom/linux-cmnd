/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.AuthenticationException
 */
package org.springframework.security.web.authentication.www;

import org.springframework.security.core.AuthenticationException;

public class NonceExpiredException
extends AuthenticationException {
    public NonceExpiredException(String msg) {
        super(msg);
    }

    public NonceExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}


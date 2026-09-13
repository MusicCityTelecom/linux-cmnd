/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.AuthenticationException
 */
package org.springframework.security.web.authentication.rememberme;

import org.springframework.security.core.AuthenticationException;

public class RememberMeAuthenticationException
extends AuthenticationException {
    public RememberMeAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RememberMeAuthenticationException(String msg) {
        super(msg);
    }
}


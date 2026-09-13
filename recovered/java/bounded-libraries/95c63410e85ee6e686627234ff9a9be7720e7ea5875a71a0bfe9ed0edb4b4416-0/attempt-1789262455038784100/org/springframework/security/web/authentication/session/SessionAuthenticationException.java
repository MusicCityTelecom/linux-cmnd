/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.AuthenticationException
 */
package org.springframework.security.web.authentication.session;

import org.springframework.security.core.AuthenticationException;

public class SessionAuthenticationException
extends AuthenticationException {
    public SessionAuthenticationException(String msg) {
        super(msg);
    }
}


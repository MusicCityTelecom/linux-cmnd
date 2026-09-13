/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.authentication.rememberme;

import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

public class CookieTheftException
extends RememberMeAuthenticationException {
    public CookieTheftException(String message) {
        super(message);
    }
}


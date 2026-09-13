/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.authentication.rememberme;

import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

public class InvalidCookieException
extends RememberMeAuthenticationException {
    public InvalidCookieException(String message) {
        super(message);
    }
}


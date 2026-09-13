/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.csrf;

import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.CsrfToken;

public class InvalidCsrfTokenException
extends CsrfException {
    public InvalidCsrfTokenException(CsrfToken expectedAccessToken, String actualAccessToken) {
        super("Invalid CSRF Token '" + actualAccessToken + "' was found on the request parameter '" + expectedAccessToken.getParameterName() + "' or header '" + expectedAccessToken.getHeaderName() + "'.");
    }
}


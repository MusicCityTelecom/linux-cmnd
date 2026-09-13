/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.csrf;

import org.springframework.security.web.csrf.CsrfException;

public class MissingCsrfTokenException
extends CsrfException {
    public MissingCsrfTokenException(String actualToken) {
        super("Could not verify the provided CSRF token because no token was found to compare.");
    }
}


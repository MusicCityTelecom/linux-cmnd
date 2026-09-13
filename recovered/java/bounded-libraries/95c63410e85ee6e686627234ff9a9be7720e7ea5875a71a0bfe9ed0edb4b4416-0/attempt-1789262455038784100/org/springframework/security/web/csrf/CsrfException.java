/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.access.AccessDeniedException
 */
package org.springframework.security.web.csrf;

import org.springframework.security.access.AccessDeniedException;

public class CsrfException
extends AccessDeniedException {
    public CsrfException(String message) {
        super(message);
    }
}


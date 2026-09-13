/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.RootCasException
 */
package org.apereo.cas.web.support;

import org.apereo.cas.authentication.RootCasException;

public class InvalidCookieException
extends RootCasException {
    private static final long serialVersionUID = -994393142011101111L;

    public InvalidCookieException(String message) {
        super(message);
    }
}


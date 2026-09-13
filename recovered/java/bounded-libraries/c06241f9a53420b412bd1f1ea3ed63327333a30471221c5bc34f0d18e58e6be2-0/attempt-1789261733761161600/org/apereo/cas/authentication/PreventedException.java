/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.RootCasException;

public class PreventedException
extends RootCasException {
    public static final String CODE = "BLOCKED_AUTHN_REQUEST";
    private static final long serialVersionUID = 4702274165911620708L;

    public PreventedException(String msg) {
        super(CODE, msg);
    }

    public PreventedException(Throwable throwable) {
        super(CODE, throwable);
    }
}


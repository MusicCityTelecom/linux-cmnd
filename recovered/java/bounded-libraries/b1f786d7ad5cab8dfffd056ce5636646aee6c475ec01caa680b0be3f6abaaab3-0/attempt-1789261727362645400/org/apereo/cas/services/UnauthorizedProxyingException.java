/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import org.apereo.cas.services.UnauthorizedServiceException;

public class UnauthorizedProxyingException
extends UnauthorizedServiceException {
    public static final String CODE = "UNAUTHORIZED_SERVICE_PROXY";
    public static final String MESSAGE = "Proxying is not allowed for registered service ";
    private static final long serialVersionUID = -7307803750894078575L;

    public UnauthorizedProxyingException() {
        super(CODE);
    }

    public UnauthorizedProxyingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedProxyingException(String message) {
        super(message);
    }
}


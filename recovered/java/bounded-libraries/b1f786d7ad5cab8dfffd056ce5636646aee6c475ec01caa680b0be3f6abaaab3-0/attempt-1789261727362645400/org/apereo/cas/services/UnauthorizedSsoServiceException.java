/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services;

import org.apereo.cas.services.UnauthorizedServiceException;

public class UnauthorizedSsoServiceException
extends UnauthorizedServiceException {
    private static final long serialVersionUID = 8909291297815558561L;
    private static final String CODE = "service.not.authorized.sso";

    public UnauthorizedSsoServiceException() {
        this(CODE);
    }

    public UnauthorizedSsoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedSsoServiceException(String message) {
        super(message);
    }
}


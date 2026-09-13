/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.ResponseStatus
 */
package org.apereo.cas.services;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Unauthorized Service Usage")
public class UnauthorizedServiceException
extends RuntimeException {
    public static final String CODE_UNAUTHZ_SERVICE = "screen.service.error.message";
    public static final String CODE_EMPTY_SVC_MGMR = "screen.service.empty.error.message";
    public static final String CODE_EXPIRED_SERVICE = "screen.service.expired.message";
    private static final long serialVersionUID = 3905807495715960369L;
    private final String code;

    public UnauthorizedServiceException(String message) {
        this(null, message);
    }

    public UnauthorizedServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public UnauthorizedServiceException(Throwable cause, String code, String message) {
        super(message, cause);
        this.code = code;
    }

    public UnauthorizedServiceException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
    }

    @Generated
    public String getCode() {
        return this.code;
    }
}


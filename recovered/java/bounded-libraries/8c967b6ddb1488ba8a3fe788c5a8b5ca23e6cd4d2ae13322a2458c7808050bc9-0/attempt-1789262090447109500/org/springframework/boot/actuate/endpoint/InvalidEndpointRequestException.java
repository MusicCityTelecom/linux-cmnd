/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint;

public class InvalidEndpointRequestException
extends RuntimeException {
    private final String reason;

    public InvalidEndpointRequestException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public InvalidEndpointRequestException(String message, String reason, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }
}


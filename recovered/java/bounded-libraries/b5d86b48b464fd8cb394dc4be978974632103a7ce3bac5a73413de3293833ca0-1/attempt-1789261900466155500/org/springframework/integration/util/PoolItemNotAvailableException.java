/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.util;

public class PoolItemNotAvailableException
extends RuntimeException {
    public PoolItemNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolItemNotAvailableException(String message) {
        super(message);
    }
}


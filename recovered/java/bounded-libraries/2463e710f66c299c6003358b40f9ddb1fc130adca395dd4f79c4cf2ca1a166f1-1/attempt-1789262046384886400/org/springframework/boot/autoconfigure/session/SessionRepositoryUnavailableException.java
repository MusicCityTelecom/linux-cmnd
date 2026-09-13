/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.autoconfigure.session.StoreType;

public class SessionRepositoryUnavailableException
extends RuntimeException {
    private final StoreType storeType;

    public SessionRepositoryUnavailableException(String message, StoreType storeType) {
        super(message);
        this.storeType = storeType;
    }

    public StoreType getStoreType() {
        return this.storeType;
    }
}


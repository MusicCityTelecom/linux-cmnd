/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.MimeType
 */
package org.springframework.boot.actuate.endpoint;

import org.springframework.util.MimeType;

public interface Producible<E extends Enum<E>> {
    public MimeType getProducedMimeType();

    default public boolean isDefault() {
        return false;
    }
}


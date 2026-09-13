/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.acks;

import org.springframework.integration.acks.AcknowledgmentCallback;

@FunctionalInterface
public interface AcknowledgmentCallbackFactory<T> {
    public AcknowledgmentCallback createCallback(T var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.acks;

import org.springframework.integration.acks.AcknowledgmentCallback;
import org.springframework.lang.Nullable;

public final class AckUtils {
    private AckUtils() {
    }

    public static void autoAck(@Nullable AcknowledgmentCallback ackCallback) {
        if (ackCallback != null && ackCallback.isAutoAck() && !ackCallback.isAcknowledged()) {
            ackCallback.acknowledge(AcknowledgmentCallback.Status.ACCEPT);
        }
    }

    public static void autoNack(@Nullable AcknowledgmentCallback ackCallback) {
        if (ackCallback != null && ackCallback.isAutoAck() && !ackCallback.isAcknowledged()) {
            ackCallback.acknowledge(AcknowledgmentCallback.Status.REJECT);
        }
    }

    public static void accept(@Nullable AcknowledgmentCallback ackCallback) {
        if (ackCallback != null) {
            ackCallback.acknowledge(AcknowledgmentCallback.Status.ACCEPT);
        }
    }

    public static void reject(@Nullable AcknowledgmentCallback ackCallback) {
        if (ackCallback != null) {
            ackCallback.acknowledge(AcknowledgmentCallback.Status.REJECT);
        }
    }

    public static void requeue(@Nullable AcknowledgmentCallback ackCallback) {
        if (ackCallback != null) {
            ackCallback.acknowledge(AcknowledgmentCallback.Status.REQUEUE);
        }
    }
}


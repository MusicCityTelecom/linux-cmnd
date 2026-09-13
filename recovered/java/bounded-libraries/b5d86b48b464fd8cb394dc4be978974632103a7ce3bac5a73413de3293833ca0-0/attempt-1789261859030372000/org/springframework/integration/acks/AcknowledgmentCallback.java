/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.acks;

import org.springframework.integration.acks.SimpleAcknowledgment;

@FunctionalInterface
public interface AcknowledgmentCallback
extends SimpleAcknowledgment {
    public void acknowledge(Status var1);

    @Override
    default public void acknowledge() {
        this.acknowledge(Status.ACCEPT);
    }

    default public boolean isAcknowledged() {
        return false;
    }

    default public void noAutoAck() {
        throw new UnsupportedOperationException("You cannot disable auto acknowledgment with this implementation");
    }

    default public boolean isAutoAck() {
        return true;
    }

    public static enum Status {
        ACCEPT,
        REJECT,
        REQUEUE;

    }
}


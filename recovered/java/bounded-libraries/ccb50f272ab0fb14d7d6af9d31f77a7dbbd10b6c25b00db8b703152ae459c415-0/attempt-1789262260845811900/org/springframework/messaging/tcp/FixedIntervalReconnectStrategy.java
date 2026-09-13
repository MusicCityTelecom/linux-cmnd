/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.messaging.tcp;

import org.springframework.messaging.tcp.ReconnectStrategy;

public class FixedIntervalReconnectStrategy
implements ReconnectStrategy {
    private final long interval;

    public FixedIntervalReconnectStrategy(long interval) {
        this.interval = interval;
    }

    @Override
    public Long getTimeToNextAttempt(int attemptCount) {
        return this.interval;
    }
}


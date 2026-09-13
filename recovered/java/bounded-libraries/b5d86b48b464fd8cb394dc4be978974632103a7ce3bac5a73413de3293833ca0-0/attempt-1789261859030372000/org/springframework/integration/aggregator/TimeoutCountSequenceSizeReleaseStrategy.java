/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

public class TimeoutCountSequenceSizeReleaseStrategy
implements ReleaseStrategy {
    public static final long DEFAULT_TIMEOUT = 60000L;
    public static final int DEFAULT_THRESHOLD = Integer.MAX_VALUE;
    private final int threshold;
    private final long timeout;

    public TimeoutCountSequenceSizeReleaseStrategy() {
        this(Integer.MAX_VALUE, 60000L);
    }

    public TimeoutCountSequenceSizeReleaseStrategy(int threshold, long timeout) {
        this.threshold = threshold;
        this.timeout = timeout;
    }

    @Override
    public boolean canRelease(MessageGroup messages) {
        long elapsedTime = System.currentTimeMillis() - this.findEarliestTimestamp(messages);
        return messages.isComplete() || messages.getMessages().size() >= this.threshold || elapsedTime > this.timeout;
    }

    private long findEarliestTimestamp(MessageGroup messages) {
        long result = Long.MAX_VALUE;
        for (Message<?> message : messages.getMessages()) {
            Long timestamp = message.getHeaders().getTimestamp();
            if (timestamp == null || timestamp >= result) continue;
            result = timestamp;
        }
        return result;
    }
}


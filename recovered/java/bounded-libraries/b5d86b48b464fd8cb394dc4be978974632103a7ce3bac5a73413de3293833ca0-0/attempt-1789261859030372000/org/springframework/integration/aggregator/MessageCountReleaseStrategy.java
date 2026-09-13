/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.aggregator;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;

public class MessageCountReleaseStrategy
implements ReleaseStrategy {
    private final int threshold;

    public MessageCountReleaseStrategy() {
        this(1);
    }

    public MessageCountReleaseStrategy(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean canRelease(MessageGroup group) {
        return group.size() >= this.threshold;
    }
}


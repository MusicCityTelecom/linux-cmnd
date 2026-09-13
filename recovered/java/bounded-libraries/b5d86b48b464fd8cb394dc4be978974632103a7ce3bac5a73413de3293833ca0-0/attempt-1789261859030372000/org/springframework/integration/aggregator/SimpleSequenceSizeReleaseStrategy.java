/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.aggregator;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;

public class SimpleSequenceSizeReleaseStrategy
implements ReleaseStrategy {
    @Override
    public boolean canRelease(MessageGroup group) {
        return group.getSequenceSize() == group.size();
    }
}


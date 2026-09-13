/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.aggregator.MessageSequenceComparator;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

public class SequenceSizeReleaseStrategy
implements ReleaseStrategy {
    private static final Log LOGGER = LogFactory.getLog(SequenceSizeReleaseStrategy.class);
    private final Comparator<Message<?>> comparator = new MessageSequenceComparator();
    private volatile boolean releasePartialSequences;

    public SequenceSizeReleaseStrategy() {
        this(false);
    }

    public SequenceSizeReleaseStrategy(boolean releasePartialSequences) {
        this.releasePartialSequences = releasePartialSequences;
    }

    public void setReleasePartialSequences(boolean releasePartialSequences) {
        this.releasePartialSequences = releasePartialSequences;
    }

    @Override
    public boolean canRelease(MessageGroup messageGroup) {
        boolean canRelease = false;
        int size = messageGroup.size();
        if (this.releasePartialSequences && size > 0) {
            int lastReleasedMessageSequence;
            Collection<Message<?>> messages;
            Message<?> minMessage;
            int nextSequenceNumber;
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace((Object)("Considering partial release of group [" + messageGroup + "]"));
            }
            if ((nextSequenceNumber = StaticMessageHeaderAccessor.getSequenceNumber(minMessage = Collections.min(messages = messageGroup.getMessages(), this.comparator))) - (lastReleasedMessageSequence = messageGroup.getLastReleasedMessageSequenceNumber()) == 1) {
                canRelease = true;
            }
        } else if (size == 0) {
            canRelease = true;
        } else {
            int sequenceSize = messageGroup.getSequenceSize();
            if (sequenceSize == size) {
                canRelease = true;
            }
        }
        return canRelease;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.aggregator.MessageSequenceComparator;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

public class ResequencingMessageGroupProcessor
implements MessageGroupProcessor {
    private final Comparator<Message<?>> comparator = new MessageSequenceComparator();

    @Override
    public Object processMessageGroup(MessageGroup group) {
        Collection<Message<?>> messages = group.getMessages();
        if (messages.size() > 0) {
            int previousSequence;
            ArrayList sorted = new ArrayList(messages);
            sorted.sort(this.comparator);
            ArrayList<Message> partialSequence = new ArrayList<Message>();
            int currentSequence = previousSequence = this.extractSequenceNumber((Message)sorted.get(0)).intValue();
            for (Message message : sorted) {
                previousSequence = currentSequence;
                currentSequence = this.extractSequenceNumber(message);
                if (currentSequence - 1 > previousSequence) break;
                partialSequence.add(message);
            }
            return partialSequence;
        }
        return null;
    }

    private Integer extractSequenceNumber(Message<?> message) {
        return StaticMessageHeaderAccessor.getSequenceNumber(message);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.io.Serializable;
import java.util.Comparator;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.messaging.Message;

public class MessageSequenceComparator
implements Comparator<Message<?>>,
Serializable {
    @Override
    public int compare(Message<?> o1, Message<?> o2) {
        int sequenceNumber1 = new IntegrationMessageHeaderAccessor(o1).getSequenceNumber();
        int sequenceNumber2 = new IntegrationMessageHeaderAccessor(o2).getSequenceNumber();
        return Integer.compare(sequenceNumber1, sequenceNumber2);
    }
}


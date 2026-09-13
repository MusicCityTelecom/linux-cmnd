/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHeaders
 */
package org.springframework.integration.channel;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.store.MessageGroupQueue;
import org.springframework.integration.store.PriorityCapableChannelMessageStore;
import org.springframework.integration.util.UpperBound;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public class PriorityChannel
extends QueueChannel {
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private final UpperBound upperBound;
    private final AtomicLong sequenceCounter = new AtomicLong();
    private final boolean useMessageStore;

    public PriorityChannel() {
        this(0, null);
    }

    public PriorityChannel(int capacity) {
        this(capacity, null);
    }

    public PriorityChannel(Comparator<Message<?>> comparator) {
        this(0, comparator);
    }

    public PriorityChannel(int capacity, @Nullable Comparator<Message<?>> comparator) {
        super(new PriorityBlockingQueue(11, new SequenceFallbackComparator(comparator)));
        this.upperBound = new UpperBound(capacity);
        this.useMessageStore = false;
    }

    public PriorityChannel(PriorityCapableChannelMessageStore messageGroupStore, Object groupId) {
        this(new MessageGroupQueue(messageGroupStore, groupId));
    }

    public PriorityChannel(MessageGroupQueue messageGroupQueue) {
        super(messageGroupQueue);
        this.upperBound = new UpperBound(0);
        this.useMessageStore = true;
    }

    @Override
    public int getRemainingCapacity() {
        return this.upperBound.availablePermits();
    }

    @Override
    protected boolean doSend(Message<?> message, long timeout) {
        if (!this.upperBound.tryAcquire(timeout)) {
            return false;
        }
        if (!this.useMessageStore) {
            return super.doSend(new MessageWrapper(message), 0L);
        }
        return super.doSend(message, 0L);
    }

    @Override
    protected Message<?> doReceive(long timeout) {
        Message<?> message = super.doReceive(timeout);
        if (message != null) {
            if (!this.useMessageStore) {
                message = ((MessageWrapper)message).getRootMessage();
            }
            this.upperBound.release();
        }
        return message;
    }

    private static final class SequenceFallbackComparator
    implements Comparator<Message<?>> {
        private final Comparator<Message<?>> targetComparator;

        SequenceFallbackComparator(@Nullable Comparator<Message<?>> targetComparator) {
            this.targetComparator = targetComparator;
        }

        @Override
        public int compare(Message<?> message1, Message<?> message2) {
            int compareResult;
            if (this.targetComparator != null) {
                compareResult = this.targetComparator.compare(message1, message2);
            } else {
                Integer priority1 = StaticMessageHeaderAccessor.getPriority(message1);
                Integer priority2 = StaticMessageHeaderAccessor.getPriority(message2);
                priority1 = priority1 != null ? priority1 : 0;
                priority2 = priority2 != null ? priority2 : 0;
                compareResult = priority2.compareTo(priority1);
            }
            if (compareResult == 0) {
                Long sequence1 = ((MessageWrapper)message1).getSequence();
                Long sequence2 = ((MessageWrapper)message2).getSequence();
                compareResult = sequence1.compareTo(sequence2);
            }
            return compareResult;
        }
    }

    private final class MessageWrapper
    implements Message<Object> {
        private final Message<?> rootMessage;
        private final long sequence;

        MessageWrapper(Message<?> rootMessage) {
            this.rootMessage = rootMessage;
            this.sequence = PriorityChannel.this.sequenceCounter.incrementAndGet();
        }

        public Message<?> getRootMessage() {
            return this.rootMessage;
        }

        public MessageHeaders getHeaders() {
            return this.rootMessage.getHeaders();
        }

        public Object getPayload() {
            return this.rootMessage.getPayload();
        }

        long getSequence() {
            return this.sequence;
        }
    }
}


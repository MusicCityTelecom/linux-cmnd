/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageHandler
 */
package org.springframework.integration.dispatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.integration.dispatcher.LoadBalancingStrategy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

public class RoundRobinLoadBalancingStrategy
implements LoadBalancingStrategy {
    private final AtomicInteger currentHandlerIndex = new AtomicInteger();

    @Override
    public final Iterator<MessageHandler> getHandlerIterator(Message<?> message, Collection<MessageHandler> handlers) {
        int size = handlers.size();
        if (size < 2) {
            this.getNextHandlerStartIndex(size);
            return handlers.iterator();
        }
        return this.buildHandlerIterator(size, handlers.toArray(new MessageHandler[size]));
    }

    private Iterator<MessageHandler> buildHandlerIterator(int size, MessageHandler[] handlers) {
        int nextHandlerStartIndex = this.getNextHandlerStartIndex(size);
        MessageHandler[] reorderedHandlers = new MessageHandler[size];
        System.arraycopy(handlers, nextHandlerStartIndex, reorderedHandlers, 0, size - nextHandlerStartIndex);
        System.arraycopy(handlers, 0, reorderedHandlers, size - nextHandlerStartIndex, nextHandlerStartIndex);
        return Arrays.stream(reorderedHandlers).iterator();
    }

    private int getNextHandlerStartIndex(int size) {
        if (size > 0) {
            int indexTail = this.currentHandlerIndex.getAndIncrement() % size;
            return indexTail < 0 ? indexTail + size : indexTail;
        }
        return size;
    }
}


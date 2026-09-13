/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.log.LogMessage
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.core.log.LogMessage;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.Message;

public class CorrelatingMessageBarrier
extends AbstractMessageHandler
implements MessageSource<Object> {
    private final ConcurrentMap<Object, Object> correlationLocks = new ConcurrentHashMap<Object, Object>();
    private final MessageGroupStore store;
    private CorrelationStrategy correlationStrategy;
    private ReleaseStrategy releaseStrategy;

    public CorrelatingMessageBarrier() {
        this(new SimpleMessageStore(0));
    }

    public CorrelatingMessageBarrier(MessageGroupStore store) {
        this.store = store;
    }

    public void setCorrelationStrategy(CorrelationStrategy correlationStrategy) {
        this.correlationStrategy = correlationStrategy;
    }

    public void setReleaseStrategy(ReleaseStrategy releaseStrategy) {
        this.releaseStrategy = releaseStrategy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void handleMessageInternal(Message<?> message) {
        Object lock;
        Object correlationKey = this.correlationStrategy.getCorrelationKey(message);
        Object object = lock = this.getLock(correlationKey);
        synchronized (object) {
            this.store.addMessagesToGroup(correlationKey, message);
        }
        this.logger.debug((CharSequence)LogMessage.format((String)"Handled message for key [%s]: %s.", (Object)correlationKey, message));
    }

    private Object getLock(Object correlationKey) {
        Object existingLock = this.correlationLocks.putIfAbsent(correlationKey, correlationKey);
        return existingLock == null ? correlationKey : existingLock;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Message<Object> receive() {
        for (Object key : this.correlationLocks.keySet()) {
            Object lock;
            Object object = lock = this.getLock(key);
            synchronized (object) {
                MessageGroup group = this.store.getMessageGroup(key);
                if (group != null && this.releaseStrategy.canRelease(group)) {
                    Message<?> nextMessage = null;
                    Iterator<Message<?>> messages = group.getMessages().iterator();
                    if (messages.hasNext()) {
                        nextMessage = messages.next();
                        this.store.removeMessagesFromGroup(key, nextMessage);
                        this.logger.debug((CharSequence)LogMessage.format((String)"Released message for key [%s]: %s.", key, nextMessage));
                    } else {
                        this.remove(key);
                    }
                    return nextMessage;
                }
            }
        }
        return null;
    }

    private void remove(Object key) {
        this.correlationLocks.remove(key);
        this.store.removeMessageGroup(key);
    }
}


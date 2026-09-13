/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.jmx.export.annotation.ManagedOperation
 *  org.springframework.jmx.export.annotation.ManagedResource
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.store.AbstractBatchingMessageGroupStore;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupFactory;
import org.springframework.integration.store.MessageGroupMetadata;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageGroupFactory;
import org.springframework.integration.store.UniqueExpiryCallback;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.messaging.Message;

@ManagedResource
public abstract class AbstractMessageGroupStore
extends AbstractBatchingMessageGroupStore
implements MessageGroupStore,
Iterable<MessageGroup> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Collection<MessageGroupStore.MessageGroupCallback> expiryCallbacks = new LinkedHashSet<MessageGroupStore.MessageGroupCallback>();
    private final MessageGroupFactory persistentMessageGroupFactory = new SimpleMessageGroupFactory(SimpleMessageGroupFactory.GroupType.PERSISTENT);
    private boolean lazyLoadMessageGroups = true;
    private boolean timeoutOnIdle;

    protected AbstractMessageGroupStore() {
    }

    protected AbstractMessageGroupStore(boolean lazyLoadMessageGroups) {
        this.lazyLoadMessageGroups = lazyLoadMessageGroups;
    }

    @Override
    protected MessageGroupFactory getMessageGroupFactory() {
        if (this.lazyLoadMessageGroups) {
            return this.persistentMessageGroupFactory;
        }
        return super.getMessageGroupFactory();
    }

    public void setExpiryCallbacks(Collection<MessageGroupStore.MessageGroupCallback> expiryCallbacks) {
        expiryCallbacks.forEach(this::registerMessageGroupExpiryCallback);
    }

    public boolean isTimeoutOnIdle() {
        return this.timeoutOnIdle;
    }

    public void setTimeoutOnIdle(boolean timeoutOnIdle) {
        this.timeoutOnIdle = timeoutOnIdle;
    }

    public void setLazyLoadMessageGroups(boolean lazyLoadMessageGroups) {
        this.lazyLoadMessageGroups = lazyLoadMessageGroups;
    }

    @Override
    public void registerMessageGroupExpiryCallback(MessageGroupStore.MessageGroupCallback callback) {
        if (callback instanceof UniqueExpiryCallback) {
            boolean uniqueExpiryCallbackPresent = this.expiryCallbacks.stream().anyMatch(UniqueExpiryCallback.class::isInstance);
            if (uniqueExpiryCallbackPresent) {
                this.logger.error((Object)"Only one instance of 'UniqueExpiryCallback' can be registered in the 'MessageGroupStore'. Use a separate 'MessageGroupStore' for each aggregator/resequencer.");
            }
        }
        this.expiryCallbacks.add(callback);
    }

    @Override
    @ManagedOperation
    public synchronized int expireMessageGroups(long timeout) {
        int count = 0;
        long threshold = System.currentTimeMillis() - timeout;
        for (MessageGroup group : this) {
            long timestamp = group.getTimestamp();
            if (this.isTimeoutOnIdle() && group.getLastModified() > 0L) {
                timestamp = group.getLastModified();
            }
            if (timestamp > threshold) continue;
            ++count;
            this.expire(this.copy(group));
        }
        return count;
    }

    protected MessageGroup copy(MessageGroup group) {
        return group;
    }

    @Override
    @ManagedAttribute
    public int getMessageCountForAllMessageGroups() {
        int count = 0;
        for (MessageGroup group : this) {
            count += group.size();
        }
        return count;
    }

    @Override
    @ManagedAttribute
    public int getMessageGroupCount() {
        int count = 0;
        for (MessageGroup group : this) {
            ++count;
        }
        return count;
    }

    @Override
    public MessageGroupMetadata getGroupMetadata(Object groupId) {
        throw new UnsupportedOperationException("Not yet implemented for this store");
    }

    @Override
    public void removeMessagesFromGroup(Object key, Message<?> ... messages) {
        this.removeMessagesFromGroup(key, Arrays.asList(messages));
    }

    @Override
    public MessageGroup addMessageToGroup(Object groupId, Message<?> message) {
        this.addMessagesToGroup(groupId, message);
        return this.getMessageGroup(groupId);
    }

    private void expire(MessageGroup group) {
        RuntimeException exception = null;
        for (MessageGroupStore.MessageGroupCallback callback : this.expiryCallbacks) {
            try {
                callback.execute(this, group);
            }
            catch (RuntimeException e) {
                if (exception == null) {
                    exception = e;
                }
                this.logger.error((Object)"Exception in expiry callback", (Throwable)e);
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
}


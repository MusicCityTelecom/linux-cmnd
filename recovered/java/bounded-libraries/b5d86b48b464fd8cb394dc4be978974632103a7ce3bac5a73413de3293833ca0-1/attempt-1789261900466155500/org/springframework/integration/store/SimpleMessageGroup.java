/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.store;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class SimpleMessageGroup
implements MessageGroup {
    private final Object groupId;
    private final Collection<Message<?>> messages;
    private final Set<Integer> sequences = new HashSet<Integer>();
    private final long timestamp;
    private volatile int lastReleasedMessageSequence;
    private volatile long lastModified;
    private volatile boolean complete;
    @Nullable
    private volatile String condition;

    public SimpleMessageGroup(Object groupId) {
        this(Collections.emptyList(), groupId);
    }

    public SimpleMessageGroup(Collection<? extends Message<?>> messages, Object groupId) {
        this(messages, groupId, System.currentTimeMillis(), false);
    }

    public SimpleMessageGroup(MessageGroup messageGroup) {
        this(messageGroup.getMessages(), messageGroup.getGroupId(), messageGroup.getTimestamp(), messageGroup.isComplete());
    }

    public SimpleMessageGroup(Collection<? extends Message<?>> messages, Object groupId, long timestamp, boolean complete) {
        this(new LinkedHashSet(), messages, groupId, timestamp, complete, false);
    }

    public SimpleMessageGroup(Collection<Message<?>> internalStore, Collection<? extends Message<?>> messages, Object groupId, long timestamp, boolean complete, boolean storePreLoaded) {
        Assert.notNull(internalStore, (String)"'internalStore' must not be null");
        this.messages = internalStore;
        this.groupId = groupId;
        this.timestamp = timestamp;
        this.complete = complete;
        if (!storePreLoaded) {
            Assert.notNull(messages, (String)"'messages' must not be null");
            for (Message<?> message : messages) {
                if (message == null) continue;
                this.addMessage(message);
            }
        }
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public long getLastModified() {
        return this.lastModified;
    }

    @Override
    public boolean canAdd(Message<?> message) {
        return true;
    }

    @Override
    public void add(Message<?> messageToAdd) {
        this.addMessage(messageToAdd);
    }

    @Override
    public boolean remove(Message<?> message) {
        this.sequences.remove(message.getHeaders().get((Object)"sequenceNumber"));
        return this.messages.remove(message);
    }

    @Override
    public int getLastReleasedMessageSequenceNumber() {
        return this.lastReleasedMessageSequence;
    }

    private boolean addMessage(Message<?> message) {
        Integer sequence = (Integer)message.getHeaders().get((Object)"sequenceNumber", Integer.class);
        this.sequences.add(sequence != null ? sequence : 0);
        return this.messages.add(message);
    }

    @Override
    public Collection<Message<?>> getMessages() {
        return Collections.unmodifiableCollection(this.messages);
    }

    @Override
    public void setLastReleasedMessageSequenceNumber(int sequenceNumber) {
        this.lastReleasedMessageSequence = sequenceNumber;
    }

    @Override
    public Object getGroupId() {
        return this.groupId;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public void complete() {
        this.complete = true;
    }

    @Override
    public int getSequenceSize() {
        if (this.size() == 0) {
            return 0;
        }
        return new IntegrationMessageHeaderAccessor(this.getOne()).getSequenceSize();
    }

    @Override
    public int size() {
        return this.messages.size();
    }

    @Override
    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    @Nullable
    public String getCondition() {
        return this.condition;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Message<?> getOne() {
        Collection<Message<?>> collection = this.messages;
        synchronized (collection) {
            Iterator<Message<?>> iterator = this.messages.iterator();
            return iterator.hasNext() ? iterator.next() : null;
        }
    }

    @Override
    public void clear() {
        this.messages.clear();
        this.sequences.clear();
    }

    public boolean containsSequence(Integer sequence) {
        return this.sequences.contains(sequence);
    }

    public String toString() {
        return "SimpleMessageGroup{groupId=" + this.groupId + ", messages=" + this.messages + ", timestamp=" + this.timestamp + ", lastModified=" + this.lastModified + '}';
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.store;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class MessageGroupMetadata
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<UUID> messageIds = new LinkedList<UUID>();
    private long timestamp;
    private volatile boolean complete;
    private volatile long lastModified;
    private volatile int lastReleasedMessageSequenceNumber;
    private volatile String condition;

    public MessageGroupMetadata() {
    }

    public MessageGroupMetadata(MessageGroup messageGroup) {
        Assert.notNull((Object)messageGroup, (String)"'messageGroup' must not be null");
        for (Message<?> message : messageGroup.getMessages()) {
            this.messageIds.add(message.getHeaders().getId());
        }
        this.complete = messageGroup.isComplete();
        this.timestamp = messageGroup.getTimestamp();
        this.lastReleasedMessageSequenceNumber = messageGroup.getLastReleasedMessageSequenceNumber();
        this.lastModified = messageGroup.getLastModified();
    }

    public void remove(UUID messageId) {
        this.messageIds.remove(messageId);
    }

    public void removeAll(Collection<UUID> messageIds) {
        this.messageIds.removeAll(messageIds);
    }

    boolean add(UUID messageId) {
        return !this.messageIds.contains(messageId) && this.messageIds.add(messageId);
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public Iterator<UUID> messageIdIterator() {
        return this.messageIds.iterator();
    }

    public int size() {
        return this.messageIds.size();
    }

    public UUID firstId() {
        if (this.messageIds.size() > 0) {
            return this.messageIds.get(0);
        }
        return null;
    }

    public List<UUID> getMessageIds() {
        return new LinkedList<UUID>(this.messageIds);
    }

    public void complete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getLastReleasedMessageSequenceNumber() {
        return this.lastReleasedMessageSequenceNumber;
    }

    public void setLastReleasedMessageSequenceNumber(int lastReleasedMessageSequenceNumber) {
        this.lastReleasedMessageSequenceNumber = lastReleasedMessageSequenceNumber;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}


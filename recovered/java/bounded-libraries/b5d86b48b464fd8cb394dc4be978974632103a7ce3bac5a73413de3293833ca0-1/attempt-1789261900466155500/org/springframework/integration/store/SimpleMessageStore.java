/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.store;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import org.springframework.integration.store.AbstractMessageGroupStore;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupMetadata;
import org.springframework.integration.store.MessageMetadata;
import org.springframework.integration.store.MessageStore;
import org.springframework.integration.support.locks.DefaultLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.util.UpperBound;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class SimpleMessageStore
extends AbstractMessageGroupStore
implements MessageStore,
ChannelMessageStore {
    private static final String MESSAGE_GROUP_FOR_GROUP_ID = "MessageGroup for groupId '";
    private static final String UPPER_BOUND_MUST_NOT_BE_NULL = "'upperBound' must not be null.";
    private static final String INTERRUPTED_WHILE_OBTAINING_LOCK = "Interrupted while obtaining lock";
    private final ConcurrentMap<UUID, Message<?>> idToMessage = new ConcurrentHashMap();
    private final ConcurrentMap<Object, MessageGroup> groupIdToMessageGroup = new ConcurrentHashMap<Object, MessageGroup>();
    private final ConcurrentMap<Object, UpperBound> groupToUpperBound = new ConcurrentHashMap<Object, UpperBound>();
    private final int groupCapacity;
    private final int individualCapacity;
    private final UpperBound individualUpperBound;
    private final long upperBoundTimeout;
    private LockRegistry lockRegistry;
    private boolean copyOnGet = false;
    private volatile boolean isUsed;

    public SimpleMessageStore(int individualCapacity, int groupCapacity) {
        this(individualCapacity, groupCapacity, new DefaultLockRegistry());
    }

    public SimpleMessageStore(int individualCapacity, int groupCapacity, long upperBoundTimeout) {
        this(individualCapacity, groupCapacity, upperBoundTimeout, new DefaultLockRegistry());
    }

    public SimpleMessageStore(int individualCapacity, int groupCapacity, LockRegistry lockRegistry) {
        this(individualCapacity, groupCapacity, 0L, lockRegistry);
    }

    public SimpleMessageStore(int individualCapacity, int groupCapacity, long upperBoundTimeout, LockRegistry lockRegistry) {
        super(false);
        Assert.notNull((Object)lockRegistry, (String)"The LockRegistry cannot be null");
        this.individualUpperBound = new UpperBound(individualCapacity);
        this.individualCapacity = individualCapacity;
        this.groupCapacity = groupCapacity;
        this.lockRegistry = lockRegistry;
        this.upperBoundTimeout = upperBoundTimeout;
    }

    public SimpleMessageStore(int capacity) {
        this(capacity, capacity);
    }

    public SimpleMessageStore() {
        this(0);
    }

    public void setCopyOnGet(boolean copyOnGet) {
        this.copyOnGet = copyOnGet;
    }

    public void setLockRegistry(LockRegistry lockRegistry) {
        Assert.notNull((Object)lockRegistry, (String)"The LockRegistry cannot be null");
        Assert.isTrue((!this.isUsed ? 1 : 0) != 0, (String)"Cannot change the lock registry after the store has been used");
        this.lockRegistry = lockRegistry;
    }

    @Override
    public void setLazyLoadMessageGroups(boolean lazyLoadMessageGroups) {
        throw new UnsupportedOperationException("The lazy-load isn't supported for in-memory 'SimpleMessageStore'");
    }

    @Override
    @ManagedAttribute
    public long getMessageCount() {
        return this.idToMessage.size();
    }

    @Override
    public <T> Message<T> addMessage(Message<T> message) {
        this.isUsed = true;
        if (!this.individualUpperBound.tryAcquire(this.upperBoundTimeout)) {
            throw new MessagingException(this.getClass().getSimpleName() + " was out of capacity (" + this.individualCapacity + "), try constructing it with a larger capacity.");
        }
        UUID id = message.getHeaders().getId();
        Assert.notNull((Object)id, (String)"ID header must not be null");
        this.idToMessage.put(id, message);
        return message;
    }

    @Override
    public Message<?> getMessage(UUID key) {
        return key != null ? (Message)this.idToMessage.get(key) : null;
    }

    @Override
    public MessageMetadata getMessageMetadata(UUID id) {
        Message<?> message = this.getMessage(id);
        if (message != null) {
            MessageMetadata messageMetadata = new MessageMetadata(id);
            Long timestamp = message.getHeaders().getTimestamp();
            messageMetadata.setTimestamp(timestamp == null ? 0L : timestamp);
            return messageMetadata;
        }
        return null;
    }

    @Override
    public Message<?> removeMessage(UUID key) {
        if (key != null) {
            Message message = (Message)this.idToMessage.remove(key);
            if (message != null) {
                this.individualUpperBound.release();
            }
            return message;
        }
        return null;
    }

    @Override
    public MessageGroup getMessageGroup(Object groupId) {
        Assert.notNull((Object)groupId, (String)"'groupId' must not be null");
        MessageGroup group = (MessageGroup)this.groupIdToMessageGroup.get(groupId);
        if (group == null) {
            return this.getMessageGroupFactory().create(groupId);
        }
        if (this.copyOnGet) {
            return this.copy(group);
        }
        return group;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected MessageGroup copy(MessageGroup group) {
        Object groupId = group.getGroupId();
        Lock lock = this.lockRegistry.obtain(groupId);
        lock.lockInterruptibly();
        try {
            MessageGroup simpleMessageGroup = this.getMessageGroupFactory().create(group.getMessages(), groupId, group.getTimestamp(), group.isComplete());
            simpleMessageGroup.setLastModified(group.getLastModified());
            simpleMessageGroup.setLastReleasedMessageSequenceNumber(group.getLastReleasedMessageSequenceNumber());
            simpleMessageGroup.setCondition(group.getCondition());
            MessageGroup messageGroup = simpleMessageGroup;
            lock.unlock();
            return messageGroup;
        }
        catch (Throwable throwable) {
            try {
                lock.unlock();
                throw throwable;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new MessagingException(INTERRUPTED_WHILE_OBTAINING_LOCK, (Throwable)e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addMessagesToGroup(Object groupId, Message<?> ... messages) {
        Assert.notNull((Object)groupId, (String)"'groupId' must not be null");
        Assert.notNull(messages, (String)"'messages' must not be null");
        Lock lock = this.lockRegistry.obtain(groupId);
        try {
            lock.lockInterruptibly();
            boolean unlocked = false;
            try {
                MessageGroup group = (MessageGroup)this.groupIdToMessageGroup.get(groupId);
                MessagingException outOfCapacityException = new MessagingException(this.getClass().getSimpleName() + " was out of capacity (" + this.groupCapacity + ") for group '" + groupId + "', try constructing it with a larger capacity.");
                if (group == null) {
                    if (this.groupCapacity > 0 && messages.length > this.groupCapacity) {
                        throw outOfCapacityException;
                    }
                    group = this.getMessageGroupFactory().create(groupId);
                    this.groupIdToMessageGroup.put(groupId, group);
                    UpperBound upperBound = new UpperBound(this.groupCapacity);
                    for (Message<?> message : messages) {
                        upperBound.tryAcquire(-1L);
                        group.add(message);
                    }
                    this.groupToUpperBound.put(groupId, upperBound);
                } else {
                    UpperBound upperBound = (UpperBound)this.groupToUpperBound.get(groupId);
                    Assert.state((upperBound != null ? 1 : 0) != 0, (String)UPPER_BOUND_MUST_NOT_BE_NULL);
                    for (Message<?> message : messages) {
                        lock.unlock();
                        if (!upperBound.tryAcquire(this.upperBoundTimeout)) {
                            unlocked = true;
                            throw outOfCapacityException;
                        }
                        lock.lockInterruptibly();
                        group.add(message);
                    }
                }
                group.setLastModified(System.currentTimeMillis());
            }
            finally {
                if (!unlocked) {
                    lock.unlock();
                }
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException(INTERRUPTED_WHILE_OBTAINING_LOCK, (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeMessageGroup(Object groupId) {
        Lock lock = this.lockRegistry.obtain(groupId);
        try {
            lock.lockInterruptibly();
            try {
                MessageGroup messageGroup = (MessageGroup)this.groupIdToMessageGroup.remove(groupId);
                if (messageGroup != null) {
                    UpperBound upperBound = (UpperBound)this.groupToUpperBound.remove(groupId);
                    Assert.state((upperBound != null ? 1 : 0) != 0, (String)UPPER_BOUND_MUST_NOT_BE_NULL);
                    upperBound.release(this.groupCapacity);
                }
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException(INTERRUPTED_WHILE_OBTAINING_LOCK, (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeMessagesFromGroup(Object groupId, Collection<Message<?>> messages) {
        Lock lock = this.lockRegistry.obtain(groupId);
        try {
            lock.lockInterruptibly();
            try {
                MessageGroup group = (MessageGroup)this.groupIdToMessageGroup.get(groupId);
                Assert.notNull((Object)group, () -> MESSAGE_GROUP_FOR_GROUP_ID + groupId + "' can not be located while attempting to remove Message(s) from the MessageGroup");
                UpperBound upperBound = (UpperBound)this.groupToUpperBound.get(groupId);
                Assert.state((upperBound != null ? 1 : 0) != 0, (String)UPPER_BOUND_MUST_NOT_BE_NULL);
                boolean modified = false;
                for (Message<?> messageToRemove : messages) {
                    if (!group.remove(messageToRemove)) continue;
                    upperBound.release();
                    modified = true;
                }
                if (modified) {
                    group.setLastModified(System.currentTimeMillis());
                }
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException(INTERRUPTED_WHILE_OBTAINING_LOCK, (Throwable)e);
        }
    }

    @Override
    public Iterator<MessageGroup> iterator() {
        return new HashSet(this.groupIdToMessageGroup.values()).iterator();
    }

    @Override
    public void setGroupCondition(Object groupId, String condition) {
        MessageGroup group = (MessageGroup)this.groupIdToMessageGroup.get(groupId);
        if (group != null) {
            group.setCondition(condition);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setLastReleasedSequenceNumberForGroup(Object groupId, int sequenceNumber) {
        Lock lock = this.lockRegistry.obtain(groupId);
        try {
            lock.lockInterruptibly();
            try {
                MessageGroup group = (MessageGroup)this.groupIdToMessageGroup.get(groupId);
                Assert.notNull((Object)group, () -> MESSAGE_GROUP_FOR_GROUP_ID + groupId + "' can not be located while attempting to set 'lastReleasedSequenceNumber'");
                group.setLastReleasedMessageSequenceNumber(sequenceNumber);
                group.setLastModified(System.currentTimeMillis());
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException(INTERRUPTED_WHILE_OBTAINING_LOCK, (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void completeGroup(Object groupId) {
        Lock lock = this.lockRegistry.obtain(groupId);
        try {
            lock.lockInterruptibly();
            try {
                MessageGroup group = (MessageGroup)this.groupIdToMessageGroup.get(groupId);
                Assert.notNull((Object)group, () -> MESSAGE_GROUP_FOR_GROUP_ID + groupId + "' can not be located while attempting to complete the MessageGroup");
                group.complete();
                group.setLastModified(System.currentTimeMillis());
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException(INTERRUPTED_WHILE_OBTAINING_LOCK, (Throwable)e);
        }
    }

    @Override
    public Message<?> pollMessageFromGroup(Object groupId) {
        Collection<Message<?>> messageList = this.getMessageGroup(groupId).getMessages();
        Message<?> message = null;
        if (!CollectionUtils.isEmpty(messageList) && (message = messageList.iterator().next()) != null) {
            this.removeMessagesFromGroup(groupId, message);
        }
        return message;
    }

    @Override
    public int messageGroupSize(Object groupId) {
        return this.getMessageGroup(groupId).size();
    }

    @Override
    public MessageGroupMetadata getGroupMetadata(Object groupId) {
        return new MessageGroupMetadata(this.getMessageGroup(groupId));
    }

    @Override
    public Message<?> getOneMessageFromGroup(Object groupId) {
        return this.getMessageGroup(groupId).getOne();
    }

    @Override
    public Collection<Message<?>> getMessagesForGroup(Object groupId) {
        return this.getMessageGroup(groupId).getMessages();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearMessageGroup(Object groupId) {
        Lock lock = this.lockRegistry.obtain(groupId);
        try {
            lock.lockInterruptibly();
            try {
                MessageGroup group = (MessageGroup)this.groupIdToMessageGroup.get(groupId);
                Assert.notNull((Object)group, () -> MESSAGE_GROUP_FOR_GROUP_ID + groupId + "' can not be located while attempting to complete the MessageGroup");
                group.clear();
                group.setLastModified(System.currentTimeMillis());
                UpperBound upperBound = (UpperBound)this.groupToUpperBound.get(groupId);
                Assert.state((upperBound != null ? 1 : 0) != 0, (String)UPPER_BOUND_MUST_NOT_BE_NULL);
                upperBound.release(this.groupCapacity);
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException(INTERRUPTED_WHILE_OBTAINING_LOCK, (Throwable)e);
        }
    }
}


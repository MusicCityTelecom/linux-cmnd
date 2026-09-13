/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.store;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.store.BasicMessageGroupStore;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.integration.store.PriorityCapableChannelMessageStore;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class MessageGroupQueue
extends AbstractQueue<Message<?>>
implements BlockingQueue<Message<?>> {
    private final Log logger = LogFactory.getLog(this.getClass());
    private static final int DEFAULT_CAPACITY = Integer.MAX_VALUE;
    private final BasicMessageGroupStore messageGroupStore;
    private final Object groupId;
    private final int capacity;
    private final Lock storeLock;
    private final Condition messageStoreNotFull;
    private final Condition messageStoreNotEmpty;

    public MessageGroupQueue(BasicMessageGroupStore messageGroupStore, Object groupId) {
        this(messageGroupStore, groupId, Integer.MAX_VALUE, new ReentrantLock(true));
    }

    public MessageGroupQueue(BasicMessageGroupStore messageGroupStore, Object groupId, int capacity) {
        this(messageGroupStore, groupId, capacity, new ReentrantLock(true));
    }

    public MessageGroupQueue(BasicMessageGroupStore messageGroupStore, Object groupId, Lock storeLock) {
        this(messageGroupStore, groupId, Integer.MAX_VALUE, storeLock);
    }

    public MessageGroupQueue(BasicMessageGroupStore messageGroupStore, Object groupId, int capacity, Lock storeLock) {
        Assert.isTrue((capacity > 0 ? 1 : 0) != 0, (String)"'capacity' must be greater than 0");
        Assert.notNull((Object)storeLock, (String)"'storeLock' must not be null");
        Assert.notNull((Object)messageGroupStore, (String)"'messageGroupStore' must not be null");
        Assert.notNull((Object)groupId, (String)"'groupId' must not be null");
        this.storeLock = storeLock;
        this.messageStoreNotFull = this.storeLock.newCondition();
        this.messageStoreNotEmpty = this.storeLock.newCondition();
        this.messageGroupStore = messageGroupStore;
        this.groupId = groupId;
        this.capacity = capacity;
        if (this.logger.isWarnEnabled() && !(messageGroupStore instanceof ChannelMessageStore)) {
            this.logger.warn((Object)(messageGroupStore.getClass().getSimpleName() + " is not optimized for use in a 'MessageGroupQueue'; consider using a `ChannelMessageStore'"));
        }
    }

    public void setPriority(boolean priority) {
        if (priority) {
            Assert.isInstanceOf(PriorityCapableChannelMessageStore.class, (Object)this.messageGroupStore);
            Assert.isTrue((boolean)((PriorityCapableChannelMessageStore)this.messageGroupStore).isPriorityEnabled(), (String)"When using priority, the 'PriorityCapableChannelMessageStore' must have priority enabled.");
        } else if (this.logger.isWarnEnabled() && this.messageGroupStore instanceof PriorityCapableChannelMessageStore && ((PriorityCapableChannelMessageStore)this.messageGroupStore).isPriorityEnabled()) {
            this.logger.warn((Object)"It's not recommended to use a priority-based message store when declaring a non-priority 'MessageGroupQueue'; message retrieval may not be FIFO; set 'priority' to 'true' if that is your intent. If you are using the namespace to define a channel, use '<priority-queue message-store.../> instead.");
        }
    }

    @Override
    public Iterator<Message<?>> iterator() {
        return this.stream().iterator();
    }

    protected BasicMessageGroupStore getMessageGroupStore() {
        return this.messageGroupStore;
    }

    protected Lock getStoreLock() {
        return this.storeLock;
    }

    protected Condition getMessageStoreNotFull() {
        return this.messageStoreNotFull;
    }

    protected Condition getMessageStoreNotEmpty() {
        return this.messageStoreNotEmpty;
    }

    @Override
    public int size() {
        return this.messageGroupStore.messageGroupSize(this.groupId);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @Override
    public Message<?> peek() {
        try {
            this.storeLock.lockInterruptibly();
            try {
                Message message;
                block11: {
                    Stream<Message<?>> messageStream = this.stream();
                    try {
                        message = messageStream.findFirst().orElse(null);
                        if (messageStream == null) break block11;
                    }
                    catch (Throwable throwable) {
                        if (messageStream != null) {
                            try {
                                messageStream.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    messageStream.close();
                }
                return message;
            }
            finally {
                this.storeLock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Message<?> poll(long timeout, TimeUnit unit) throws InterruptedException {
        Message<?> message;
        long timeoutInNanos = unit.toNanos(timeout);
        Lock lock = this.storeLock;
        lock.lockInterruptibly();
        try {
            message = this.doPoll();
            while (message == null && timeoutInNanos > 0L) {
                timeoutInNanos = this.messageStoreNotEmpty.awaitNanos(timeoutInNanos);
                message = this.doPoll();
            }
        }
        finally {
            lock.unlock();
        }
        return message;
    }

    @Override
    public Message<?> poll() {
        Message<?> message = null;
        Lock lock = this.storeLock;
        try {
            lock.lockInterruptibly();
            try {
                message = this.doPoll();
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return message;
    }

    @Override
    public int drainTo(Collection<? super Message<?>> c) {
        return this.drainTo(c, Integer.MAX_VALUE);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int drainTo(Collection<? super Message<?>> collection, int maxElements) {
        Assert.notNull(collection, (String)"'collection' must not be null");
        int originalSize = collection.size();
        ArrayList list = new ArrayList();
        Lock lock = this.storeLock;
        try {
            lock.lockInterruptibly();
            try {
                Message<?> message = this.messageGroupStore.pollMessageFromGroup(this.groupId);
                for (int i = 0; i < maxElements && message != null; ++i) {
                    list.add(message);
                    message = this.messageGroupStore.pollMessageFromGroup(this.groupId);
                }
                this.messageStoreNotFull.signal();
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            this.logger.warn((Object)"Queue may not have drained completely since this operation was interrupted", (Throwable)e);
            Thread.currentThread().interrupt();
        }
        collection.addAll(list);
        return collection.size() - originalSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean offer(Message<?> message) {
        boolean offered = true;
        Lock lock = this.storeLock;
        try {
            lock.lockInterruptibly();
            try {
                offered = this.doOffer(message);
            }
            finally {
                lock.unlock();
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return offered;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean offer(Message<?> message, long timeout, TimeUnit unit) throws InterruptedException {
        long timeoutInNanos = unit.toNanos(timeout);
        boolean offered = false;
        Lock lock = this.storeLock;
        lock.lockInterruptibly();
        try {
            if (this.capacity != Integer.MAX_VALUE) {
                while (this.size() == this.capacity && timeoutInNanos > 0L) {
                    timeoutInNanos = this.messageStoreNotFull.awaitNanos(timeoutInNanos);
                }
            }
            if (timeoutInNanos > 0L) {
                offered = this.doOffer(message);
            }
        }
        finally {
            lock.unlock();
        }
        return offered;
    }

    @Override
    public void put(Message<?> message) throws InterruptedException {
        Lock lock = this.storeLock;
        lock.lockInterruptibly();
        try {
            if (this.capacity != Integer.MAX_VALUE) {
                while (this.size() == this.capacity) {
                    this.messageStoreNotFull.await();
                }
            }
            this.doOffer(message);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public int remainingCapacity() {
        if (this.capacity == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return this.capacity - this.size();
    }

    @Override
    public Message<?> take() throws InterruptedException {
        Message<?> message;
        Lock lock = this.storeLock;
        lock.lockInterruptibly();
        try {
            while (this.size() == 0) {
                this.messageStoreNotEmpty.await();
            }
            message = this.doPoll();
        }
        finally {
            lock.unlock();
        }
        return message;
    }

    protected Collection<Message<?>> getMessages() {
        return this.messageGroupStore.getMessageGroup(this.groupId).getMessages();
    }

    @Override
    public Stream<Message<?>> stream() {
        return this.messageGroupStore.getMessageGroup(this.groupId).streamMessages();
    }

    protected Message<?> doPoll() {
        Message<?> message = this.messageGroupStore.pollMessageFromGroup(this.groupId);
        this.messageStoreNotFull.signal();
        return message;
    }

    protected boolean doOffer(Message<?> message) {
        boolean offered = false;
        if (this.capacity == Integer.MAX_VALUE || this.size() < this.capacity) {
            this.messageGroupStore.addMessageToGroup(this.groupId, message);
            offered = true;
            this.messageStoreNotEmpty.signal();
        }
        return offered;
    }
}


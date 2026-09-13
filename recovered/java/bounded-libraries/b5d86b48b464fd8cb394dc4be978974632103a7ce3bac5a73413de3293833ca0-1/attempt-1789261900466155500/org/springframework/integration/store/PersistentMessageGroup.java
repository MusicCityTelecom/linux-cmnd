/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

class PersistentMessageGroup
implements MessageGroup {
    private static final Log LOGGER = LogFactory.getLog(PersistentMessageGroup.class);
    private final MessageGroupStore messageGroupStore;
    private final Collection<Message<?>> messages = new PersistentCollection();
    private final MessageGroup original;
    private volatile Message<?> oneMessage;
    private volatile int size;

    PersistentMessageGroup(MessageGroupStore messageGroupStore, MessageGroup original) {
        this.messageGroupStore = messageGroupStore;
        this.original = original;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Collection<Message<?>> getMessages() {
        return Collections.unmodifiableCollection(this.messages);
    }

    @Override
    public Stream<Message<?>> streamMessages() {
        return this.messageGroupStore.streamMessagesForGroup(this.original.getGroupId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Message<?> getOne() {
        if (this.oneMessage == null) {
            PersistentMessageGroup persistentMessageGroup = this;
            synchronized (persistentMessageGroup) {
                if (this.oneMessage == null) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object)("Lazy loading of one message for messageGroup: " + this.original.getGroupId()));
                    }
                    this.oneMessage = this.messageGroupStore.getOneMessageFromGroup(this.original.getGroupId());
                }
            }
        }
        return this.oneMessage;
    }

    @Override
    public int getSequenceSize() {
        if (this.size() == 0) {
            return 0;
        }
        Message<?> message = this.getOne();
        if (message != null) {
            Integer sequenceSize = (Integer)message.getHeaders().get((Object)"sequenceSize", Integer.class);
            return sequenceSize != null ? sequenceSize : 0;
        }
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int size() {
        if (this.size == 0) {
            PersistentMessageGroup persistentMessageGroup = this;
            synchronized (persistentMessageGroup) {
                if (this.size == 0) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object)("Lazy loading of group size for messageGroup: " + this.original.getGroupId()));
                    }
                    this.size = this.messageGroupStore.messageGroupSize(this.original.getGroupId());
                }
            }
        }
        return this.size;
    }

    @Override
    public Object getGroupId() {
        return this.original.getGroupId();
    }

    @Override
    public boolean canAdd(Message<?> message) {
        return this.original.canAdd(message);
    }

    @Override
    public int getLastReleasedMessageSequenceNumber() {
        return this.original.getLastReleasedMessageSequenceNumber();
    }

    @Override
    public boolean isComplete() {
        return this.original.isComplete();
    }

    @Override
    public void complete() {
        this.original.complete();
    }

    @Override
    public long getTimestamp() {
        return this.original.getTimestamp();
    }

    @Override
    public long getLastModified() {
        return this.original.getLastModified();
    }

    @Override
    public void setLastModified(long lastModified) {
        this.original.setLastModified(lastModified);
    }

    @Override
    public void add(Message<?> messageToAdd) {
        this.original.add(messageToAdd);
    }

    @Override
    public boolean remove(Message<?> messageToRemove) {
        return this.original.remove(messageToRemove);
    }

    @Override
    public void setLastReleasedMessageSequenceNumber(int sequenceNumber) {
        this.original.setLastReleasedMessageSequenceNumber(sequenceNumber);
    }

    @Override
    public void setCondition(String condition) {
        this.original.setCondition(condition);
    }

    @Override
    @Nullable
    public String getCondition() {
        return this.original.getCondition();
    }

    @Override
    public void clear() {
        this.original.clear();
    }

    private final class PersistentCollection
    extends AbstractCollection<Message<?>> {
        private volatile Collection<Message<?>> collection;

        PersistentCollection() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void load() {
            if (this.collection == null) {
                PersistentCollection persistentCollection = this;
                synchronized (persistentCollection) {
                    if (this.collection == null) {
                        Object groupId = PersistentMessageGroup.this.original.getGroupId();
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug((Object)("Lazy loading of messages for messageGroup: " + groupId));
                        }
                        this.collection = PersistentMessageGroup.this.messageGroupStore.getMessagesForGroup(groupId);
                    }
                }
            }
        }

        @Override
        public boolean contains(Object o) {
            this.load();
            return this.collection.contains(o);
        }

        @Override
        public Object[] toArray() {
            this.load();
            return this.collection.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            this.load();
            return this.collection.toArray(a);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            this.load();
            return this.collection.containsAll(c);
        }

        @Override
        public Iterator<Message<?>> iterator() {
            this.load();
            return this.collection.iterator();
        }

        @Override
        public int size() {
            return PersistentMessageGroup.this.size();
        }

        @Override
        public Spliterator<Message<?>> spliterator() {
            return PersistentMessageGroup.this.streamMessages().spliterator();
        }

        @Override
        public Stream<Message<?>> stream() {
            return PersistentMessageGroup.this.streamMessages();
        }
    }
}


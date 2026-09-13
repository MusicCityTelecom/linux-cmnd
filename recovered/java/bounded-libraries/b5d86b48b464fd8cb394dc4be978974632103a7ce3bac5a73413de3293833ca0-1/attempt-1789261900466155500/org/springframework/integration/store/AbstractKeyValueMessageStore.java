/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jmx.export.annotation.ManagedAttribute
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.integration.store.AbstractMessageGroupStore;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupMetadata;
import org.springframework.integration.store.MessageHolder;
import org.springframework.integration.store.MessageMetadata;
import org.springframework.integration.store.MessageStore;
import org.springframework.integration.store.SimpleMessageGroup;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public abstract class AbstractKeyValueMessageStore
extends AbstractMessageGroupStore
implements MessageStore {
    private static final String GROUP_ID_MUST_NOT_BE_NULL = "'groupId' must not be null";
    protected static final String MESSAGE_KEY_PREFIX = "MESSAGE_";
    protected static final String MESSAGE_GROUP_KEY_PREFIX = "MESSAGE_GROUP_";
    private final String messagePrefix;
    private final String groupPrefix;

    protected AbstractKeyValueMessageStore() {
        this("");
    }

    protected AbstractKeyValueMessageStore(String prefix) {
        Assert.notNull((Object)prefix, (String)"'prefix' must not be null");
        this.messagePrefix = prefix + MESSAGE_KEY_PREFIX;
        this.groupPrefix = prefix + MESSAGE_GROUP_KEY_PREFIX;
    }

    protected String getMessagePrefix() {
        return this.messagePrefix;
    }

    public String getGroupPrefix() {
        return this.groupPrefix;
    }

    @Override
    public Message<?> getMessage(UUID messageId) {
        Assert.notNull((Object)messageId, (String)"'messageId' must not be null");
        Object object = this.doRetrieve(this.messagePrefix + messageId);
        if (object != null) {
            return this.extractMessage(object);
        }
        return null;
    }

    private Message<?> extractMessage(Object object) {
        if (object instanceof MessageHolder) {
            return ((MessageHolder)object).getMessage();
        }
        if (object instanceof Message) {
            return (Message)object;
        }
        throw new IllegalArgumentException("Object of class [" + object.getClass().getName() + "] must be an instance of [org.springframework.integration.store.MessageHolder].");
    }

    @Override
    public MessageMetadata getMessageMetadata(UUID messageId) {
        Assert.notNull((Object)messageId, (String)"'messageId' must not be null");
        Object object = this.doRetrieve(this.messagePrefix + messageId);
        if (object != null) {
            this.extractMessage(object);
            if (object instanceof MessageHolder) {
                return ((MessageHolder)object).getMessageMetadata();
            }
        }
        return null;
    }

    @Override
    public <T> Message<T> addMessage(Message<T> message) {
        this.doAddMessage(message);
        return this.getMessage(message.getHeaders().getId());
    }

    protected void doAddMessage(Message<?> message) {
        Assert.notNull(message, (String)"'message' must not be null");
        UUID messageId = message.getHeaders().getId();
        Assert.notNull((Object)messageId, (String)"Cannot store messages without an ID header");
        this.doStoreIfAbsent(this.messagePrefix + messageId, new MessageHolder(message));
    }

    @Override
    public Message<?> removeMessage(UUID id) {
        Assert.notNull((Object)id, (String)"'id' must not be null");
        Object object = this.doRemove(this.messagePrefix + id);
        if (object != null) {
            return this.extractMessage(object);
        }
        return null;
    }

    @Override
    @ManagedAttribute
    public long getMessageCount() {
        Collection<?> messageIds = this.doListKeys(this.messagePrefix + '*');
        return messageIds != null ? (long)messageIds.size() : 0L;
    }

    @Override
    public MessageGroup getMessageGroup(Object groupId) {
        MessageGroupMetadata metadata = this.getGroupMetadata(groupId);
        if (metadata != null) {
            MessageGroup messageGroup = this.getMessageGroupFactory().create(this, groupId, metadata.getTimestamp(), metadata.isComplete());
            messageGroup.setLastModified(metadata.getLastModified());
            messageGroup.setLastReleasedMessageSequenceNumber(metadata.getLastReleasedMessageSequenceNumber());
            messageGroup.setCondition(metadata.getCondition());
            return messageGroup;
        }
        return new SimpleMessageGroup(groupId);
    }

    @Override
    public MessageGroupMetadata getGroupMetadata(Object groupId) {
        Assert.notNull((Object)groupId, (String)GROUP_ID_MUST_NOT_BE_NULL);
        Object mgm = this.doRetrieve(this.groupPrefix + groupId);
        if (mgm != null) {
            Assert.isInstanceOf(MessageGroupMetadata.class, (Object)mgm);
            return (MessageGroupMetadata)mgm;
        }
        return null;
    }

    @Override
    public void addMessagesToGroup(Object groupId, Message<?> ... messages) {
        Assert.notNull((Object)groupId, (String)GROUP_ID_MUST_NOT_BE_NULL);
        Assert.notNull(messages, (String)"'messages' must not be null");
        MessageGroupMetadata metadata = this.getGroupMetadata(groupId);
        SimpleMessageGroup group = null;
        if (metadata == null) {
            group = new SimpleMessageGroup(groupId);
        }
        for (Message<?> message : messages) {
            this.doAddMessage(message);
            if (metadata != null) {
                metadata.add(message.getHeaders().getId());
                continue;
            }
            group.add(message);
        }
        if (group != null) {
            metadata = new MessageGroupMetadata(group);
            metadata.setLastModified(group.getTimestamp());
        } else {
            metadata.setLastModified(System.currentTimeMillis());
        }
        this.doStore(this.groupPrefix + groupId, metadata);
    }

    @Override
    public void removeMessagesFromGroup(Object groupId, Collection<Message<?>> messages) {
        Assert.notNull((Object)groupId, (String)GROUP_ID_MUST_NOT_BE_NULL);
        Assert.notNull(messages, (String)"'messages' must not be null");
        Object mgm = this.doRetrieve(this.groupPrefix + groupId);
        if (mgm != null) {
            Assert.isInstanceOf(MessageGroupMetadata.class, (Object)mgm);
            MessageGroupMetadata messageGroupMetadata = (MessageGroupMetadata)mgm;
            ArrayList<UUID> ids = new ArrayList<UUID>();
            for (Message<?> messageToRemove : messages) {
                ids.add(messageToRemove.getHeaders().getId());
            }
            messageGroupMetadata.removeAll(ids);
            ArrayList<Object> messageIds = new ArrayList<Object>();
            for (UUID id : ids) {
                messageIds.add(this.messagePrefix + id);
            }
            this.doRemoveAll(messageIds);
            messageGroupMetadata.setLastModified(System.currentTimeMillis());
            this.doStore(this.groupPrefix + groupId, messageGroupMetadata);
        }
    }

    @Override
    public void completeGroup(Object groupId) {
        Assert.notNull((Object)groupId, (String)GROUP_ID_MUST_NOT_BE_NULL);
        MessageGroupMetadata metadata = this.getGroupMetadata(groupId);
        if (metadata != null) {
            metadata.complete();
            metadata.setLastModified(System.currentTimeMillis());
            this.doStore(this.groupPrefix + groupId, metadata);
        }
    }

    @Override
    public void removeMessageGroup(Object groupId) {
        Assert.notNull((Object)groupId, (String)GROUP_ID_MUST_NOT_BE_NULL);
        Object mgm = this.doRemove(this.groupPrefix + groupId);
        if (mgm != null) {
            Assert.isInstanceOf(MessageGroupMetadata.class, (Object)mgm);
            MessageGroupMetadata messageGroupMetadata = (MessageGroupMetadata)mgm;
            List<Object> messageIds = messageGroupMetadata.getMessageIds().stream().map(id -> this.messagePrefix + id).collect(Collectors.toList());
            this.doRemoveAll(messageIds);
        }
    }

    @Override
    public void setGroupCondition(Object groupId, String condition) {
        MessageGroupMetadata metadata = this.getGroupMetadata(groupId);
        if (metadata != null) {
            metadata.setCondition(condition);
            this.doStore(this.groupPrefix + groupId, metadata);
        }
    }

    @Override
    public void setLastReleasedSequenceNumberForGroup(Object groupId, int sequenceNumber) {
        Assert.notNull((Object)groupId, (String)GROUP_ID_MUST_NOT_BE_NULL);
        MessageGroupMetadata metadata = this.getGroupMetadata(groupId);
        if (metadata == null) {
            SimpleMessageGroup messageGroup = new SimpleMessageGroup(groupId);
            metadata = new MessageGroupMetadata(messageGroup);
        }
        metadata.setLastReleasedMessageSequenceNumber(sequenceNumber);
        metadata.setLastModified(System.currentTimeMillis());
        this.doStore(this.groupPrefix + groupId, metadata);
    }

    @Override
    public Message<?> pollMessageFromGroup(Object groupId) {
        UUID firstId;
        MessageGroupMetadata groupMetadata = this.getGroupMetadata(groupId);
        if (groupMetadata != null && (firstId = groupMetadata.firstId()) != null) {
            groupMetadata.remove(firstId);
            groupMetadata.setLastModified(System.currentTimeMillis());
            this.doStore(this.groupPrefix + groupId, groupMetadata);
            return this.removeMessage(firstId);
        }
        return null;
    }

    @Override
    public Message<?> getOneMessageFromGroup(Object groupId) {
        UUID messageId;
        MessageGroupMetadata groupMetadata = this.getGroupMetadata(groupId);
        if (groupMetadata != null && (messageId = groupMetadata.firstId()) != null) {
            return this.getMessage(messageId);
        }
        return null;
    }

    @Override
    public Collection<Message<?>> getMessagesForGroup(Object groupId) {
        MessageGroupMetadata groupMetadata = this.getGroupMetadata(groupId);
        ArrayList messages = new ArrayList();
        if (groupMetadata != null) {
            Iterator<UUID> messageIds = groupMetadata.messageIdIterator();
            while (messageIds.hasNext()) {
                messages.add(this.getMessage(messageIds.next()));
            }
        }
        return messages;
    }

    @Override
    public Stream<Message<?>> streamMessagesForGroup(Object groupId) {
        return this.getGroupMetadata(groupId).getMessageIds().stream().map(this::getMessage);
    }

    @Override
    public Iterator<MessageGroup> iterator() {
        Iterator<String> idIterator = this.normalizeKeys(this.doListKeys(this.groupPrefix + '*')).iterator();
        return new MessageGroupIterator(idIterator);
    }

    private Collection<String> normalizeKeys(Collection<String> keys) {
        HashSet<String> normalizedKeys = new HashSet<String>();
        for (String key : keys) {
            String strKey = key;
            if (strKey.startsWith(this.groupPrefix)) {
                strKey = strKey.replace(this.groupPrefix, "");
            } else if (strKey.startsWith(this.messagePrefix)) {
                strKey = strKey.replace(this.messagePrefix, "");
            }
            normalizedKeys.add(strKey);
        }
        return normalizedKeys;
    }

    @Override
    public int messageGroupSize(Object groupId) {
        MessageGroupMetadata mgm = this.getGroupMetadata(groupId);
        if (mgm != null) {
            return mgm.size();
        }
        return 0;
    }

    protected abstract Object doRetrieve(Object var1);

    protected abstract void doStore(Object var1, Object var2);

    protected abstract void doStoreIfAbsent(Object var1, Object var2);

    protected abstract Object doRemove(Object var1);

    protected abstract void doRemoveAll(Collection<Object> var1);

    protected abstract Collection<?> doListKeys(String var1);

    private final class MessageGroupIterator
    implements Iterator<MessageGroup> {
        private final Iterator<?> idIterator;

        MessageGroupIterator(Iterator<?> idIterator) {
            this.idIterator = idIterator;
        }

        @Override
        public boolean hasNext() {
            return this.idIterator.hasNext();
        }

        @Override
        public MessageGroup next() {
            Object messageGroupId = this.idIterator.next();
            return AbstractKeyValueMessageStore.this.getMessageGroup(messageGroupId);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}


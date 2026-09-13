/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupFactory;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.PersistentMessageGroup;
import org.springframework.integration.store.SimpleMessageGroup;
import org.springframework.messaging.Message;

public class SimpleMessageGroupFactory
implements MessageGroupFactory {
    private final GroupType type;

    public SimpleMessageGroupFactory() {
        this(GroupType.HASH_SET);
    }

    public SimpleMessageGroupFactory(GroupType type) {
        this.type = type;
    }

    @Override
    public MessageGroup create(Object groupId) {
        return this.create(Collections.emptyList(), groupId);
    }

    @Override
    public MessageGroup create(Collection<? extends Message<?>> messages, Object groupId) {
        return this.create(messages, groupId, System.currentTimeMillis(), false);
    }

    @Override
    public MessageGroup create(Collection<? extends Message<?>> messages, Object groupId, long timestamp, boolean complete) {
        return new SimpleMessageGroup(this.type.get(), messages, groupId, timestamp, complete, false);
    }

    @Override
    public MessageGroup create(MessageGroupStore messageGroupStore, Object groupId) {
        if (GroupType.PERSISTENT.equals((Object)this.type)) {
            return new PersistentMessageGroup(messageGroupStore, new SimpleMessageGroup(groupId));
        }
        return this.create(messageGroupStore.getMessagesForGroup(groupId), groupId);
    }

    @Override
    public MessageGroup create(MessageGroupStore messageGroupStore, Object groupId, long timestamp, boolean complete) {
        if (GroupType.PERSISTENT.equals((Object)this.type)) {
            SimpleMessageGroup original = new SimpleMessageGroup(Collections.emptyList(), groupId, timestamp, complete);
            return new PersistentMessageGroup(messageGroupStore, original);
        }
        return this.create(messageGroupStore.getMessagesForGroup(groupId), groupId, timestamp, complete);
    }

    public static enum GroupType {
        LIST{

            @Override
            Collection<Message<?>> get() {
                return new ArrayList();
            }
        }
        ,
        BLOCKING_QUEUE{

            @Override
            Collection<Message<?>> get() {
                return new LinkedBlockingQueue();
            }
        }
        ,
        HASH_SET{

            @Override
            Collection<Message<?>> get() {
                return new LinkedHashSet();
            }
        }
        ,
        SYNCHRONISED_SET{

            @Override
            Collection<Message<?>> get() {
                return Collections.synchronizedSet(new LinkedHashSet());
            }
        }
        ,
        PERSISTENT{

            @Override
            Collection<Message<?>> get() {
                return HASH_SET.get();
            }
        };


        abstract Collection<Message<?>> get();
    }
}


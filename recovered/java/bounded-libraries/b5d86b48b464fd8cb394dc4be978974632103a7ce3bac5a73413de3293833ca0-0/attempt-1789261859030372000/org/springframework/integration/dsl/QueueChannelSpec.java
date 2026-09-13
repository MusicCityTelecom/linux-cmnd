/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.dsl;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.store.BasicMessageGroupStore;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.integration.store.MessageGroupQueue;
import org.springframework.messaging.Message;

public class QueueChannelSpec
extends MessageChannelSpec<QueueChannelSpec, QueueChannel> {
    protected Queue<Message<?>> queue;
    protected Integer capacity;

    protected QueueChannelSpec() {
    }

    protected QueueChannelSpec(Queue<Message<?>> queue) {
        this.queue = queue;
    }

    protected QueueChannelSpec(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    protected QueueChannel doGet() {
        this.channel = this.queue != null ? new QueueChannel(this.queue) : (this.capacity != null ? new QueueChannel(this.capacity) : new QueueChannel());
        return (QueueChannel)super.doGet();
    }

    public static class MessageStoreSpec
    extends QueueChannelSpec {
        private final ChannelMessageStore messageGroupStore;
        private final Object groupId;
        private Lock storeLock;

        protected MessageStoreSpec(ChannelMessageStore messageGroupStore, Object groupId) {
            this.messageGroupStore = messageGroupStore;
            this.groupId = groupId;
        }

        @Override
        protected MessageStoreSpec id(String id) {
            return (MessageStoreSpec)super.id(id);
        }

        public MessageStoreSpec capacity(Integer capacityToSet) {
            this.capacity = capacityToSet;
            return this;
        }

        public MessageStoreSpec storeLock(Lock storeLockToSet) {
            this.storeLock = storeLockToSet;
            return this;
        }

        @Override
        protected QueueChannel doGet() {
            this.queue = this.capacity != null ? (this.storeLock != null ? new MessageGroupQueue(this.messageGroupStore, this.groupId, this.capacity, this.storeLock) : new MessageGroupQueue((BasicMessageGroupStore)this.messageGroupStore, this.groupId, this.capacity)) : (this.storeLock != null ? new MessageGroupQueue((BasicMessageGroupStore)this.messageGroupStore, this.groupId, this.storeLock) : new MessageGroupQueue(this.messageGroupStore, this.groupId));
            return super.doGet();
        }
    }
}


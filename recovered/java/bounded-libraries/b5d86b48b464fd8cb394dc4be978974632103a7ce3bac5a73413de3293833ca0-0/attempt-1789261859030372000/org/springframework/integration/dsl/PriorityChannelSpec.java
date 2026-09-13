/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.dsl;

import java.util.Comparator;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.store.MessageGroupQueue;
import org.springframework.integration.store.PriorityCapableChannelMessageStore;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class PriorityChannelSpec
extends MessageChannelSpec<PriorityChannelSpec, PriorityChannel> {
    private int capacity;
    private Comparator<Message<?>> comparator;
    private MessageGroupQueue messageGroupQueue;

    protected PriorityChannelSpec() {
    }

    public PriorityChannelSpec capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public PriorityChannelSpec comparator(Comparator<Message<?>> comparator) {
        this.comparator = comparator;
        return this;
    }

    public PriorityChannelSpec messageStore(PriorityCapableChannelMessageStore messageGroupStore, Object groupId) {
        this.messageGroupQueue = new MessageGroupQueue(messageGroupStore, groupId);
        this.messageGroupQueue.setPriority(true);
        return this;
    }

    @Override
    protected PriorityChannel doGet() {
        Assert.state((this.comparator == null || this.messageGroupQueue == null ? 1 : 0) != 0, (String)"Only one of 'comparator' or 'messageGroupStore' can be specified.");
        this.channel = this.messageGroupQueue != null ? new PriorityChannel(this.messageGroupQueue) : new PriorityChannel(this.capacity, this.comparator);
        return (PriorityChannel)super.doGet();
    }
}


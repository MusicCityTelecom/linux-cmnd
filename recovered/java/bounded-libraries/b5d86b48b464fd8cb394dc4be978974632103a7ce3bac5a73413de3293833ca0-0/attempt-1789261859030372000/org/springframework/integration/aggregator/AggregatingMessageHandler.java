/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.Collection;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.aggregator.AbstractCorrelatingMessageHandler;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.Message;

public class AggregatingMessageHandler
extends AbstractCorrelatingMessageHandler {
    private volatile boolean expireGroupsUponCompletion = false;

    public AggregatingMessageHandler(MessageGroupProcessor processor, MessageGroupStore store, CorrelationStrategy correlationStrategy, ReleaseStrategy releaseStrategy) {
        super(processor, store, correlationStrategy, releaseStrategy);
    }

    public AggregatingMessageHandler(MessageGroupProcessor processor, MessageGroupStore store) {
        super(processor, store);
    }

    public AggregatingMessageHandler(MessageGroupProcessor processor) {
        super(processor);
    }

    public void setExpireGroupsUponCompletion(boolean expireGroupsUponCompletion) {
        this.expireGroupsUponCompletion = expireGroupsUponCompletion;
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.aggregator;
    }

    @Override
    protected boolean isExpireGroupsUponCompletion() {
        return this.expireGroupsUponCompletion;
    }

    @Override
    protected void afterRelease(MessageGroup messageGroup, Collection<Message<?>> completedMessages) {
        Object groupId = messageGroup.getGroupId();
        MessageGroupStore messageStore = this.getMessageStore();
        messageStore.completeGroup(groupId);
        if (this.expireGroupsUponCompletion) {
            this.remove(messageGroup);
        } else if (messageStore instanceof SimpleMessageStore) {
            ((SimpleMessageStore)messageStore).clearMessageGroup(groupId);
        } else {
            messageStore.removeMessagesFromGroup(groupId, messageGroup.getMessages());
        }
    }
}

